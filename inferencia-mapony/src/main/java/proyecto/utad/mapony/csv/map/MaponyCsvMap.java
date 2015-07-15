package proyecto.utad.mapony.csv.map;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.beans.CustomGeoHashBean;
import util.clases.GeoHashCiudad;
import util.clases.MaponyUtil;
import util.constantes.MaponyCte;
import util.writables.CsvWritable;

public class MaponyCsvMap extends Mapper<LongWritable, Text, Text, CsvWritable> {

	private HashMap<String, CustomGeoHashBean> ciudades;
	private final Logger logger = LoggerFactory.getLogger(MaponyCsvMap.class);

	protected void map(LongWritable offset, Text line, Context context) throws IOException, InterruptedException {

		try {
			if (null == ciudades) {
				ciudades = GeoHashCiudad.getDatosCiudadesSeleccionadas();
			}
		} catch (Exception e) {
			getLogger().error(e.getMessage());
		}

		String[] dato = line.toString().split(MaponyCte.TAB);

		// Comenzamos por limpiar las referencias de videos, por lo que el campo [22] del String[] ha de ser
		// '0' para que lo procesemos.
		// Además, si no tiene informados los campos de longitud y latitud, también descartamos el registro.
		if ("1".toString().compareTo(dato[22]) != 0
				&& (MaponyCte.VACIO.compareTo(dato[10]) != 0 && MaponyCte.VACIO.compareTo(dato[11]) != 0)) {
			try {

				final Text longitud = new Text(dato[10]);
				final Text latitud = new Text(dato[11]);

				final Text geoHash = MaponyUtil.getGeoHashPorPrecision(longitud, latitud,
						MaponyCte.precisionGeoHashCinco);

				if (ciudades.containsKey(geoHash.toString())) {
					CustomGeoHashBean cghb = ciudades.get(geoHash.toString());
					CsvWritable rdBean = new CsvWritable(new Text(dato[0]),
							new Text(MaponyUtil.getFechaFromString(dato[3])),
							new Text(MaponyUtil.cleanStringCaptureDevice(dato[5])),
							new Text(MaponyUtil.cleanString(dato[6])), new Text(MaponyUtil.cleanString(dato[7])),
							new Text(MaponyUtil.cleanString(dato[8])), new Text(MaponyUtil.cleanString(dato[9])), longitud,
							latitud, new Text(dato[14]), geoHash, new Text(cghb.getName()));

					context.write(rdBean.getGeoHash(), rdBean);
				}

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * @return the logger
	 */
	private final Logger getLogger() {
		return logger;
	};
}
