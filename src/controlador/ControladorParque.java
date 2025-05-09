package controlador;

import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import modelo.Estacion;
import modelo.Parque;
import modelo.Sendero;
import vista.VistaParque;

public class ControladorParque implements IVistaControlador {

	private Parque parque;
	private VistaParque vista;

	public ControladorParque() {
		inicializarModelo();
		this.vista = new VistaParque(this);
	}

	private void inicializarModelo() {
	}

	public void iniciarAplicacion() {
		vista.mostrar();
	}

	@Override
	public Map<Integer, Coordinate> obtenerCoordenadasEstaciones() {
		return null;
	}

	@Override
	public String obtenerNombreEstacion(int id) {
		return null;
	}

	@Override
	public List<Sendero> obtenerSenderos() {
		return null;
	}
}
