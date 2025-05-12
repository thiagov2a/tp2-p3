package main.java.controlador;

import main.java.algoritmo.Kruskal;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;
import main.java.servicio.ConsumoParque;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class ControladorParque implements IVistaControlador {

	private Parque parque;

	public ControladorParque(String rutaJson) {
		this.parque = ConsumoParque.cargarParqueDesdeJson(rutaJson);
	}

	@Override
	public Map<Integer, Coordinate> obtenerCoordenadasEstaciones() {
		Map<Integer, Coordinate> coords = new HashMap<>();
		for (Estacion e : parque.obtenerEstaciones()) {
			coords.put(e.obtenerId(), new Coordinate(e.obtenerLatitud(), e.obtenerLongitud()));
		}
		return coords;
	}

	@Override
	public String obtenerNombreEstacion(int id) {
		return parque.obtenerEstaciones().stream().filter(e -> e.obtenerId() == id).map(Estacion::obtenerNombre)
				.findFirst().orElse("Desconocido");
	}

	@Override
	public List<Sendero> obtenerSenderos() {
		return parque.obtenerSenderos();
	}

	public boolean verificarConectividad() {
		return parque.esConexo();
	}

	public List<Sendero> obtenerAGM() {
		return Kruskal.obtenerAGM(parque);
	}

	public Coordinate obtenerCentroParque() {
		return new Coordinate(parque.obtenerCentroLatitud(), parque.obtenerCentroLongitud());
	}

	public int obtenerZoomInicial() {
		return parque.obtenerZoomInicial();
	}
}
