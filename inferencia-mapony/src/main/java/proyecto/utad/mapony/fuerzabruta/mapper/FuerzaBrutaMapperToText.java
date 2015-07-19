package proyecto.utad.mapony.fuerzabruta.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import util.clases.MaponyUtil;
import util.constantes.MaponyCte;

public class FuerzaBrutaMapperToText extends Mapper<LongWritable, Text, Text, Text> {
	protected void map(LongWritable key, Text line, Context context) throws IOException, InterruptedException {

		String[] dato = line.toString().split("\t");
		// Comenzamos por limpiar las referencias de videos, por lo que el campo [21] del String[] ha de ser
		// '0' para que lo procesemos.
		// Además, si no tiene informados los campos de longitud y latitud, también descartamos el registro.
		if ("1".toString().compareTo(dato[22]) != 0
				&& (MaponyCte.VACIO.compareTo(dato[10]) != 0 && MaponyCte.VACIO.compareTo(dato[11]) != 0)) {

			final String longitud = dato[10];
			final String latitud = dato[11];

			context.write(new Text(dato[0]), new Text(

			MaponyUtil.cleanString(dato[6]) + MaponyCte.PIPE + MaponyUtil.cleanString(dato[7]) + MaponyCte.PIPE
					+ MaponyUtil.cleanString(dato[8]) + MaponyCte.PIPE + MaponyUtil.cleanString(dato[9])
					+ MaponyCte.PIPE + latitud + MaponyCte.COMA + longitud + MaponyCte.PIPE + dato[14] + MaponyCte.PIPE
					+ MaponyUtil.cleanString(dato[5])

			));
			
			
			
			
		}
	}
}
