package controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import algoritmo.Kruskal;
import modelo.Estacion;
import modelo.Parque;
import modelo.Sendero;
import vista.VistaParque;

public class ControladorParque implements IVistaControlador {

	private Parque parque;
	private Map<Integer, Estacion> mapaEstaciones;
	private VistaParque vista;

	public ControladorParque() {
		this.mapaEstaciones = new HashMap<>();
		inicializarModelo();
		this.vista = new VistaParque(this);
	}

	private void inicializarModelo() {
		parque = new Parque("Parque Nacional");

		// Se deben usar IDs a partir de 0 por UnionFind
		agregarEstacion(new Estacion(0, "Mirador del Valle", -27.606, -67.832));
		agregarEstacion(new Estacion(1, "Refugio Laguna", -27.615, -67.825));
		agregarEstacion(new Estacion(2, "Observatorio de Cóndores", -27.610, -67.818));
		agregarEstacion(new Estacion(3, "Puesto de Guardaparques", -27.620, -67.830));
		agregarEstacion(new Estacion(4, "Sitio Cultural Incaico", -27.617, -67.812));

		parque.agregarSendero(mapaEstaciones.get(0), mapaEstaciones.get(1), 3);
		parque.agregarSendero(mapaEstaciones.get(1), mapaEstaciones.get(2), 5);
		parque.agregarSendero(mapaEstaciones.get(2), mapaEstaciones.get(4), 8);
		parque.agregarSendero(mapaEstaciones.get(3), mapaEstaciones.get(1), 4);
		parque.agregarSendero(mapaEstaciones.get(3), mapaEstaciones.get(4), 7);
	}

	private void agregarEstacion(Estacion e) {
		parque.agregarEstacion(e);
		mapaEstaciones.put(e.obtenerId(), e);
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
		return mapaEstaciones.get(id).obtenerNombre();
	}

	@Override
	public List<Sendero> obtenerSenderos() {
		return parque.obtenerSenderos();
	}
}
