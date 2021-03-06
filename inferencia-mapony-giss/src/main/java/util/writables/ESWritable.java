package util.writables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 * @author Álvaro Sánchez Blasco
 * 
 * Writable para crear los objetos del json que vamos a cargar en ES
 */
public class ESWritable implements WritableComparable<ESWritable> {
	
	private String tipo;
	private String texto;
	
	public ESWritable(String tipo) {
		this.setTipo(tipo);
	}
	
	public ESWritable(){
		
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void readFields(DataInput arg0) throws IOException {
		tipo = Text.readString(arg0);
		texto = Text.readString(arg0);	
	}

	public void write(DataOutput arg0) throws IOException {
		Text.writeString(arg0, tipo);
		Text.writeString(arg0, texto);
		
	}

	public int compareTo(ESWritable o) {
		int cmp = tipo.compareTo(o.tipo);
		if (cmp == 0) {
	  		return texto.compareTo(o.texto);
	  	}
	  	return cmp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ESWritable other = (ESWritable) obj;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
}
