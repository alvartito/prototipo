package proyecto.utad.mapony;

public class Limpieza {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Apéndice de método generado automáticamente
		cleanString("AA A kkita+23");
	}

	/**
	 * The first step removes all characters that are not a letter or a space and replaces them with a space. The second step removes
	 * multiple spaces by only one space.
	 * 
	 * @param cadena
	 * @return la cadena limpia de caracteres extraÃ±os
	 */
	public static final void cleanString(String cadena) {
		String a1 = cadena.replaceAll("([A-Z\\s]{1})", "");
		System.err.println(a1);
		a1=cadena.replaceAll("([A-Z\\s]{1})", "");
		// Representa el espacio -> \\s
		// Representa una letra maýuscula seguida de espacio ([A-Z\\s]{1})
		// Representa dos letras mayúsculas seguidas de espacio ([A-Z\\s]{2})
        a1= cadena.replaceAll("[\\d[^\\w\\s]]+", " ").replaceAll("(\\s{2,})", " ");
        System.err.println(a1);
	}
	
}
