package util.constantes;

/** @author Álvaro Sánchez Blasco */
public final class MaponyCte {

	public static final String HELP_MAPONY = "Invalid number of arguments\n\n";
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

	public static final String FIN = "Procesado de datos finalizado";
	
	public static final int precisionGeoHashCiudad = 2;
	/**
	 *
	 * Geohash Level Dimensions 
	 * <p>g 1 ~ 5,004km x 5,004km 
	 * <p>gc 2 ~ 1,251km x 625km 
	 * <p>gcp 3 ~ 156km x 156km 
	 * <p>gcpu 4 ~39km x 19.5km 
	 * <p>gcpuu 5 ~ 4.9km x 4.9km 
	 * <p>gcpuuz 6 ~ 1.2km x 0.61km 
	 * <p>gcpuuz9 7 ~ 152.8m x 152.8m 
	 * <p>gcpuuz94 8 ~ 38.2m x 19.1m 
	 * <p>gcpuuz94k 9 ~ 4.78m x 4.78m 
	 * <p>gcpuuz94kk 10 ~ 1.19m x 0.60m 
	 * <p>gcpuuz94kkp 11 ~ 14.9cm x 14.9cm 
	 * <p>gcpuuz94kkp5 12 ~ 3.7cm x 1.8cm
	 *
	 */
	public static final int precisionGeoHashAgrupar = 5;
	
	public static final String MSG_PROPIEDADES_CARGADAS = "Propiedades cargadas";

	public static final String getMsgFinJob(final String jobName) {
		return "\nJob "+ jobName +" finalizado con éxito\n";
	}

	public static final String VACIO = "";
	public static final String PIPE = "|";
	public static final String ESCAPED_PIPE = "\\|";
	public static final String TAB = "\t";
	public static final String GUION = "-";
	
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
