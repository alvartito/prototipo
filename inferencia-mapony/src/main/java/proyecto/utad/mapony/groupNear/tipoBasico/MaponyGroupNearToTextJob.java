package proyecto.utad.mapony.groupNear.tipoBasico;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proyecto.utad.mapony.groupNear.tipoBasico.combiner.MaponyGroupNearToTextComb;
import proyecto.utad.mapony.groupNear.tipoBasico.mapper.MaponyGroupNearToTextMap;
import proyecto.utad.mapony.groupNear.tipoBasico.reducer.MaponyGNArrayToTextRed;
import util.clases.GeoHashCiudad;
import util.constantes.MaponyCte;
import util.writables.array.TextArrayWritable;

/**
 * @author Álvaro Sánchez Blasco<p>
 *         Job para la agrupación de los datos a procesar. Leemos los datos de la ruta especificada en el properties
 *         (ruta_ficheros).<p>
 *         Debemos tener en hdfs, en la ruta especificada en el properties (ruta_paises) el fichero de ciudades obtenido
 *         de geonames (se encuentra en la carpeta ext_dataset) del proyecto.<p>
 *         El fichero de propiedades job.properties, actualmente, hay que copiarlo en la carpeta target. TODO cambiarlo
 *         para que se reciba como parámetro del job.
 */
public class MaponyGroupNearToTextJob extends Configured implements Tool {

	private static Properties properties;
	private static final Logger logger = LoggerFactory.getLogger(MaponyGroupNearToTextJob.class);
	private String rutaFicheros;
	private int numReducers;
	
	private static void loadProperties(final String fileName) throws IOException {
		if (null == properties) {
			properties = new Properties();
		}
		try {
			FileInputStream in = new FileInputStream(fileName);
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int run(String[] args) throws Exception {
		setRutaFicheros(properties.getProperty(MaponyCte.datos));
		setNumReducers(Integer.parseInt(properties.getProperty(MaponyCte.reducers)));

		Configuration config = getConf();

		Job job = Job.getInstance(config, MaponyCte.jobNameGroupNear);
		job.setJarByClass(MaponyGroupNearToTextJob.class);

		job.setInputFormatClass(TextInputFormat.class);

		//La salida de nuestro Job será del tipo Sequencefile con compresión a nivel de Bloque.
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		SequenceFileOutputFormat.setCompressOutput(job, true);
		SequenceFileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
		SequenceFileOutputFormat.setOutputCompressionType(job, CompressionType.BLOCK);

		//Salida del Mapper
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		//Salida del Job
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TextArrayWritable.class);

		// Recuperamos los ficheros que vamos a procesar, y los añadimos como datos de entrada
		final FileSystem fs = FileSystem.get(new URI("hdfs://quickstart.cloudera:8020/"), config);

		// Path de destino de ejecución
		Path outPath = new Path(properties.getProperty(MaponyCte.salida_group_near));

		// Borramos todos los directorios que puedan existir
		FileSystem.get(outPath.toUri(), config).delete(outPath, true);

		// Recuperamos el contenido del fichero de países y ciudades obtenido de geonames, para cargarlos en memoria.
		Path pathPaises = new Path(properties.getProperty(MaponyCte.paises));

		FileSystem hdfs = FileSystem.get(config);
		String linea = null;
		BufferedReader d = new BufferedReader(new InputStreamReader(hdfs.open(pathPaises)));
		while ((linea = d.readLine()) != null) {
			GeoHashCiudad.cargaHashDatosCiudades(linea);
		}

		// Recuperamos los datos a procesar del path origen (data/*.bz2)
		try {
			FileStatus[] glob = fs.globStatus(new Path(getRutaFicheros()));

			// Si tenemos datos...
			if (null != glob) {
				if (glob.length > 0) {
					for (FileStatus fileStatus : glob) {
						Path pFich = fileStatus.getPath();
						// MultipleInputs
						MultipleInputs.addInputPath(job, pFich, TextInputFormat.class, MaponyGroupNearToTextMap.class);
					}
				}
			} else {
				logger.error(MaponyCte.MSG_NO_DATOS + " '" + getRutaFicheros() + "'");
				return -1;
			}
		} catch (IOException e) {
			logger.error(MaponyCte.MSG_NO_DATOS + " '" + getRutaFicheros() + "'");
			return -1;
		}

		// Combiner de nuestro Job
		job.setCombinerClass(MaponyGroupNearToTextComb.class);
		
		//Reducer de nuestro Job
		job.setReducerClass(MaponyGNArrayToTextRed.class);

		job.setNumReduceTasks(getNumReducers());

		FileOutputFormat.setOutputPath(job, outPath);

		job.waitForCompletion(true);

		getLogger().info(MaponyCte.getMsgFinJob(MaponyCte.jobNameGroupNear));

		return 0;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		loadProperties(MaponyCte.propiedades);

		getLogger().info(MaponyCte.MSG_PROPIEDADES_CARGADAS);

		ToolRunner.run(new MaponyGroupNearToTextJob(), args);
		System.exit(1);

	}

	/**
	 * @return the logger
	 */
	private static final Logger getLogger() {
		return logger;
	}

	/**
	 * @return the rutaFicheros
	 */
	private final String getRutaFicheros() {
		return rutaFicheros;
	}

	/**
	 * @param rutaFicheros
	 *            the rutaFicheros to set
	 */
	private final void setRutaFicheros(String rutaFicheros) {
		this.rutaFicheros = rutaFicheros;
	}

	/**
	 * @return the numReducers
	 */
	private final int getNumReducers() {
		return numReducers;
	}

	/**
	 * @param numReducers the numReducers to set
	 */
	private final void setNumReducers(int numReducers) {
		this.numReducers = numReducers;
	}
}
