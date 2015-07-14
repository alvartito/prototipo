package util.constantes;

/** @author Álvaro Sánchez Blasco */
public final class MaponyCte {

	public static final String HELP_MAPONY = "Invalid number of arguments\n\n";
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
	 * */
	public static final String PATTER_ESPACIO = "%2[A-Z]{1}";
	
	/** Objetos del JSon */
	/**
	 * userTags
	 */
	public static final String userTagsObject = "userTags";
	/**
	 * machineTags
	 */
	public static final String machineTagsObject = "machineTags";
	/**
	 * descripcion
	 */
	public static final String descripcionObject = "descripcion";
	/**
	 * foto
	 */
	public static final String fotoObject = "foto";
	/**
	 * id
	 */
	public static final String idObject = "id";
	/**
	 * titulo
	 */
	public static final String tituloObject = "titulo";
	/**
	 * ciudad
	 */
	public static final String ciudadObject = "ciudad";
	/**
	 * location
	 */
	public static final String locationObject = "location";
	/**
	 * fechaCaptura
	 */
	public static final String fechaCapturaObject = "fechaCaptura";

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * g 1 ~ 5,004km x 5,004km
	 */
	public static final int precisionGeoHashUno = 1;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gc 2 ~ 1,251km x 625km
	 */
	public static final int precisionGeoHashDos = 2;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcp 3 ~ 156km x 156km
	 */
	public static final int precisionGeoHashTres = 3;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpu 4 ~39km x 19.5km
	 */
	public static final int precisionGeoHashCuatro = 4;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpuu 5 ~ 4.9km x 4.9km
	 */
	public static final int precisionGeoHashCinco = 5;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpuuz 6 ~ 1.2km x 0.61km
	 */
	public static final int precisionGeoHashSeis = 6;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpuuz9 7 ~ 152.8m x 152.8m
	 */
	public static final int precisionGeoHashSiete = 7;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpuuz94 8 ~ 38.2m x 19.1m
	 */
	public static final int precisionGeoHashOcho = 8;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpuuz94k 9 ~ 4.78m x 4.78m
	 */
	public static final int precisionGeoHashNueve = 9;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpuuz94kk 10 ~ 1.19m x 0.60m
	 */
	public static final int precisionGeoHashDiez = 10;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpuuz94kkp 11 ~ 14.9cm x 14.9cm
	 */
	public static final int precisionGeoHashOnce = 11;

	/**
	 *
	 * Geohash Level Dimensions
	 * <p>
	 * gcpuuz94kkp5 12 ~ 3.7cm x 1.8cm
	 */
	public static final int precisionGeoHashDoce = 12;

	
	public static final String MSG_PROPIEDADES_CARGADAS = "Propiedades cargadas";

	public static final String getMsgFinJob(final String jobName) {
		return "\nJob "+ jobName +" finalizado con éxito\n";
	}

	public static final String VACIO = "";
	public static final String PIPE = "|";
	public static final String ESCAPED_PIPE = "\\|";
	public static final String TAB = "\t";
	public static final String GUION = "-";
	public static final String COMA = ",";
	
	public static final String propiedades = "job.properties";
	
	public static final String datos = "ruta_ficheros";
	public static final String paises = "ruta_paises";
	public static final String salida_group_near = "ruta_salida_group_near";
	
	public static final String indice = "index_name";
	public static final String tipo = "type_name";
	public static final String cluster = "clusterName";
	
	public static final String ip = "ip";
	public static final String puerto = "port";
	
	public static final String reducers = "numero_reducer";
	
	public static final String jobNameMainJob = "Inferencia Job";
	public static final String jobNameGroupNear = "Group Near Job";
	public static final String jobNameCsv = "Create CSV Job";
	public static final String jobNamePruebas = "Pruebas Job";
	public static final String jobPrototipo = "Prototipo Job";
}
