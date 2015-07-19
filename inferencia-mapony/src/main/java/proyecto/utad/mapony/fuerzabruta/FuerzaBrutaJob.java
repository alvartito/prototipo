package proyecto.utad.mapony.fuerzabruta;
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

import proyecto.utad.mapony.fuerzabruta.mapper.FuerzaBrutaMapper;
import proyecto.utad.mapony.fuerzabruta.mapper.FuerzaBrutaMapperToText;
import proyecto.utad.mapony.fuerzabruta.reduce.FuerzaBrutaReducer;
import util.clases.ElasticSearchClient;
import util.clases.GeoHashCiudad;
import util.constantes.MaponyCte;

public class FuerzaBrutaJob extends Configured implements Tool {

	private static Properties properties;
	private static String indexES;
	private static String typeES;
	private static String clusterName;
	private static final Logger logger = LoggerFactory.getLogger(FuerzaBrutaJob.class);

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
		//TODO Maravilloso GeoHashCiudad
//		new GeoHashCiudad(properties.getProperty(MaponyCte.paises));
	}

	public int run(String[] args) throws Exception {

		// Creamos el job
		Job job = Job.getInstance(getConf(), MaponyCte.jobFuerzaBruta);
		job.setJarByClass(FuerzaBrutaJob.class);
		logger.info(MaponyCte.getMsgInicioJob(MaponyCte.jobFuerzaBruta));

		Configuration conf = job.getConfiguration();
		
		// MultipleInputs
		MultipleInputs.addInputPath(job, new Path("data/sample"), TextInputFormat.class);
        
		// Output a Elastic Search Output Format
		job.setOutputFormatClass(EsOutputFormat.class);

		String ip = properties.getProperty("ip");
		String port = properties.getProperty("port");
		String numeroReducer = properties.getProperty(MaponyCte.reducers);

//		conf.setBoolean("mapred.map.tasks.speculative.execution", false);
//		conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
//		conf.set("es.input.json", "yes");
		conf.set("key.value.separator.in.input.line", " ");
		conf.set("es.nodes", ip + ":" + port);
		conf.set("es.resource", indexES + "/" + typeES);

		// Salida del mapper
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MapWritable.class);

//		// Salida del reducer
//		job.setOutputKeyClass(Text.class);
//		job.setOutputValueClass(MapWritable.class);
		
		// Mapper y Reducer
		job.setMapperClass(FuerzaBrutaMapper.class);
//		job.setReducerClass(FuerzaBrutaReducer.class);

		// Numero de Reducers
//		job.setNumReduceTasks(Integer.parseInt(numeroReducer));
		
		job.waitForCompletion(true);
		logger.info(MaponyCte.getMsgFinJob(MaponyCte.jobFuerzaBruta));
		
		return 1;
	}

	public static void main(String args[]) throws Exception {
		loadProperties(MaponyCte.propiedades);
		logger.info(MaponyCte.MSG_PROPIEDADES_CARGADAS);
		new ElasticSearchClient(indexES, typeES, clusterName);
		ToolRunner.run(new FuerzaBrutaJob(), args);
		System.exit(1);
	}

}

