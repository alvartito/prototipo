package proyecto.utad.mapony.prototipo.red;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import proyecto.utad.mapony.prototipo.writable.TextArrayWritable;

public class ReducerPrototipo extends Reducer<Text, Text, Text, TextArrayWritable> {

	protected void map(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		ArrayList<Text> list = new ArrayList<Text>();    
	    for (Text val : values) {
	        list.add(val);
	    }
	    context.write(key, new TextArrayWritable(list.toArray(new Text[list.size()])));
	}
}
