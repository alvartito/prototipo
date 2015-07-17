package proyecto.utad.mapony.inferencia.map;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.constantes.MaponyJsonCte;
import util.patrones.ReservoirSampler;
import util.writables.ESWritable;
import util.writables.RawDataWritable;
import util.writables.TextArrayWritable;

public class MaponyInferenciaMap extends Mapper<Text, TextArrayWritable, Text, ESWritable> {

	private Text outKey;
	private final Logger logger = LoggerFactory.getLogger(MaponyInferenciaMap.class);

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

			ESWritable cwDescripcion = new ESWritable(MaponyJsonCte.descripcionObject);
			cwDescripcion.setTexto(rdBean.getDescription().toString());

			ESWritable cwFoto = new ESWritable(MaponyJsonCte.fotoObject);
			String url = rdBean.getDownloadUrl().toString();
			cwFoto.setTexto(url);

			ESWritable cwGeo = new ESWritable(MaponyJsonCte.locationObject);
			StringBuilder posicion = new StringBuilder();
			posicion.append(rdBean.getLatitude());
			posicion.append(",");
			posicion.append(rdBean.getLongitude());
			cwGeo.setTexto(posicion.toString());

			ESWritable cwUserTags = new ESWritable(MaponyJsonCte.userTagsObject);
			cwUserTags.setTexto(rdBean.getUserTags().toString());

			ESWritable cwMachineTags = new ESWritable(MaponyJsonCte.machineTagsObject);
			cwMachineTags.setTexto(rdBean.getMachineTags().toString());

			ESWritable cwTitulo = new ESWritable(MaponyJsonCte.tituloObject);
			cwTitulo.setTexto(rdBean.getTitle().toString());

			ESWritable cwCiudad = new ESWritable(MaponyJsonCte.ciudadObject);
			cwCiudad.setTexto(rdBean.getCiudad().toString());

			context.write(outKey, cwDescripcion);
			context.write(outKey, cwFoto);
			context.write(outKey, cwGeo);
			context.write(outKey, cwUserTags);
			context.write(outKey, cwMachineTags);
			context.write(outKey, cwTitulo);
			context.write(outKey, cwCiudad);
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
