package proyecto.utad.mapony.groupNear.tipoBasico.combiner;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaponyGroupNearToTextComb extends Reducer<Text, Text, Text, Text> {

	protected void reduce(Text clave, Iterable<Text> valores, Context context) throws IOException, InterruptedException {
		for (Text valor : valores) {
			context.write(new Text(clave.toString()), new Text(valor.toString()));	
		}
	};
}