package proyecto.utad.mapony.cargaES;

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
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.elasticsearch.hadoop.mr.EsOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proyecto.utad.mapony.cargaES.map.MaponyCargaESMap;
import util.clases.ElasticSearchClient;
import util.clases.GeoHashCiudad;
import util.constantes.MaponyCte;
import util.constantes.MaponyJsonCte;

/**
 * @author Álvaro Sánchez Blasco
 *         <p>
 *         Writing data to Elasticsearch
 *         <p>
 * 
 *         With elasticsearch-hadoop, Map/Reduce jobs can write data to Elasticsearch making it searchable through indexes.
 *         elasticsearch-hadoop supports both (so-called) old and new Hadoop APIs.
 *         <p>
 * 
 *         Tip
 *         <p>
 *         Both Hadoop 1.x and 2.x are supported by the same binary
 *         <p>
 * 
 *         EsOutputFormat expects a Map<Writable, Writable> representing a document value that is converted internally into a JSON document
 *         and indexed in Elasticsearch. Hadoop OutputFormat requires implementations to expect a key and a value however, since for
 *         Elasticsearch only the document (that is the value) is necessary, EsOutputFormat ignores the key.
 *         <p>
 * 
 * 
 */

public class MaponyCargaESJob extends Configured implements Tool {

	private static Properties properties;
	private static String indexES;
	private static String typeES;
	private static String clusterName;
	private static final Logger logger = LoggerFactory.getLogger(MaponyCargaESJob.class);
	private String rutaFicheros;

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
		indexES = properties.getProperty(MaponyCte.indice);
		typeES = properties.getProperty(MaponyCte.tipo);
		clusterName = properties.getProperty(MaponyCte.cluster);
	}

	public int run(String[] args) throws Exception {
		// Lectura de las properties de configuracion
		setRutaFicheros(properties.getProperty(MaponyCte.datos));

		final String ip = properties.getProperty(MaponyCte.ip);
		final String port = properties.getProperty(MaponyCte.puerto);

		// Creamos el job
		Job job = Job.getInstance(getConf(), MaponyCte.jobNameMainJob);
		job.setJarByClass(MaponyCargaESJob.class);

		Configuration config = job.getConfiguration();

		config.setBoolean("mapred.map.tasks.speculative.execution", false);
		config.set("es.mapping.id", MaponyJsonCte.idObject);

		config.set("key.value.separator.in.input.line", " ");
		config.set("es.nodes", ip + ":" + port);
		config.set("es.resource", indexES + "/" + typeES);

		// Output a Elastic Search Output Format
		job.setOutputFormatClass(EsOutputFormat.class);

		// Path pathOrigen = new Path(getRutaFicheros());
		Path pathOrigen = new Path("data/groupNearJobOut/part-r-*");

		// Recuperamos los ficheros que vamos a procesar, y los añadimos como datos de entrada
		final FileSystem fs = FileSystem.get(new URI("hdfs://quickstart.cloudera:8020/"), config);
		try {
			// Recuperamos los datos del path origen (data/*.bz2)
			FileStatus[] glob = fs.globStatus(pathOrigen);

			// Si tenemos datos...
			if (null != glob) {
				if (glob.length > 0) {
					for (FileStatus fileStatus : glob) {
						Path pFich = fileStatus.getPath();
						// MultipleInputs
						MultipleInputs.addInputPath(job, pFich, SequenceFileInputFormat.class, MaponyCargaESMap.class);
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

		// Salida del mapper
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MapWritable.class);

		// Reducer. Este job no hace uso de reducers. La carga de ES se realiza mediante los datos emitidos desde la fase de Map.

		job.waitForCompletion(true);
		logger.info(MaponyCte.getMsgFinJob(MaponyCte.jobNameMainJob));

		return 1;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String args[]) throws Exception {
		// if (args.length != 1) {
		// System.out.printf("Uso: <config.properties file>\n");
		// System.exit(-1);
		// }
		// loadProperties(args[0]);

		loadProperties(MaponyCte.propiedades);

		logger.info(MaponyCte.MSG_PROPIEDADES_CARGADAS);
		new ElasticSearchClient(indexES, typeES, clusterName);
		ToolRunner.run(new MaponyCargaESJob(), args);
		System.exit(1);
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
	private final void setRutaFicheros(final String rutaFicheros) {
		this.rutaFicheros = rutaFicheros;
	}
}
