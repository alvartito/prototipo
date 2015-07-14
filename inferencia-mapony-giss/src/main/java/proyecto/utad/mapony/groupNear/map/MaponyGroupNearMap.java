package proyecto.utad.mapony.groupNear.map;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.GeoHashCiudad;
import util.MaponyUtil;
import util.beans.GeoHashBean;
import util.constantes.MaponyCte;
import util.writables.RawDataWritable;

public class MaponyGroupNearMap extends Mapper<LongWritable, Text, Text, RawDataWritable> {

	private static final Logger logger = LoggerFactory.getLogger(MaponyGroupNearMap.class);
	private HashMap<String, GeoHashBean> ciudades;

	protected void map(LongWritable offset, Text line, Context context) throws IOException, InterruptedException {

		//Patr�n singleton para obtener el hashmap de las ciudades
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
		// Además, si no tiene informados los campos de longitud y latitud, tambi�n descartamos el registro.
		if ("1".toString().compareTo(dato[22]) != 0 && (MaponyCte.VACIO.compareTo(dato[10]) != 0 && MaponyCte.VACIO.compareTo(dato[11]) != 0)) {
			try {
				
				final Text longitud = new Text(dato[10]);
				final Text latitud = new Text(dato[11]);
				
				// Geohash de precisi�n 5, con el que agruparemos datos cercanos (key de nuestro map)
				final Text geoHash = MaponyUtil.getGeoHashPorPrecision(longitud, latitud, MaponyCte.precisionGeoHashDos);
				
				// Geohash de precisi�n 2, con el que obtendremos la ciudad del hashmap que cotiene los datos de las ciudades.
				final Text geoHashCiudad = MaponyUtil.getGeoHashPorPrecision(longitud, latitud, MaponyCte.precisionGeoHashDos);

				
				Text ciudad = new Text();
				Text pais = new Text();
				Text continente = new Text();
				if (ciudades.containsKey(geoHashCiudad.toString())) {
					final GeoHashBean temp = ciudades.get(geoHashCiudad.toString());
					ciudad = new Text(temp.getName());
					pais = new Text(temp.getPais());
					continente = new Text(temp.getContinente());
				}

				//TODO formatear la fecha (datos[3]) para insertarla en ES también
				
				// Bean que contendr� los datos limpios para su posterior procesado en el job de inferencia
				RawDataWritable rdBean = new RawDataWritable(new Text(dato[0]), new Text(dato[3]), new Text(MaponyUtil.cleanString(dato[5])),
						new Text(MaponyUtil.cleanString(dato[6])), new Text(MaponyUtil.cleanString(dato[7])), new Text(
								MaponyUtil.cleanString(dato[8])), new Text(MaponyUtil.cleanString(dato[9])), longitud, latitud,
						new Text(dato[14]), geoHash, geoHashCiudad, continente, pais, ciudad);

				context.write(rdBean.getGeoHash(), rdBean);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
