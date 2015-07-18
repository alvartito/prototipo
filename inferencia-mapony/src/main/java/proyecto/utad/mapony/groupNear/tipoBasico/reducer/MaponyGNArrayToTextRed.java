package proyecto.utad.mapony.groupNear.tipoBasico.reducer;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.writables.array.TextArrayWritable;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class MaponyGNArrayToTextRed extends Reducer<Text, Text, Text, ArrayWritable> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		ArrayList<Text> list = new ArrayList<Text>();
	
		for (Text val : values) {
			list.add(new Text(val));
		}

	    context.write(new Text(key), new TextArrayWritable(list.toArray(new Text[list.size()])));
	}
}
