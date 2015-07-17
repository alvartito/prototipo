package util.writables;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class TextArrayWritable extends ArrayWritable {
	public TextArrayWritable() {
		super(Text.class);
	}

	public TextArrayWritable(Text[] strings) {
		super(Text.class, strings);
	}
	
	public TextArrayWritable(Text valueClass, Text[] values) {
		super(valueClass.getClass(), values);
	}
	
	public Writable[] get() {
		return (Writable[]) super.get();
	}

//	@Override
//	public void write(DataOutput arg0) throws IOException {
//	    for(Text i : get()){
//	        i.write(arg0);
//	    }
//	}
	
	@Override
	public String toString() {
		return Arrays.toString(get());
	}
	
}
