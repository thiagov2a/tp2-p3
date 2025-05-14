package main.java.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import main.java.dto.ResultadoAGM;
import main.java.dto.SenderoDTO;
import main.java.interfaz.IAlgoritmoAGM;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;

public class ServicioParque {

	private Parque parque;

	public ServicioParque(String rutaJson) {
		this.parque = ConsumoParque.cargarParqueDesdeJson(rutaJson);
	}

	public Map<Integer, Coordinate> obtenerCoordenadasEstaciones() {
		Map<Integer, Coordinate> coords = new HashMap<>();

		Set<Estacion> estaciones = parque.obtenerEstaciones();
		for (Estacion e : estaciones) {
			Coordinate coord = new Coordinate(e.obtenerLatitud(), e.obtenerLongitud());
			coords.put(e.obtenerId(), coord);
		}
		return coords;
	}

	public String obtenerNombreEstacion(int id) {
		return parque.obtenerEstaciones().stream()
				.filter(e -> e.obtenerId() == id)
				.map(Estacion::obtenerNombre)
				.findFirst()
				.orElse("Desconocido");
	}

	public List<SenderoDTO> obtenerSenderos() {
		Map<Integer, Coordinate> coords = obtenerCoordenadasEstaciones();

		return parque.obtenerSenderos().stream()
				.map(s -> new SenderoDTO(
						coords.get(s.obtenerOrigen().obtenerId()),
						coords.get(s.obtenerDestino().obtenerId()),
						s.obtenerImpactoAmbiental()))
				.collect(Collectors.toList());
	}

	public ResultadoAGM obtenerAGM(IAlgoritmoAGM algoritmo) {
		Map<Integer, Coordinate> coords = obtenerCoordenadasEstaciones();

		long inicio = System.nanoTime();
		List<Sendero> resultado = algoritmo.obtenerAGM(parque);
		long fin = System.nanoTime() - inicio;

		List<SenderoDTO> senderos = resultado.stream()
				.map(s -> new SenderoDTO(
						coords.get(s.obtenerOrigen().obtenerId()),
						coords.get(s.obtenerDestino().obtenerId()),
						s.obtenerImpactoAmbiental()))
				.toList();

		int impactoTotal = resultado.stream().mapToInt(Sendero::obtenerImpactoAmbiental).sum();
		double tiempo = fin / 1000000.0;

		return new ResultadoAGM(senderos, impactoTotal, tiempo);
	}

	public boolean verificarConectividad() {
		return parque.esConexo();
	}

	public Coordinate obtenerCentroParque() {
		return new Coordinate(parque.obtenerLatitud(), parque.obtenerLongitud());
	}

	public int obtenerZoom() {
		return parque.obtenerZoom();
	}
}
