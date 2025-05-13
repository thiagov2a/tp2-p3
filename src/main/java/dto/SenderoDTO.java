package main.java.dto;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class SenderoDTO {

	private Coordinate origen;
	private Coordinate destino;
	private int impactoAmbiental;

	public SenderoDTO(Coordinate origen, Coordinate destino, int impactoAmbiental) {
		this.origen = origen;
		this.destino = destino;
		this.impactoAmbiental = impactoAmbiental;
	}

	public Coordinate obtenerOrigen() {
		return origen;
	}

	public Coordinate obtenerDestino() {
		return destino;
	}

	public int obtenerImpactoAmbiental() {
		return impactoAmbiental;
	}
}
