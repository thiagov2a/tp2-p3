package main.java.dto;

import java.util.List;

public class ResultadoAGM {

	private List<SenderoDTO> senderos;
	private int impactoTotal;
	private double tiempoEjecucion;

	public ResultadoAGM(List<SenderoDTO> senderos, int impactoTotal, double tiempo) {
		this.senderos = senderos;
		this.impactoTotal = impactoTotal;
		this.tiempoEjecucion = tiempo;
	}

	public List<SenderoDTO> obtenerSenderos() {
		return senderos;
	}

	public int obtenerImpactoTotal() {
		return impactoTotal;
	}

	public double obtenerTiempoEjecucion() {
		return tiempoEjecucion;
	}
}
