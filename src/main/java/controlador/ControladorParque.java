package main.java.controlador;

import main.java.algoritmo.Kruskal;
import main.java.dto.SenderoDTO;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;
import main.java.servicio.ConsumoParque;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class ControladorParque implements IVistaControlador {

	private final Parque parque;

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
		return parque.obtenerEstaciones().stream()
				.filter(e -> e.obtenerId() == id)
				.map(Estacion::obtenerNombre)
				.findFirst()
				.orElse("Desconocido");
	}

	@Override
	public List<SenderoDTO> obtenerSenderos() {
		Map<Integer, Coordinate> coords = obtenerCoordenadasEstaciones();

		return parque.obtenerSenderos().stream()
				.map(s -> new SenderoDTO(coords.get(s.obtenerEstacionOrigen().obtenerId()),
						coords.get(s.obtenerEstacionDestino().obtenerId()), s.obtenerImpactoAmbiental()))
				.collect(Collectors.toList());
	}

	@Override
	public List<SenderoDTO> obtenerAGM() {
		Map<Integer, Coordinate> coords = obtenerCoordenadasEstaciones();

		return Kruskal.obtenerAGM(parque).stream()
				.map(s -> new SenderoDTO(coords.get(s.obtenerEstacionOrigen().obtenerId()),
						coords.get(s.obtenerEstacionDestino().obtenerId()), s.obtenerImpactoAmbiental()))
				.collect(Collectors.toList());
	}

	@Override
	public int obtenerImpactoTotalAGM() {
		return Kruskal.obtenerAGM(parque).stream().mapToInt(Sendero::obtenerImpactoAmbiental).sum();
	}

	@Override
	public boolean verificarConectividad() {
		return parque.esConexo();
	}

	@Override
	public Coordinate obtenerCentroParque() {
		return new Coordinate(parque.obtenerCentroLatitud(), parque.obtenerCentroLongitud());
	}

	@Override
	public int obtenerZoomInicial() {
		return parque.obtenerZoomInicial();
	}
}
