package modelo;

public class Sendero {

	private final Estacion estacionOrigen;
	private final Estacion estacionDestino;
	private final int impactoAmbiental; // Rango del 1-10

	public Sendero(Estacion origen, Estacion destino, int impacto) {
		if (origen.equals(destino)) {
			throw new IllegalArgumentException("No se permiten senderos a la misma estación");
		}

		if (impacto < 1 || impacto > 10) {
			throw new IllegalArgumentException("El impacto ambiental debe estar entre 1 y 10");
		}

		this.estacionOrigen = origen;
		this.estacionDestino = destino;
		this.impactoAmbiental = impacto;
	}

	public boolean contieneEstacion(Estacion estacion) {
		return estacionOrigen.equals(estacion) || estacionDestino.equals(estacion);
	}

	public Estacion obtenerEstacionOrigen() {
		return estacionOrigen;
	}

	public Estacion obtenerEstacionDestino() {
		return estacionDestino;
	}

	public int obtenerImpactoAmbiental() {
		return impactoAmbiental;
	}

	@Override
	public String toString() {
		return String.format("%-20s <--> %-20s | Impacto: %d%n", estacionOrigen.obtenerNombre(),
				estacionDestino.obtenerNombre(), impactoAmbiental);
	}
}
