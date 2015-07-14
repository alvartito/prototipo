package proyecto.utad.mapony.inferencia.map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.writables.CustomWritable;
import util.writables.RawDataWritable;

public class MaponyInferenciaMap extends Mapper<LongWritable, Text, Text, CustomWritable> {

	private Text outKey;
	private final Logger logger = LoggerFactory.getLogger(MaponyInferenciaMap.class);

	protected void map(LongWritable key, Text data, Context context) throws IOException, InterruptedException {

		try {
			String[] datos = data.toString().split("\t");
			String[] valoresRDBean = datos[1].split("\\|");
			RawDataWritable rdBean = new RawDataWritable(valoresRDBean);

			outKey = new Text(rdBean.getIdentifier());

			CustomWritable cwDescripcion = new CustomWritable("descripcion");
			cwDescripcion.setTexto(rdBean.getDescription().toString());

			CustomWritable cwFoto = new CustomWritable("foto");
			String url = rdBean.getDownloadUrl().toString();
			cwFoto.setTexto(url);

			CustomWritable cwGeo = new CustomWritable("location");
			StringBuilder posicion = new StringBuilder();
			posicion.append(rdBean.getLatitude());
			posicion.append(",");
			posicion.append(rdBean.getLongitude());
			cwGeo.setTexto(posicion.toString());

			CustomWritable cwTags = new CustomWritable("tags");
			String tags = rdBean.getMachineTags() + " " + rdBean.getUserTags();
			if(tags.length() > 200) {
				tags = tags.substring(0, 200);
			}
			cwTags.setTexto(tags);

			CustomWritable cwTitulo = new CustomWritable("titulo");
			cwTitulo.setTexto(rdBean.getTitle().toString());

			context.write(outKey, cwDescripcion);
			context.write(outKey, cwFoto);
			context.write(outKey, cwGeo);
			context.write(outKey, cwTags);
			context.write(outKey, cwTitulo);
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
