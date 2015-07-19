package util.driver;

import proyecto.utad.mapony.cargaES.MaponyCargaESJob;
import proyecto.utad.mapony.csv.MaponyCsvJob;
import proyecto.utad.mapony.fuerzabruta.FuerzaBrutaJob;
import proyecto.utad.mapony.groupNear.customWritable.MaponyGroupNearJob;
import proyecto.utad.mapony.groupNear.tipoBasico.MaponyGroupNearToTextJob;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class Driver extends ProgramDriver {
	
	/**
	 * @throws Throwable
	 */
	public Driver() throws Throwable {
		super();
		addClass("csv", MaponyCsvJob.class, "Mapony CSV. Exportar a csv.");
//		addClass("agrupa_cw", MaponyGroupNearJob.class, "Mapony Group By Near Places (MaponyGroupNearJob)");
		addClass("agrupa_tb", MaponyGroupNearToTextJob.class, "Mapony Group By Near Places (MaponyGroupNearJob)");
		addClass("carga", MaponyCargaESJob.class, "Mapony carga en ES");
//		addClass("fuerzabruta", FuerzaBrutaJob.class, "Mapony Fuerza Bruta. Cargar a ES directamente, sin procesar mas que los datos a insertar");
	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		Driver driver = new Driver();
		driver.driver(args);
		System.exit(0);
	}
}
