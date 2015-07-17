package util.reducers;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.writables.RawDataWritable;

public class MaponyGroupNearToTextRed extends Reducer<Text, RawDataWritable, Text, RawDataWritable> {

	protected void reduce(Text clave, Iterable<RawDataWritable> valor, Context context) throws IOException, InterruptedException {
		for (RawDataWritable rawDataWritable : valor) {
			context.write(clave, rawDataWritable);	
		}
	};
}