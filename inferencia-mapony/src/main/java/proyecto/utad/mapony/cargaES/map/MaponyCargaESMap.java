package proyecto.utad.mapony.cargaES.map;

import java.io.IOException;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.patrones.ReservoirSampler;
import util.writables.RawDataWritable;
import util.writables.array.TextArrayWritable;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class MaponyCargaESMap extends Mapper<Text, TextArrayWritable, Text, MapWritable> {

	private Text outKey;
	private final Logger logger = LoggerFactory.getLogger(MaponyCargaESMap.class);

	@SuppressWarnings("unused")
	protected void map(Text geoHash, TextArrayWritable values, Context context)
			throws IOException, InterruptedException {

		try {
			Writable[] tmp = values.get();
			if(tmp.length>20){
				ReservoirSampler<Text> r = new ReservoirSampler<Text>(tmp.length/2);
				for (Writable writable : tmp) {
					//Cada elemento del array es de tipo Text
					r.sample(new Text(((Text)writable).toString())); 
				}
				
				Iterable<Text> samples = r.getSamples();
				for (Text text : samples) {
					
				}
			}

			RawDataWritable rdBean = new RawDataWritable();
			
			outKey = new Text(rdBean.getIdentifier());

			MapWritable mwr = new MapWritable();

			//TODO Cambiar para acceder a los datos del CustomWritable
//			mwr.put(new Text(MaponyJsonCte.idObject), new Text(dato[0]));
//			mwr.put(new Text(MaponyJsonCte.tituloObject), new Text(dato[6]));
//			mwr.put(new Text(MaponyJsonCte.descripcionObject), new Text(dato[7]));
//			mwr.put(new Text(MaponyJsonCte.userTagsObject), new Text(MaponyUtil.cleanString(dato[8])));
//			mwr.put(new Text(MaponyJsonCte.machineTagsObject), new Text(MaponyUtil.cleanString(dato[9])));
//			mwr.put(new Text(MaponyJsonCte.locationObject), new Text(dato[11] + MaponyCte.COMA + dato[10]));
//			mwr.put(new Text(MaponyJsonCte.fotoObject), new Text(dato[14]));
//			mwr.put(new Text(MaponyJsonCte.captureDeviceObject), new Text(MaponyUtil.cleanStringCaptureDevice(dato[5])));
//
//
//			context.write(new Text(key), mwr);
		} catch (Exception e) {
			getLogger().error(e.getMessage());
		}
	}

	/**
	 * @return the logger
	 */
	private final Logger getLogger() {
		return logger;
	};
}
