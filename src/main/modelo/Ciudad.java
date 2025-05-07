package main.modelo;

public class Ciudad {

	private int indice;
	private String nombre;

	public Ciudad(int indice, String nombre) {
		this.indice = indice;
		this.nombre = nombre;
	}

	public int getIndice() {
		return indice;
	}

	public String getNombre() {
		return nombre;
	}
}
