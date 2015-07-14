package proyecto.utad.mapony.groupNear;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proyecto.utad.mapony.groupNear.map.MaponyGroupNearMap;
import util.GeoHashCiudad;
import util.constantes.MaponyCte;
import util.reducers.MaponyGNArrayRed;
import util.reducers.MaponyGroupNearRed;
import util.writables.RawDataArrayWritable;
import util.writables.RawDataWritable;

/**
 * @author �lvaro S�nchez Blasco
 *
 */
public class MaponyGroupNearJob extends Configured implements Tool {

	private static Properties properties;
	private static final Logger logger = LoggerFactory.getLogger(MaponyGroupNearJob.class);
	private String rutaFicheros;

	private static void loadProperties(final String fileName) throws IOException {
		//Cargamos el fichero de propiedades
		if (null == properties) {
			properties = new Properties();
		}
		try {
			FileInputStream in = new FileInputStream(fileName);
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Cargamos en memoria el hashmap con la relaci�n de ciudades con m�s de 1000 habitantes
		new GeoHashCiudad(properties.getProperty(MaponyCte.paises));
	}
	
	
	public int run(String[] args) throws Exception {
		// Ruta donde buscaremos los ficheros
		setRutaFicheros(properties.getProperty(MaponyCte.datos));

		Configuration config = getConf();

//		Path pathOrigen = new Path(properties.getProperty(getRutaFicheros()));
		Path outPath = new Path(properties.getProperty(MaponyCte.salida_group_near));

//		final FileSystem fs = FileSystem.get(new URI("hdfs://quickstart.cloudera:8020/"), config);
		
		// Borramos todos los directorios que puedan existir
		FileSystem.get(outPath.toUri(), config).delete(outPath, true);
		
		Job job = Job.getInstance(config, MaponyCte.jobNameGroupNear);
		job.setJarByClass(MaponyGroupNearJob.class);

		job.setInputFormatClass(TextInputFormat.class);
		//La salida del job ser� un fichero de texto
		job.setOutputFormatClass(TextOutputFormat.class);		
//		job.setOutputFormatClass(SequenceFileOutputFormat.class);
//		SequenceFileOutputFormat.setCompressOutput(job, true);
//		SequenceFileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
//		SequenceFileOutputFormat.setOutputCompressionType(job, CompressionType.BLOCK);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(RawDataWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(RawDataArrayWritable.class);

		// Recuperamos el path origen
//		FileStatus[] glob = fs.globStatus(pathOrigen);

		// Si tenemos datos...
//		if (null != glob) {
//			if (glob.length > 0) {
//				for (FileStatus fileStatus : glob) {
//					//TODO Comprobar que estoy recuperando bien los datos, o buscar la forma de hacerlo correctamente.
//					Path pFich = fileStatus.getPath();
//					// MultipleInputs. La salida del Job ser� un archivo de texto.
//					MultipleInputs.addInputPath(job, pFich, TextInputFormat.class, MaponyGroupNearMap.class);
//				}
//			} else {
//				logger.info(MaponyCte.MSG_NO_DATOS);
//			}
//		} else {
//			logger.info(MaponyCte.MSG_NO_DATOS);
//		}
		
		MultipleInputs.addInputPath(job, new Path("data/sample"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-0.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-1.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-2.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-3.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-4.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-5.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-6.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-7.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-8.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);
//		MultipleInputs.addInputPath(job, new Path("data/yfcc100m_dataset-9.bz2"), TextInputFormat.class, MaponyGroupNearMap.class);

		job.setCombinerClass(MaponyGroupNearRed.class);
		job.setReducerClass(MaponyGNArrayRed.class);

		job.setNumReduceTasks(1);
		
		FileOutputFormat.setOutputPath(job, outPath);

		logger.info(MaponyCte.getMsgFinJob(MaponyCte.jobNameGroupNear));

		boolean success = job.waitForCompletion(true);
		System.exit(success ? 0 : 1);
		return success ? 0 : 1;
	}

	public static void main(String args[]) throws Exception {
		//Cargamos las propiedades
		loadProperties(MaponyCte.propiedades);
		
		logger.info(MaponyCte.MSG_PROPIEDADES_CARGADAS);

		System.exit(ToolRunner.run(new MaponyGroupNearJob(), args));
	}

//	/**
//	 * @return the rutaFicheros
//	 */
//	private final String getRutaFicheros() {
//		return rutaFicheros;
//	}


	/**
	 * @param rutaFicheros the rutaFicheros to set
	 */
	private final void setRutaFicheros(String rutaFicheros) {
		this.rutaFicheros = rutaFicheros;
	}
}
