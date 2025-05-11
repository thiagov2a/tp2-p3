package main.java.modelo;

import main.java.algoritmo.BFS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parque {

	private String nombre;
	private double centroLatitud;
	private double centroLongitud;
	private int zoomInicial;
	private final Set<Estacion> estaciones;
	private final List<Sendero> senderos;
	private final Map<Estacion, Set<Sendero>> mapaAdyacencia;

	public Parque(String nombre, double centroLatitud, double centroLongitud, int zoomInicial) {
		this.nombre = nombre;
		this.centroLatitud = centroLatitud;
		this.centroLongitud = centroLongitud;
		this.zoomInicial = zoomInicial;
		this.estaciones = new HashSet<>();
		this.senderos = new ArrayList<>();
		this.mapaAdyacencia = new HashMap<>();
	}

	public void agregarEstacion(Estacion estacion) {
		if (!estaciones.add(estacion)) {
			throw new IllegalArgumentException("La estación " + estacion.obtenerNombre() + " ya existe");
		}
		mapaAdyacencia.put(estacion, new HashSet<>());
	}

	public void agregarSendero(Estacion origen, Estacion destino, int impacto) {
		if (!estaciones.contains(origen) || !estaciones.contains(destino)) {
			throw new IllegalArgumentException("Ambas estaciones deben estar registradas en el mapa");
		}

		Sendero nuevoSendero = new Sendero(origen, destino, impacto);

		if (existeConexion(nuevoSendero)) {
			throw new IllegalArgumentException(
					"Ya existe un sendero entre " + origen.obtenerNombre() + " y " + destino.obtenerNombre());
		}

		senderos.add(nuevoSendero);
		mapaAdyacencia.get(origen).add(nuevoSendero);
		mapaAdyacencia.get(destino).add(nuevoSendero);
	}

	private boolean existeConexion(Sendero nuevoSendero) {
		return senderos.stream().anyMatch(s -> s.obtenerEstacionOrigen() == nuevoSendero.obtenerEstacionOrigen()
				&& s.obtenerEstacionDestino() == nuevoSendero.obtenerEstacionDestino());
	
	}

	public boolean contieneEstacion(Estacion estacion) {
		return estaciones.contains(estacion);
	}

	public void eliminarEstacion(Estacion estacion) {
		if (!estaciones.remove(estacion)) {
			throw new IllegalArgumentException("La estación no existe en el mapa");
		}

		// Eliminar todos los senderos conectados
		Set<Sendero> senderosAEliminar = new HashSet<>(mapaAdyacencia.get(estacion));
		senderos.removeAll(senderosAEliminar);

		// Actualizar mapa de adyacencia
		mapaAdyacencia.remove(estacion);
		mapaAdyacencia.values().forEach(s -> s.removeAll(senderosAEliminar));
	}

	public void eliminarSendero(Sendero sendero) {
		if (!senderos.remove(sendero)) {
			throw new IllegalArgumentException("El sendero no existe en el mapa");
		}

		mapaAdyacencia.get(sendero.obtenerEstacionOrigen()).remove(sendero);
		mapaAdyacencia.get(sendero.obtenerEstacionDestino()).remove(sendero);
	}

	public double obtenerCentroLatitud() {
		return centroLatitud;
	}

	public double obtenerCentroLongitud() {
		return centroLongitud;
	}

	public int obtenerZoomInicial() {
		return zoomInicial;
	}

	public Set<Estacion> obtenerEstaciones() {
		return Collections.unmodifiableSet(estaciones);
	}

	public List<Sendero> obtenerSenderos() {
		return Collections.unmodifiableList(senderos);
	}

	public Set<Sendero> obtenerSenderosDesde(Estacion estacion) {
		return Collections.unmodifiableSet(mapaAdyacencia.getOrDefault(estacion, new HashSet<>()));
	}

	public boolean esConexo() {
		return BFS.esConexo(this);
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
