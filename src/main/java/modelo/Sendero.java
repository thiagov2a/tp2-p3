package main.java.modelo;

public class Sendero {

	private Estacion origen;
	private Estacion destino;
	private int impactoAmbiental;

	public Sendero(Estacion origen, Estacion destino, int impacto) {
		if (origen.equals(destino)) {
			throw new IllegalArgumentException("No se permiten senderos a la misma estación");
		}

		if (impacto < 1 || impacto > 10) {
			throw new IllegalArgumentException("El impacto ambiental debe estar entre 1 y 10");
		}

		this.origen = origen;
		this.destino = destino;
		this.impactoAmbiental = impacto;
	}

	public boolean contieneEstacion(Estacion estacion) {
		return origen.equals(estacion) || destino.equals(estacion);
	}

	public Estacion obtenerOrigen() {
		return origen;
	}

	public Estacion obtenerDestino() {
		return destino;
	}

	public int obtenerImpactoAmbiental() {
		return impactoAmbiental;
	}
}
