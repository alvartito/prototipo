package util.reducers;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.constantes.MaponyCte;
import util.constantes.MaponyJsonCte;
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
    	   ESWritable esw = valuesIt.next();
    	   if (esw.getTipo().toString().equals(MaponyJsonCte.locationObject)){
    		   completo = true;	   
    	   }
    	   mapWritable.put(new Text(esw.getTipo()), new Text(esw.getTexto()));
       }
       mapWritable.put(new Text(MaponyJsonCte.idObject), key);
       if (completo)
    	  con.write(key, mapWritable);
    }
}
