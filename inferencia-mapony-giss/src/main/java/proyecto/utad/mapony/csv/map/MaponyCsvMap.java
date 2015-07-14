package proyecto.utad.mapony.csv.map;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.GeoHashCiudad;
import util.beans.GeoHashBean;
import util.constantes.MaponyCte;
import util.writables.RawDataWritable;
import ch.hsr.geohash.GeoHash;

public class MaponyCsvMap extends Mapper<LongWritable, Text, Text, Text> {

	private Text outKey;
	private HashMap<String, GeoHashBean> ciudades;
	private final Logger logger = LoggerFactory.getLogger(MaponyCsvMap.class);

	protected void map(LongWritable offset, Text line, Context context) throws IOException, InterruptedException {

//		try {
//			if (null == ciudades) {
//				ciudades = GeoHashCiudad.getDatos();
//			}
//		} catch (Exception e) {
//			getLogger().error(e.getMessage());
//		}
//
//		String[] dato = line.toString().split("\t");
//
//		// Comenzamos por limpiar las referencias de videos, por lo que el campo [22] del String[] ha de ser
//		// '0' para que lo procesemos.
//		final RawDataWritable rdBean = new RawDataWritable(dato[0], dato[1], dato[2], dato[3], dato[4], dato[5], dato[6], dato[7], dato[8], dato[9],
//				dato[10], dato[11], dato[12], dato[13], dato[14], dato[15], dato[16], dato[17], dato[18], dato[19], dato[20], dato[21], dato[22]);
//
//		// Además, si no tiene informados los campos de longitud y latitud, también descartamos el registro.
//		if ("1".toString().compareTo(rdBean.getMarker()) != 0
//				&& (MaponyCte.VACIO.compareTo(rdBean.getLatitude()) != 0 && MaponyCte.VACIO.compareTo(rdBean.getLongitude()) != 0)) {
//
//			double dLatitude = new Double(rdBean.getLatitude());
//			double dLongitude = new Double(rdBean.getLongitude());
//
//			String geoHash = GeoHash.geoHashStringWithCharacterPrecision(dLatitude, dLongitude, MaponyCte.precisionGeoHashCiudad);
//			rdBean.setGeoHash(geoHash);
//
//			if (ciudades.containsKey(geoHash)) {
//				GeoHashBean temp = ciudades.get(geoHash);
////				getLogger().info("Dato Encontrado para " + rdBean.getIdentifier() + ": " + temp.toString());
//				String[] geoHashCiudadPaisContinente = temp.toString().split("|");
//				rdBean.setCiudad(geoHashCiudadPaisContinente[1]);
//				rdBean.setPais(geoHashCiudadPaisContinente[2]);
//				rdBean.setPais(geoHashCiudadPaisContinente[3]);
//				outKey = new Text(rdBean.toCsvString());
//				context.write(outKey, new Text(MaponyCte.VACIO));
//			}
//		}
	}

	/**
	 * @return the logger
	 */
	private final Logger getLogger() {
		return logger;
	};
}
