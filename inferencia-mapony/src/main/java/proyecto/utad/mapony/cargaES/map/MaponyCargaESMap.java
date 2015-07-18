package proyecto.utad.mapony.cargaES.map;

import java.io.IOException;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.constantes.MaponyCte;
import util.constantes.MaponyJsonCte;
import util.writables.array.TextArrayWritable;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class MaponyCargaESMap extends Mapper<Text, TextArrayWritable, Text, MapWritable> {

	private final Logger logger = LoggerFactory.getLogger(MaponyCargaESMap.class);

	protected void map(Text geoHash, TextArrayWritable values, Context context)
			throws IOException, InterruptedException {

		try {
			Writable[] tmp = values.get();
			for (Writable writable : tmp) {
				Text t = new Text(((Text)writable).toString());

				/**
				 * @param identifier
				 * @param dateTaken
				 * @param captureDevice
				 * @param title
				 * @param description
				 * @param userTags
				 * @param machineTags
				 * @param longitude
				 * @param latitude
				 * @param downloadUrl
				 * @param geoHash
				 * @param geoHashCiudad
				 * @param continente
				 * @param pais
				 * @param ciudad
				 */
				String[] dato = t.toString().split(MaponyCte.ESCAPED_PIPE);
				if(dato.length == 14){
					MapWritable mwr = new MapWritable();
					Text key = new Text(dato[0]);
					//TODO Cambiar para acceder a los datos del CustomWritable
					mwr.put(new Text(MaponyJsonCte.idObject), key);
					mwr.put(new Text(MaponyJsonCte.tituloObject), new Text(dato[3]));
					mwr.put(new Text(MaponyJsonCte.descripcionObject), new Text(dato[4]));
					mwr.put(new Text(MaponyJsonCte.userTagsObject), new Text(dato[5]));
					mwr.put(new Text(MaponyJsonCte.machineTagsObject), new Text(dato[6]));
					mwr.put(new Text(MaponyJsonCte.locationObject), new Text(dato[8] + MaponyCte.COMA + dato[7]));
					mwr.put(new Text(MaponyJsonCte.fotoObject), new Text(dato[9]));
					mwr.put(new Text(MaponyJsonCte.captureDeviceObject), new Text(dato[2]));
					mwr.put(new Text(MaponyJsonCte.fechaCapturaObject), new Text(dato[1]));
					mwr.put(new Text(MaponyJsonCte.ciudadObject), new Text(dato[13]));
	
					context.write(new Text(key), new MapWritable(mwr));
				}
			}
			
//			
////			RawDataWritable rdBean = new RawDataWritable();
////			
////			outKey = new Text(rdBean.getIdentifier());
//
//			MapWritable mwr = new MapWritable();
//
//			//TODO Cambiar para acceder a los datos del CustomWritable
////			mwr.put(new Text(MaponyJsonCte.idObject), new Text(dato[0]));
////			mwr.put(new Text(MaponyJsonCte.tituloObject), new Text(dato[6]));
////			mwr.put(new Text(MaponyJsonCte.descripcionObject), new Text(dato[7]));
////			mwr.put(new Text(MaponyJsonCte.userTagsObject), new Text(MaponyUtil.cleanString(dato[8])));
////			mwr.put(new Text(MaponyJsonCte.machineTagsObject), new Text(MaponyUtil.cleanString(dato[9])));
////			mwr.put(new Text(MaponyJsonCte.locationObject), new Text(dato[11] + MaponyCte.COMA + dato[10]));
////			mwr.put(new Text(MaponyJsonCte.fotoObject), new Text(dato[14]));
////			mwr.put(new Text(MaponyJsonCte.captureDeviceObject), new Text(MaponyUtil.cleanStringCaptureDevice(dato[5])));
////
////
////			context.write(new Text(key), mwr);
		} catch (Exception e) {
			getLogger().error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the logger
	 */
	private final Logger getLogger() {
		return logger;
	};
}
