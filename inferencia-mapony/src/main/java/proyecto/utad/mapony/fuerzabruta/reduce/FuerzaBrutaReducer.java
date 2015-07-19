package proyecto.utad.mapony.fuerzabruta.reduce;

import java.io.IOException;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.clases.MaponyUtil;
import util.constantes.MaponyCte;
import util.constantes.MaponyJsonCte;

/**
 * Recuperamos los datos, y del value, que tiene una estructura:
 * <p>
 * titulo|descripcion|userTag|machineTag|posicion|foto|captureDevice
 * <p>
 * Rellenamos un MapWritable con cada par, clave (el campo asociado del json a completar), y valor, para finalmente, a�adirlo al MapWritable que
 * emitiremos, con clave la key, y valor, el MapWritable con los datos.
 * */
public class FuerzaBrutaReducer extends Reducer<Text, Text, Text, MapWritable> {
	public void reduce(Text key, Text value, Context context) throws IOException, InterruptedException {
		String dato[] = value.toString().split(MaponyCte.ESCAPED_PIPE);
		
		MapWritable mwr = new MapWritable();
		
		mwr.put(new Text(MaponyJsonCte.idObject), new Text(dato[0]));
		mwr.put(new Text(MaponyJsonCte.tituloObject), new Text(dato[6]));
		mwr.put(new Text(MaponyJsonCte.descripcionObject), new Text(dato[7]));
		mwr.put(new Text(MaponyJsonCte.userTagsObject), new Text(MaponyUtil.cleanString(dato[8])));
		mwr.put(new Text(MaponyJsonCte.machineTagsObject), new Text(MaponyUtil.cleanString(dato[9])));
		mwr.put(new Text(MaponyJsonCte.locationObject), new Text(dato[11] + MaponyCte.COMA + dato[10]));
		mwr.put(new Text(MaponyJsonCte.fotoObject), new Text(dato[14]));
		mwr.put(new Text(MaponyJsonCte.captureDeviceObject), new Text(MaponyUtil.cleanString(dato[5])));


		context.write(new Text(key), mwr);
	}
}
