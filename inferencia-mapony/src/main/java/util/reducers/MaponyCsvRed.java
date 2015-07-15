package util.reducers;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import util.writables.CsvWritable;

public class MaponyCsvRed extends Reducer<Text, CsvWritable, Text, Text> {
	
	
	private MultipleOutputs<Text, Text> multipleOut;
	
	protected void reduce(Text clave, CsvWritable valor, Context context) throws IOException, InterruptedException {
//		context.write(clave, new Text(valor.toString()));
		multipleOut.write(clave, new Text(valor.toString()), valor.getCiudad().toString());
	};
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		multipleOut = new MultipleOutputs<Text, Text>(context);
	}
}