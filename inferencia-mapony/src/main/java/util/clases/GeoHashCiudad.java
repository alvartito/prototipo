package util.clases;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.fs.Path;

import util.beans.CustomGeoHashBean;
import util.beans.GeoHashBean;
import util.constantes.MaponyCte;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class GeoHashCiudad {

	private static HashMap<String, GeoHashBean> datos;
	private static HashMap<String, CustomGeoHashBean> datosCiudadesSeleccionadas;

	private String archivo;
	private Path rutaArchivo;
	
	public GeoHashCiudad(){
		cargaGeoHashCiudadesSeleccionadas();
	}

	/**
	 * Lee el fichero de ciudades, y por cada registro, cargar en mi HashMap los datos correspondientes.
	 * @throws IOException 
	 */
	public HashMap<String, GeoHashBean> cargaGeoHashCiudades(String cadena) throws IOException {
		// Lee el fichero de ciudades, y por cada registro, cargar en mi HashMap los datos
		// correspondientes.
		GeoHashBean bean = new GeoHashBean(cadena.split("\t"));
		String geoHashString = bean.getGeoHash();
		datos.put(geoHashString, bean);

		return datos;
	}

	public static void cargaHashDatosCiudades(String cadena) throws Exception {
		GeoHashBean bean = new GeoHashBean(cadena.split("\t"));
		String geoHashString = bean.getGeoHash();
		getDatos().put(geoHashString, bean);
	}
	
	public HashMap<String, CustomGeoHashBean> cargaGeoHashCiudadesSeleccionadas() {
		datosCiudadesSeleccionadas = new HashMap<String, CustomGeoHashBean>();

		CustomGeoHashBean cghLondres = new CustomGeoHashBean("Londres", MaponyCte.posicionLondon);
		datosCiudadesSeleccionadas.put(cghLondres.getGeoHash(), cghLondres);

		CustomGeoHashBean cghMadrid = new CustomGeoHashBean("Madrid", MaponyCte.posicionMadrid);
		datosCiudadesSeleccionadas.put(cghMadrid.getGeoHash(), cghMadrid);

		CustomGeoHashBean cghBerlin = new CustomGeoHashBean("Berlin", MaponyCte.posicionBerlin);
		datosCiudadesSeleccionadas.put(cghBerlin.getGeoHash(), cghBerlin);

		return datosCiudadesSeleccionadas;
	}

	/**
	 * @return the archivo
	 */
	private final String getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo
	 *            the archivo to set
	 */
	private final void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	/**
	 * @return the datos
	 * @throws Exception
	 */
	public static final HashMap<String, GeoHashBean> getDatos() throws Exception {
		if (null == datos) {
			datos = new HashMap<String, GeoHashBean>();
		}
		return datos;
	}
	
	/**
	 * @return the datos
	 * @throws Exception
	 */
	public static final HashMap<String, CustomGeoHashBean> getDatosCiudadesSeleccionadas() throws Exception {
		if (null == datosCiudadesSeleccionadas) {
			throw new Exception("Datos de ciudades no cargados");
		}
		return datosCiudadesSeleccionadas;
	}

	/**
	 * @return the rutaArchivo
	 */
	private final Path getRutaArchivo() {
		return rutaArchivo;
	}

	/**
	 * @param rutaArchivo the rutaArchivo to set
	 */
	private final void setRutaArchivo(Path rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	

}
