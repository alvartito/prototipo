package proyecto.utad.mapony.prototipo.com;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CombinerPrototipo extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> value, Context context) throws IOException, InterruptedException {
		for (Text val : value) {
			context.write(key, val);
		}
	}
}
