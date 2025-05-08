package modelo;

public class Conexion {

	private Ciudad origen;
	private Ciudad destino;
	private double impactoAmbiental;

	public Conexion(Ciudad origen, Ciudad destino, double impactoAmbiental) {
		if (origen.getIndice() == destino.getIndice()) {
			throw new IllegalArgumentException("No se permiten conexiones a sí mismo.");
		}
		this.origen = origen;
		this.destino = destino;
		this.impactoAmbiental = impactoAmbiental;
	}

	public Ciudad getOrigen() {
		return origen;
	}

	public Ciudad getDestino() {
		return destino;
	}

	public double getImpactoAmbiental() {
		return impactoAmbiental;
	}
}
