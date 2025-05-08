package modelo;

public class Estacion {

	private final int id;
	private String nombre;
	private double coordenadaX;
	private double coordenadaY;

	public Estacion(int id, String nombre, double x, double y) {
		this.id = id;
		this.nombre = nombre;
		this.coordenadaX = x;
		this.coordenadaY = y;
	}

	public int obtenerId() {
		return id;
	}

	public String obtenerNombre() {
		return nombre;
	}

	public double obtenerCoordenadaX() {
		return coordenadaX;
	}

	public double obtenerCoordenadaY() {
		return coordenadaY;
	}

	@Override
	public String toString() {
		return nombre + " (" + id + ")";
	}
}
