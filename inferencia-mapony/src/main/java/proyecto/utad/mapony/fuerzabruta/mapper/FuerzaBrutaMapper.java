package proyecto.utad.mapony.fuerzabruta.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import util.clases.MaponyUtil;
import util.constantes.MaponyCte;
import util.constantes.MaponyJsonCte;

public class FuerzaBrutaMapper extends Mapper<LongWritable, Text, Text, MapWritable> {
	protected void map(LongWritable key, Text line, Context context) throws IOException, InterruptedException {

		String[] dato = line.toString().split("\t");

		// Comenzamos por limpiar las referencias de videos, por lo que el campo [21] del String[] ha de ser
		// '0' para que lo procesemos.
		// Además, si no tiene informados los campos de longitud y latitud, también descartamos el registro.
		if ("1".toString().compareTo(dato[22]) != 0
				&& (MaponyCte.VACIO.compareTo(dato[10]) != 0 && MaponyCte.VACIO.compareTo(dato[11]) != 0)) {
			MapWritable mwr = new MapWritable();
			
			mwr.put(new Text(MaponyJsonCte.idObject), new Text(dato[0]));
			mwr.put(new Text(MaponyJsonCte.tituloObject), new Text(dato[6]));
			mwr.put(new Text(MaponyJsonCte.descripcionObject), new Text(dato[7]));
			mwr.put(new Text(MaponyJsonCte.userTagsObject), new Text(MaponyUtil.cleanString(dato[8])));
			mwr.put(new Text(MaponyJsonCte.machineTagsObject), new Text(MaponyUtil.cleanString(dato[9])));
			mwr.put(new Text(MaponyJsonCte.locationObject), new Text(dato[11] + MaponyCte.COMA + dato[10]));
			mwr.put(new Text(MaponyJsonCte.fotoObject), new Text(dato[14]));
			mwr.put(new Text(MaponyJsonCte.captureDeviceObject), new Text(MaponyUtil.cleanString(dato[5])));

			context.write(new Text(dato[0]), mwr);
		}
	}
}
