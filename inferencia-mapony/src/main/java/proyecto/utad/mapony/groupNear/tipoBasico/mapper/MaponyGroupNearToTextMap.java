package proyecto.utad.mapony.groupNear.tipoBasico.mapper;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.beans.GeoHashBean;
import util.clases.GeoHashCiudad;
import util.clases.MaponyUtil;
import util.constantes.MaponyCte;

public class MaponyGroupNearToTextMap extends Mapper<LongWritable, Text, Text, Text> {

	private static final Logger logger = LoggerFactory.getLogger(MaponyGroupNearToTextMap.class);
	private HashMap<String, GeoHashBean> ciudades;

	protected void map(LongWritable offset, Text line, Context context) throws IOException, InterruptedException {

		try {
			if (null == ciudades) {
				ciudades = GeoHashCiudad.getDatos();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		String[] dato = line.toString().split(MaponyCte.TAB);

		// Comenzamos por limpiar las referencias de videos, por lo que el campo [22] del String[] ha de ser
		// '0' para que lo procesemos.
		// Además, si no tiene informados los campos de longitud y latitud, también descartamos el registro.
		if ("1".toString().compareTo(dato[22]) != 0
				&& (MaponyCte.VACIO.compareTo(dato[10]) != 0 && MaponyCte.VACIO.compareTo(dato[11]) != 0)) {
			try {

				final Text longitud = new Text(dato[10]);
				final Text latitud = new Text(dato[11]);

				final Text geoHash = MaponyUtil.getGeoHashPorPrecision(longitud, latitud,
						MaponyCte.precisionGeoHashCuatro);

				Text ciudad = new Text();
				Text pais = new Text();
				Text continente = new Text();
				if (ciudades.containsKey(geoHash.toString())) {
					final GeoHashBean temp = ciudades.get(geoHash.toString());
					ciudad = new Text(temp.getName());
					pais = new Text(temp.getPais());
					continente = new Text(temp.getContinente());
				}

				context.write(new Text(geoHash.toString()), 
						new Text(dato[0]+MaponyCte.PIPE+MaponyUtil.getFechaFromString(dato[3])+MaponyCte.PIPE+
								MaponyUtil.cleanStringCaptureDevice(dato[5])+MaponyCte.PIPE+
								MaponyUtil.cleanString(dato[6])+MaponyCte.PIPE+MaponyUtil.cleanString(dato[7])+MaponyCte.PIPE+
								MaponyUtil.cleanString(dato[8])+MaponyCte.PIPE+MaponyUtil.cleanString(dato[9])+MaponyCte.PIPE+
								dato[10]+MaponyCte.PIPE+dato[11]+MaponyCte.PIPE+dato[14]+MaponyCte.PIPE+geoHash.toString()
								+MaponyCte.PIPE+continente.toString()+MaponyCte.PIPE+pais.toString()+MaponyCte.PIPE+ciudad.toString())
						);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
