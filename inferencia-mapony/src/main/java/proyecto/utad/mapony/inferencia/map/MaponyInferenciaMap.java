package proyecto.utad.mapony.inferencia.map;

import java.io.IOException;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.constantes.MaponyCte;
import util.writables.ESWritable;
import util.writables.RawDataWritable;

public class MaponyInferenciaMap extends Mapper<Text, ArrayWritable, Text, ESWritable> {

	private Text outKey;
	private final Logger logger = LoggerFactory.getLogger(MaponyInferenciaMap.class);

	@SuppressWarnings("unused")
	protected void map(Text geoHash, ArrayWritable values, Context context)
			throws IOException, InterruptedException {

		try {
			Writable[] tmp = values.get();
			
			values.toString().split("");

			RawDataWritable rdBean = new RawDataWritable();
			
			outKey = new Text(rdBean.getIdentifier());

			ESWritable cwDescripcion = new ESWritable(MaponyCte.descripcionObject);
			cwDescripcion.setTexto(rdBean.getDescription().toString());

			ESWritable cwFoto = new ESWritable(MaponyCte.fotoObject);
			String url = rdBean.getDownloadUrl().toString();
			cwFoto.setTexto(url);

			ESWritable cwGeo = new ESWritable(MaponyCte.locationObject);
			StringBuilder posicion = new StringBuilder();
			posicion.append(rdBean.getLatitude());
			posicion.append(",");
			posicion.append(rdBean.getLongitude());
			cwGeo.setTexto(posicion.toString());

			ESWritable cwUserTags = new ESWritable(MaponyCte.userTagsObject);
			cwUserTags.setTexto(rdBean.getUserTags().toString());

			ESWritable cwMachineTags = new ESWritable(MaponyCte.machineTagsObject);
			cwMachineTags.setTexto(rdBean.getMachineTags().toString());

			ESWritable cwTitulo = new ESWritable(MaponyCte.tituloObject);
			cwTitulo.setTexto(rdBean.getTitle().toString());

			ESWritable cwCiudad = new ESWritable(MaponyCte.ciudadObject);
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
