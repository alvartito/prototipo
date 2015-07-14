package proyecto.utad.mapony.prototipo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
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

import proyecto.utad.mapony.prototipo.com.CombinerPrototipo;
import proyecto.utad.mapony.prototipo.map.MapperPrototipo;
import proyecto.utad.mapony.prototipo.red.ReducerPrototipo;
import proyecto.utad.mapony.prototipo.writable.TextArrayWritable;
import util.clases.GeoHashCiudad;
import util.constantes.MaponyCte;

public class PrototipoToTextJob extends Configured implements Tool {

	private static Properties properties;
	private static final Logger logger = LoggerFactory.getLogger(PrototipoToTextJob.class);
	
	public static void main(String args[]) throws Exception {
		loadProperties(MaponyCte.propiedades);
		
		logger.info(MaponyCte.MSG_PROPIEDADES_CARGADAS);

		ToolRunner.run(new PrototipoToTextJob(), args);
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

		new GeoHashCiudad(properties.getProperty(MaponyCte.paises));
	}
	
	@Override
	public int run(String[] arg0) throws Exception {

		Configuration config = getConf();

		Job job = Job.getInstance(config, MaponyCte.jobPrototipo);
		job.setJarByClass(PrototipoToTextJob.class);

		Path outPath = new Path(properties.getProperty(MaponyCte.salida_group_near));

		// Borramos todos los directorios que puedan existir
		FileSystem.get(outPath.toUri(), config).delete(outPath, true);

		FileOutputFormat.setOutputPath(job, outPath);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// Salida del mapper
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		// Salida del reducer
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TextArrayWritable.class);

		job.setReducerClass(CombinerPrototipo.class);
		job.setReducerClass(ReducerPrototipo.class);

		job.setNumReduceTasks(1);
		
		MultipleInputs.addInputPath(job, new Path("data/sample"), TextInputFormat.class, MapperPrototipo.class);

		job.waitForCompletion(true);
		logger.info(MaponyCte.getMsgFinJob(MaponyCte.jobNameMainJob));

		return 1;
	}

	
	
}
