package proyecto.utad.mapony.prototipo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.elasticsearch.hadoop.mr.EsOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proyecto.utad.mapony.groupNear.map.MaponyGroupNearMap;
import proyecto.utad.mapony.prototipo.com.CombinerPrototipo;
import proyecto.utad.mapony.prototipo.map.MapperPrototipo;
import proyecto.utad.mapony.prototipo.red.ReducerPrototipo;
import util.ElasticSearchClient;
import util.GeoHashCiudad;
import util.constantes.MaponyCte;

public class PrototipoJob extends Configured implements Tool {

	private static Properties properties;
	private static String indexES;
	private static String typeES;
	private static String clusterName;
	private static final Logger logger = LoggerFactory.getLogger(PrototipoJob.class);
	
	public static void main(String args[]) throws Exception {
		loadProperties(MaponyCte.propiedades);
		
		logger.info(MaponyCte.MSG_PROPIEDADES_CARGADAS);

		new ElasticSearchClient(indexES, typeES, clusterName);

		ToolRunner.run(new PrototipoJob(), args);
		System.exit(1);
	}
	
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

		new GeoHashCiudad(properties.getProperty(MaponyCte.paises));
	}
	
	@Override
	public int run(String[] arg0) throws Exception {

		final String ip = properties.getProperty(MaponyCte.ip);
		final String port = properties.getProperty(MaponyCte.puerto);
		final int numeroReducer = Integer.parseInt(properties.getProperty(MaponyCte.reducers));

		Configuration config = getConf();
		config.setBoolean("mapred.map.tasks.speculative.execution", false);
		config.setBoolean("mapred.reduce.tasks.speculative.execution", false);
		config.set("es.input.json", "yes");
		config.set("key.value.separator.in.input.line", " ");
		config.set("es.nodes", ip + ":" + port);
		config.set("es.resource", indexES + "/" + typeES);

		Job job = Job.getInstance(config, MaponyCte.jobPrototipo);
		job.setJarByClass(PrototipoJob.class);

		job.setInputFormatClass(TextInputFormat.class);
		
		// Output a Elastic Search Output Format
		job.setOutputFormatClass(EsOutputFormat.class);

		// Salida del mapper
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		// Salida del reducer
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(MapWritable.class);

		job.setCombinerClass(CombinerPrototipo.class);
		job.setReducerClass(ReducerPrototipo.class);

		job.setNumReduceTasks(numeroReducer);
		
		MultipleInputs.addInputPath(job, new Path("data/sample"), TextInputFormat.class, MapperPrototipo.class);

		job.waitForCompletion(true);
		logger.info(MaponyCte.getMsgFinJob(MaponyCte.jobNameMainJob));

		return 1;
	}

	
	
}
