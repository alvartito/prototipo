package proyecto.utad.mapony.prototipo.map;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proyecto.utad.mapony.prototipo.bean.RawDataBeanPrototipo;
import util.GeoHashCiudad;
import util.MaponyUtil;
import util.beans.GeoHashBean;
import util.constantes.MaponyCte;

public class MapperPrototipo extends Mapper<LongWritable, Text, Text, Text> {

	private static final Logger logger = LoggerFactory.getLogger(MapperPrototipo.class);
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

				final Text geoHash = MaponyUtil.getGeoHashPorPrecision(longitud, latitud, MaponyCte.precisionGeoHashDos);

				final Text geoHashCiudad = MaponyUtil.getGeoHashPorPrecision(longitud, latitud,
						MaponyCte.precisionGeoHashCinco);

				Text ciudad = new Text();
				Text pais = new Text();
				Text continente = new Text();
				if (ciudades.containsKey(geoHashCiudad.toString())) {
					final GeoHashBean temp = ciudades.get(geoHashCiudad.toString());
					ciudad = new Text(temp.getName());
					pais = new Text(temp.getPais());
					continente = new Text(temp.getContinente());
				}

				RawDataBeanPrototipo rdBean = new RawDataBeanPrototipo(dato[0], dato[3],
						MaponyUtil.cleanString(dato[5]), MaponyUtil.cleanString(dato[6]),
						MaponyUtil.cleanString(dato[7]), MaponyUtil.cleanString(dato[8]),
						MaponyUtil.cleanString(dato[9]), longitud.toString(), latitud.toString(), dato[14],
						geoHash.toString(), geoHashCiudad.toString(), continente.toString(), pais.toString(),
						ciudad.toString());

				context.write(geoHash, new Text(rdBean.getString()));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
