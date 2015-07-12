package util;

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
	 * Método que compara el valor String del Text que llega como parámetro con null y '' para verificar si tiene valor o no.
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
	 * The first step removes all characters that are not a letter or a space and replaces them with a space. The second step removes
	 * multiple spaces by only one space.
	 * 
	 * @param cadena
	 * @return la cadena limpia de caracteres extraños
	 */
	public static final String cleanString(String cadena) {
        return cadena.replaceAll("[\\d[^\\w\\s]]+", " ").replaceAll("(\\s{2,})", " ");
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
	public static final Text getGeoHashPorPrecision(Text longitude, Text latitude, int precision) throws Exception {
		try {
			double dLatitude = new Double(latitude.toString());
			double dLongitude = new Double(longitude.toString());
			return new Text(GeoHash.geoHashStringWithCharacterPrecision(dLatitude, dLongitude, precision));
		} catch (Exception e) {
			throw e;
		}
	}
}
