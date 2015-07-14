package util.maps;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import util.writables.CustomWritable;

public class GeoMapper extends Mapper<Text, Text, Text, CustomWritable> {
		
		public void map(Text k, Text v, Context con)
				throws IOException, InterruptedException {
			String[] line = v.toString().split(" ");
			if (line.length == 3){
				String posicion = line[0] + "," + line[1];
				CustomWritable value = new CustomWritable("location");
				value.setTexto(posicion);
				con.write(new Text(k), value); 
			}
		}
}
