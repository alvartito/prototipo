package util.clases;

import org.apache.hadoop.io.Text;

import ch.hsr.geohash.GeoHash;
import util.constantes.MaponyCte;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class MaponyUtil {

	/**
	 * Método que compara el String que llega como parámetro con null y '' para verificar si tiene valor o no.
	 * 
	 * @param cadena
	 * @return true en caso de que no sea nulo ni vacío.
	 */
	public static boolean stringTieneValor(String cadena) {
		boolean tieneValor = false;
		if (null != cadena && !MaponyCte.VACIO.equals(cadena)) {
			tieneValor = true;
		}
		return tieneValor;
	}

	/**
	 * Método que compara el valor String del Text que llega como parámetro con null y '' para verificar si tiene valor
	 * o no.
	 * 
	 * @param cadena
	 * @return true en caso de que no sea nulo ni vacío.
	 */
	public static boolean textTieneValor(Text cadena) {
		boolean tieneValor = false;
		if (null != cadena) {
			Text vacio = new Text();
			if (!vacio.equals(cadena)) {
				tieneValor = true;
			}
		}
		return tieneValor;
	}

	/**
	 * The first step removes all characters that are not a letter or a space and replaces them with a space. The second
	 * step removes multiple spaces by only one space.
	 * 
	 * @param cadena
	 * @return la cadena limpia de caracteres extraños
	 */
	public static final String cleanString(String cadena) {
		if (stringTieneValor(cadena)) {
			return cadena.replaceAll(MaponyCte.PATTER_ESPACIO, " ").replaceAll(MaponyCte.PATTERN_SIMBOLOS_2, " ")
					.replaceAll(MaponyCte.PATTERN_DOS_MAYUSCULAS, "").replaceAll(MaponyCte.PATTERN_DOS_ESPACIOS, " ");
		} else
			return MaponyCte.GUION;
	}

//	/**
//	 * The first step removes all characters that are not a letter or a space and replaces them with a space. The second
//	 * step removes multiple spaces by only one space.
//	 * 
//	 * @param cadena
//	 * @return la cadena limpia de caracteres extraños
//	 */
//	public static final String cleanStringCaptureDevice(String cadena) {
//		return cadena.replaceAll(MaponyCte.PATTER_ESPACIO, " ").replaceAll(MaponyCte.PATTERN_SIMBOLOS_2, " ")
//				.replaceAll(MaponyCte.PATTERN_DOS_MAYUSCULAS, "").replaceAll(MaponyCte.PATTERN_DOS_ESPACIOS, " ");
//	}

	/**
	 * Recibidos los parametros de longitud, latitud, y precisión del geohash, devuelve el geohash calculado.
	 * 
	 * @param longitude
	 * @param latitude
	 * @param precision
	 * @return El geohash calculado
	 * @throws Exception
	 */
	public static final Text getGeoHashPorPrecision(Text longitude, Text latitude, int precision) throws Exception {
		try {
			double dLatitude = new Double(latitude.toString());
			double dLongitude = new Double(longitude.toString());
			return new Text(GeoHash.geoHashStringWithCharacterPrecision(dLatitude, dLongitude, precision));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Recibidos los parametros de longitud, latitud, y precisión del geohash, devuelve el geohash calculado.
	 * 
	 * @param longitude
	 * @param latitude
	 * @param precision
	 * @return El geohash calculado
	 * @throws Exception
	 */
	public static final String getStringGeoHashPorPrecision(String longitude, String latitude, int precision)
			throws Exception {
		try {
			double dLatitude = new Double(latitude);
			double dLongitude = new Double(longitude);
			return GeoHash.geoHashStringWithCharacterPrecision(dLatitude, dLongitude, precision);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Recibidos los parametros de posicion[] ([0]latitud,[1]longitud), y precisión del geohash, devuelve el geohash
	 * calculado.
	 * 
	 * @param posicion[]
	 * @param precision
	 * @return El geohash calculado
	 * @throws Exception
	 */
	public static final Text getGeoHashPorPrecision(String posicion, int precision) throws Exception {
		try {
			String[] pos = posicion.split(",");
			double dLatitude = new Double(pos[0]);
			double dLongitude = new Double(pos[1]);

			return new Text(GeoHash.geoHashStringWithCharacterPrecision(dLatitude, dLongitude, precision));
		} catch (Exception e) {
			throw e;
		}
	}

	public static final String getFechaFromString(String fecha) {
		// 2008-02-09 16:27:11.0
		return fecha.substring(0, 10);
	}

}
