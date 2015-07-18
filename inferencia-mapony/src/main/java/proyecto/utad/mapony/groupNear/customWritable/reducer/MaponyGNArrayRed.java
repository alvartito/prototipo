package proyecto.utad.mapony.groupNear.customWritable.reducer;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.writables.RawDataWritable;
import util.writables.array.RawDataArrayWritable;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class MaponyGNArrayRed extends Reducer<Text, Text, Text, ArrayWritable> {

	/**
	 * @param key
	 * @param values
	 * @param context
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void reduce(Text key, Iterable<RawDataWritable> values, Context context) throws IOException, InterruptedException {

		ArrayList<RawDataWritable> list = new ArrayList<RawDataWritable>();
	
		for (RawDataWritable val : values) {
			list.add(new RawDataWritable(val));
		}

	    context.write(new Text(key), new RawDataArrayWritable(Text.class, list.toArray(new RawDataWritable[list.size()])));
	}
}