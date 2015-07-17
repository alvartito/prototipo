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

import proyecto.utad.mapony.fuerzabruta.mapper.FuerzaBrutaMapperToText;
import proyecto.utad.mapony.fuerzabruta.reduce.FuerzaBrutaReducer;
import util.clases.ElasticSearchClient;

public class FuerzaBrutaJobToText extends Configured implements Tool {

	private static Properties properties = null;
	private static String indexES;
	private static String typeES;
	private static String clusterName;
	
	private static void loadProperties(String fileName) throws IOException
	{
		properties = new Properties();
		try {
			FileInputStream in = new FileInputStream(fileName);
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		indexES = properties.getProperty("index_name");
		typeES = properties.getProperty("type_name");
		clusterName = properties.getProperty("clusterName");
	}
	
	
	public int run(String[] args) throws Exception {
		
		System.out.println("MainJob START");
		// Lectura de las properties de configuracion
		String inputIdTitle = "data/sample";
//		String inputIdDescripcion = "data/descripcion/part-r-00000";
//		String inputIdLatLon = "data/latlon/part-r-00000";
//		String inputIdFarmServer = "data/farm/part-r-00000";
//		String inputIdTags = "data/tags/part-r-00000";
		
		String ip = properties.getProperty("ip");
		String port = properties.getProperty("port");
		String numeroReducer = properties.getProperty("numero_reducer");
		
		// Ficheros de entrada
		Path input_1=new Path(inputIdTitle);
//		Path input_2=new Path(inputIdDescripcion);
//		Path input_3=new Path(inputIdLatLon);
//		Path input_4=new Path(inputIdFarmServer);
//		Path input_5=new Path(inputIdTags);
			
		// Creamos el job
		@SuppressWarnings("deprecation")
		Job job = new Job(getConf(), "Flickrjob");
		job.setJarByClass(FuerzaBrutaJobToText.class);
		
		Configuration conf = job.getConfiguration();
//		conf.set("fs.defaultFS", "hdfs://localhost.localdomain:8020");
		conf.set("key.value.separator.in.input.line", " ");
		conf.set("es.nodes",ip + ":" + port);
        conf.set("es.resource", indexES + "/" + typeES);
        
		// MultipleInputs
		MultipleInputs.addInputPath(job, input_1, TextInputFormat.class);

        // Output a ES
 		job.setOutputFormatClass(EsOutputFormat.class);
		
		// Salida del mapper
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		// Salida del reducer
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(MapWritable.class);
		
		// Mapper y Reducer
		job.setMapperClass(FuerzaBrutaMapperToText.class);
		job.setReducerClass(FuerzaBrutaReducer.class);		
		
		// Numero de Reducers
		job.setNumReduceTasks(Integer.parseInt(numeroReducer));
		
		job.waitForCompletion(true);
		System.out.println("MainJob FINISH SUCCESS");
		
		return 1;
	}
	
	
	public static void main(String args[]) throws Exception {
		loadProperties("job.properties");
		System.out.println("Propiedades cargadas");
		new ElasticSearchClient(indexES, typeES, clusterName);
		ToolRunner.run(new FuerzaBrutaJobToText(), args);
		System.exit(1);
	}

}

