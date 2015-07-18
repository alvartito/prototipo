package proyecto.utad.mapony.groupNear.tipoBasico.reducer;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.patrones.ReservoirSampler;
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

		Text[] arrayDeText = list.toArray(new Text[list.size()]);
		
		if(arrayDeText.length >= 20){
			if(arrayDeText.length >= 30){
				ReservoirSampler<Text> r = new ReservoirSampler<>(arrayDeText.length/2);
				for (Text text : arrayDeText) {
					r.sample(new Text(text));
				}
			
				Iterable<Text> samplesToEmit = r.getSamples();
				ArrayList<Text> emit = new ArrayList<Text>();
				for (Text text : samplesToEmit) {
					emit.add(new Text(text));
				}
				
				context.write(new Text(key), new TextArrayWritable(emit.toArray(new Text[emit.size()])));
			} else {
				context.write(new Text(key), new TextArrayWritable(list.toArray(new Text[list.size()])));
			}
		}
	}
}
