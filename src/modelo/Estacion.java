package modelo;

public class Estacion {

	private final int id;
	private String nombre;
	private double longitud;
	private double latitud;

	public Estacion(int id, String nombre, double longitud, double latitud) {
		this.id = id;
		this.nombre = nombre;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	public int obtenerId() {
		return id;
	}

	public String obtenerNombre() {
		return nombre;
	}

	public double obtenerLongitud() {
		return longitud;
	}

	public double obtenerLatitud() {
		return latitud;
	}

	@Override
	public String toString() {
		return nombre + " (" + id + ")";
	}
}
