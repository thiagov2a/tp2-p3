package main.java.controlador;

import main.java.algoritmo.Kruskal;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;
import main.java.vista.VistaParque;
import main.java.servicio.ConsumoParque;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorParque implements IVistaControlador {

	private Parque parque;
	private VistaParque vista;

	public ControladorParque() {
		this.parque = ConsumoParque.cargarParqueDesdeJson("src/main/recursos/parque.json"); // Ruta del archivo JSON
		this.vista = new VistaParque(this);
	}

	public boolean verificarConectividad() {
		return parque.esConexo();
	}

	public List<Sendero> obtenerAGM() {
		return Kruskal.obtenerAGM(parque);
	}

	public void iniciarAplicacion() {
		vista.mostrar();
	}

	// Implementación de la interfaz
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
		return parque.obtenerEstaciones().stream().filter(e -> e.obtenerId() == id).map(e -> e.obtenerNombre())
				.findFirst().orElse("Desconocido");
	}

	@Override
	public List<Sendero> obtenerSenderos() {
		return parque.obtenerSenderos();
	}
}
