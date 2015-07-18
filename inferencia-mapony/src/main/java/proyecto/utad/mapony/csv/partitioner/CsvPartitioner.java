package proyecto.utad.mapony.csv.partitioner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import util.constantes.MaponyCte;
import util.writables.CsvWritable;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class CsvPartitioner extends
Partitioner<Text, CsvWritable>{

	@Override
	public int getPartition(Text key,
			CsvWritable value, int numPartitions) {
		if (value.getCiudad().toString().compareTo(MaponyCte.sLondres) == 0) {
			return 0;
		} else if(value.getCiudad().toString().compareTo(MaponyCte.sBerlin) == 0){
			return 1;
		} else if(value.getCiudad().toString().compareTo(MaponyCte.sMadrid) == 0){
			return 2;
		} else {
			return 3;
		}
	}
	
}
