package main.java.controlador;

import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import main.java.dto.ResultadoAGM;
import main.java.dto.SenderoDTO;
import main.java.interfaz.AlgoritmoAGM;
import main.java.interfaz.IVistaControlador;

public class ServicioParque implements IVistaControlador {

	private ServicioParque servicioParque;

	public ServicioParque(String rutaJson) {
		this.servicioParque = new ServicioParque(rutaJson);
	}

	@Override
	public Map<Integer, Coordinate> obtenerCoordenadasEstaciones() {
		return servicioParque.obtenerCoordenadasEstaciones();
	}

	@Override
	public String obtenerNombreEstacion(int id) {
		return servicioParque.obtenerNombreEstacion(id);
	}

	@Override
	public List<SenderoDTO> obtenerSenderos() {
		return servicioParque.obtenerSenderos();
	}

	@Override
	public ResultadoAGM obtenerAGM(AlgoritmoAGM algoritmo) {
		return servicioParque.obtenerAGM(algoritmo);
	}

	@Override
	public boolean verificarConectividad() {
		return servicioParque.verificarConectividad();
	}

	@Override
	public Coordinate obtenerCentroParque() {
		return servicioParque.obtenerCentroParque();
	}

	@Override
	public int obtenerZoom() {
		return servicioParque.obtenerZoom();
	}
}
