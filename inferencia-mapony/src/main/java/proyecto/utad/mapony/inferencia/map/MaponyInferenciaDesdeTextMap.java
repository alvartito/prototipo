package proyecto.utad.mapony.inferencia.map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.constantes.MaponyCte;
import util.constantes.MaponyJsonCte;
import util.writables.ESWritable;
import util.writables.RawDataWritable;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class MaponyInferenciaDesdeTextMap extends Mapper<LongWritable, Text, Text, ESWritable> {

	private Text outKey;
	private final Logger logger = LoggerFactory.getLogger(MaponyInferenciaDesdeTextMap.class);

	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		try {
			//Value.
			//Tendremos registros del tipo:
			//geohash\t[RawDataWritable, RawDataWritable, ...]
			//Separamos los datos en clave y valores asociados:
			String[] datos = value.toString().split(MaponyCte.TAB);
			String[] valores = datos[1].split(MaponyCte.COMA);
			//Para cada dato contenido en valores[] 
			for (String string : valores) {
				// Como no trabajamos directamente con ArrayWritable, sino con
				// su representación en String, los caracteres de inicio y fin de
				// colección hay que eliminarlos.
				string = string.replaceAll("\\[", "");
				string = string.replaceAll("\\]", "");
				String[] sRawDataWritable = string.split(MaponyCte.ESCAPED_PIPE);
				
				RawDataWritable rdBean = new RawDataWritable(sRawDataWritable);

				outKey = new Text(rdBean.getIdentifier());

				ESWritable eswDescripcion = new ESWritable(MaponyJsonCte.descripcionObject);
				eswDescripcion.setTexto(rdBean.getDescription().toString());

				ESWritable eswFoto = new ESWritable(MaponyJsonCte.fotoObject);
				String url = rdBean.getDownloadUrl().toString();
				eswFoto.setTexto(url);

				ESWritable eswGeo = new ESWritable(MaponyJsonCte.locationObject);
				StringBuilder posicion = new StringBuilder();
				posicion.append(rdBean.getLatitude());
				posicion.append(",");
				posicion.append(rdBean.getLongitude());
				eswGeo.setTexto(posicion.toString());

				ESWritable eswUserTags = new ESWritable("tags");
				eswUserTags.setTexto(rdBean.getUserTags().toString()+" "+rdBean.getMachineTags().toString());

//				ESWritable eswMachineTags = new ESWritable(MaponyCte.machineTagsObject);
//				eswMachineTags.setTexto(rdBean.getMachineTags().toString());

				ESWritable eswTitulo = new ESWritable(MaponyJsonCte.tituloObject);
				eswTitulo.setTexto(rdBean.getTitle().toString());
//
//				ESWritable eswCiudad = new ESWritable(MaponyCte.ciudadObject);
//				eswCiudad.setTexto(rdBean.getCiudad().toString());

				context.write(outKey, eswDescripcion);
				context.write(outKey, eswFoto);
				context.write(outKey, eswGeo);
				context.write(outKey, eswUserTags);
//				context.write(outKey, eswMachineTags);
				context.write(outKey, eswTitulo);
//				context.write(outKey, eswCiudad);
//				context.write(outKey, eswFechaCaptura);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
