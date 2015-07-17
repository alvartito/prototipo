package util.constantes;

//TODO Revisar ortografia
//TODO si da tiempo, refactorizar nombres de constantes
/**
 * @author Ã�lvaro SÃ¡nchez Blasco
 */
public final class MaponyCte {

	public static final String HELP_MAPONY = "Numero de argumentos no valido \n\n";
	/**
	 * [\\d[^\\w^\\-\\s]]+
	 */
	public static final String PATTERN_SIMBOLOS = "[\\d[^\\w^\\-\\s]]+";

	/**
	 * [^\\w^\\-\\s]+
	 */
	public static final String PATTERN_SIMBOLOS_2 = "[^\\w^\\-\\s]+";

	/**
	 * (\\s{2,})
	 */
	public static final String PATTERN_DOS_ESPACIOS = "(\\s{2,})";
	/**
	 * [\\s{1}][A-Z{2}][\\s{1}]
	 */
	public static final String PATTERN_DOS_MAYUSCULAS = "[\\s{1}][A-Z{2}][\\s{1}]";

	/**
	 * %2B
	 */
	public static final String PATTER_ESPACIO = "%2[A-Z]{1}";

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * g, 1, ~ 5,004km x 5,004km
	 */
	public static final int precisionGeoHashUno = 1;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gc, 2, ~ 1,251km x 625km
	 */
	public static final int precisionGeoHashDos = 2;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcp, 3, ~ 156km x 156km
	 */
	public static final int precisionGeoHashTres = 3;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpu, 4, ~39km x 19.5km
	 */
	public static final int precisionGeoHashCuatro = 4;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpuu, 5, ~ 4.9km x 4.9km
	 */
	public static final int precisionGeoHashCinco = 5;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpuuz, 6, ~ 1.2km x 0.61km
	 */
	public static final int precisionGeoHashSeis = 6;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpuuz9, 7, ~ 152.8m x 152.8m
	 */
	public static final int precisionGeoHashSiete = 7;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpuuz94, 8, ~ 38.2m x 19.1m
	 */
	public static final int precisionGeoHashOcho = 8;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpuuz94k, 9, ~ 4.78m x 4.78m
	 */
	public static final int precisionGeoHashNueve = 9;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpuuz94kk, 10, ~ 1.19m x 0.60m
	 */
	public static final int precisionGeoHashDiez = 10;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpuuz94kkp, 11, ~ 14.9cm x 14.9cm
	 */
	public static final int precisionGeoHashOnce = 11;

	/**
	 * 
	 * Geohash, Level,Dimensions
	 * <p>
	 * gcpuuz94kkp5, 12, ~ 3.7cm x 1.8cm
	 */
	public static final int precisionGeoHashDoce = 12;

	/**
	 * London
	 * <p>
	 * Lat. 51.50853 / Lon. -0.12574
	 * <p>
	 * GeoNameId : 2643743
	 */
	public static final String posicionLondon = "51.50853,-0.12574";

	/**
	 * Madrid
	 * <p>
	 * Lat. 40.4165 / Lon. -3.70256
	 * <p>
	 * GeoNameId : 3117735
	 */
	public static final String posicionMadrid = "40.4165,-3.70256";

	/**
	 * Berlin
	 * <p>
	 * Lat. 52.52437 / Lon. 13.41053
	 * <p>
	 * GeoNameId : 2950159
	 */
	public static final String posicionBerlin = "52.52437,13.41053";

	/**
	 * Londres
	 */
	public static final String sLondres = "Londres";
	/**
	 * Madrid
	 */
	public static final String sMadrid = "Madrid";
	/**
	 * Berlin
	 */
	public static final String sBerlin = "Berlin";

	/**
	 * Mensaje informativo de propiedades cargadas
	 */
	public static final String MSG_PROPIEDADES_CARGADAS = "Propiedades cargadas";

	/**
	 * Crea un String informando de la finalización del job que recibe como parámetro
	 * 
	 * @param jobName
	 * @return Mensaje de fin del job
	 */
	public static final String getMsgFinJob(final String jobName) {
		return "\nJob " + jobName + " finalizado con exito\n";
	}

	/**
	 * Crea un String informando que el job que recibe como parámetro va a dar comienzo
	 * 
	 * @param jobName
	 * @return Mensaje de inicio del job
	 */
	public static final String getMsgInicioJob(final String jobName) {
		return "\nComienza la ejecucion del job " + jobName;
	}

	/**
	 * ''
	 */
	public static final String VACIO = "";
	/**
	 * |
	 */
	public static final String PIPE = "|";
	/**
	 * \\|
	 */
	public static final String ESCAPED_PIPE = "\\|";
	/**
	 * \t
	 */
	public static final String TAB = "\t";
	/**
	 * -
	 */
	public static final String GUION = "-";
	/**
	 * ,
	 */
	public static final String COMA = ",";

	/**
	 * Nombre del fichero de propiedades
	 */
	public static final String propiedades = "job.properties";

	/**
	 * Constante del fichero de propiedades que indica la ruta a los ficheros a procesar 
	 */
	public static final String datos = "ruta_ficheros";
	/**
	 * Constante del fichero de propiedades que indica la ruta al fichero con la información de los países (obtenido de geonames)
	 */
	public static final String paises = "ruta_paises";
	/**
	 * Constante del fichero de propiedades que indica la ruta en la que dejar los datos de salida del job
	 */
	public static final String salida_group_near = "ruta_salida_group_near";

	/**
	 * Constante del fichero de propiedades que indica el nombre del indice que vamos a crear en ES
	 */
	public static final String indice = "index_name";
	/**
	 * Constante del fichero de propiedades que indica el tipo que vamos a crear en ES
	 */
	public static final String tipo = "type_name";
	/**
	 * Constante del fichero de propiedades que indica el nombre del cluster en el que vamos a hacer la carga de datos en ES
	 */
	public static final String cluster = "clusterName";

	/**
	 * Constante del fichero de propiedades que indica la ip del cluster de ES
	 */
	public static final String ip = "ip";
	/**
	 * Constante del fichero de propiedades que indica el puerto del cluste de ES
	 */
	public static final String puerto = "port";

	/**
	 * Constante del fichero de propiedades que indica el numero de reducers para nuestro job
	 */
	public static final String reducers = "numero_reducer";

	public static final String jobNameMainJob = "Inferencia Job";
	public static final String jobNameGroupNear = "Group Near Job";
	public static final String jobNameCsv = "Create CSV Job";
	public static final String jobNamePruebas = "Pruebas Job";
	public static final String jobPrototipo = "Prototipo Job";
	public static final String jobFuerzaBruta = "Fuerza Bruta Job";
}
