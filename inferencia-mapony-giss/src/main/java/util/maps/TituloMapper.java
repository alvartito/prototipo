package util.maps;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import util.writables.ESWritable;

public class TituloMapper extends Mapper<Text, Text, Text, ESWritable> {
		
		public void map(Text k, Text v, Context con)
				throws IOException, InterruptedException {
			String line = v.toString();
			ESWritable value = new ESWritable("titulo");
			value.setTexto(line.toLowerCase());
			con.write(new Text(k), value); 
		}
}
