package util.maps;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import util.writables.ESWritable;

public class DescripcionMapper extends Mapper<Text, Text, Text, ESWritable> {
		
		public void map(Text k, Text v, Context con)
				throws IOException, InterruptedException {
			String line = v.toString();
			ESWritable value = new ESWritable("descripcion");
			value.setTexto(line.toLowerCase());
			con.write(new Text(k), value); 
		}
}
