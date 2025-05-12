package main.java.dto;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class SenderoDTO {

	private final Coordinate origen;
	private final Coordinate destino;
	private final int impactoAmbiental;

	public SenderoDTO(Coordinate origen, Coordinate destino, int impactoAmbiental) {
		this.origen = origen;
		this.destino = destino;
		this.impactoAmbiental = impactoAmbiental;
	}

	public Coordinate getOrigen() {
		return origen;
	}

	public Coordinate getDestino() {
		return destino;
	}

	public int getImpactoAmbiental() {
		return impactoAmbiental;
	}
}
