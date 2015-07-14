package proyecto.utad.mapony.inferencia.map;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.constantes.MaponyCte;
import util.writables.ESWritable;
import util.writables.RawDataWritable;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class MaponyInferenciaMapSinArrayWritable extends Mapper<LongWritable, Text, Text, ESWritable> {

	private Text outKey;
	private final Logger logger = LoggerFactory.getLogger(MaponyInferenciaMapSinArrayWritable.class);

	protected void map(LongWritable geoHash, Text values, Context context) throws IOException, InterruptedException {

		try {
				final String[] valores = values.toString().split(MaponyCte.TAB);
				String[] rdString = valores[1].split(MaponyCte.ESCAPED_PIPE);
//				
//				StringTokenizer st = new StringTokenizer(rdString, MaponyCte.ESCAPED_PIPE);
//				String[] datos = new String[st.countTokens()];
//				int i=0;
//				while (st.hasMoreElements()) {
//					String object = (String)st.nextElement();
//					datos[i] = object;
//					i++;
//				}
				
				final RawDataWritable rdBean = new RawDataWritable(rdString);
				outKey = new Text(rdBean.getIdentifier());

				ESWritable cwDescripcion = new ESWritable("descripcion");
				cwDescripcion.setTexto(rdBean.getDescription().toString());

				ESWritable cwFoto = new ESWritable("foto");
				String url = rdBean.getDownloadUrl().toString();
				cwFoto.setTexto(url);

				ESWritable cwGeo = new ESWritable("location");
				StringBuilder posicion = new StringBuilder();
				posicion.append(rdBean.getLatitude());
				posicion.append(",");
				posicion.append(rdBean.getLongitude());
				cwGeo.setTexto(posicion.toString());

				ESWritable cwTags = new ESWritable("tags");
				cwTags.setTexto(rdBean.getMachineTags() + " " + rdBean.getUserTags());

				ESWritable cwTitulo = new ESWritable("titulo");
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
