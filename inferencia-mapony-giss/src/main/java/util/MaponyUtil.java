package util;

import org.apache.hadoop.io.Text;

import ch.hsr.geohash.GeoHash;
import util.constantes.MaponyCte;

/**
 * @author Ã�lvaro SÃ¡nchez Blasco
 *
 */
public class MaponyUtil {

	/**
	 * MÃ©todo que compara el String que llega como parÃ¡metro con null y '' para verificar si tiene valor o no.
	 * 
	 * @param cadena
	 * @return true en caso de que no sea nulo ni vacÃ­o.
	 */
	public static boolean stringTieneValor(String cadena) {
		boolean tieneValor = false;
		if (null != cadena && !MaponyCte.VACIO.equals(cadena)) {
			tieneValor = true;
		}
		return tieneValor;
	}

	/**
	 * MÃ©todo que compara el valor String del Text que llega como parÃ¡metro con null y '' para verificar si tiene valor o no.
	 * 
	 * @param cadena
	 * @return true en caso de que no sea nulo ni vacÃ­o.
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
	 * @return la cadena limpia de caracteres extraÃ±os
	 */
	public static final String cleanString(final String cadena) {
        String sCadena = cadena.replaceAll(MaponyCte.PATTERN_, " ").replaceAll("(\\s{2,})", " ");
        return sCadena.replaceAll(MaponyCte.c3, "");
	}

	/**
	 * Recibidos los parametros de longitud, latitud, y precisiÃ³n del geohash, devuelve el geohash calculado.
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
