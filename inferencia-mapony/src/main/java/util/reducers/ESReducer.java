package util.reducers;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.constantes.MaponyCte;
import util.writables.ESWritable;

public class ESReducer extends Reducer<Text, ESWritable, Text, MapWritable>
{

	public void reduce(Text key, Iterable<ESWritable> text, Context con)
            throws IOException , InterruptedException
    {
       Iterator<ESWritable> valuesIt = text.iterator();
       boolean completo = false;
       MapWritable mapWritable = new MapWritable();
       while(valuesIt.hasNext()){
    	   ESWritable cw = valuesIt.next();
    	   if (cw.getTipo().toString().equals(MaponyCte.locationObject)){
    		   completo = true;	   
    	   }
    	   mapWritable.put(new Text(cw.getTipo()), new Text(cw.getTexto()));
       }
       mapWritable.put(new Text(MaponyCte.idObject), key);
       if (completo)
    	  con.write(key, mapWritable);
    }
}
