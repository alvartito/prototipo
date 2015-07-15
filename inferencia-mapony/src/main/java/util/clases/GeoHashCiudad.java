package util.clases;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

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

	public GeoHashCiudad(final String archivo) {
		setArchivo(archivo);
		cargaGeoHashCiudades();
	}
	
	public GeoHashCiudad(){
		cargaGeoHashCiudadesSeleccionadas();
	}

	/**
	 * Lee el fichero de ciudades, y por cada registro, cargar en mi HashMap los datos correspondientes.
	 */
	public HashMap<String, GeoHashBean> cargaGeoHashCiudades() {
		// Lee el fichero de ciudades, y por cada registro, cargar en mi HashMap los datos
		// correspondientes.
		datos = new HashMap<String, GeoHashBean>();

		String cadena;
		FileReader f;
		try {
			f = new FileReader(getArchivo());
			BufferedReader b = new BufferedReader(f);
			while ((cadena = b.readLine()) != null) {
				GeoHashBean bean = new GeoHashBean(cadena.split("\t"));
				String geoHashString = bean.getGeoHash();
				datos.put(geoHashString, bean);
			}
			b.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return datos;
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
			throw new Exception("Datos de ciudades no cargados");
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
	

}
