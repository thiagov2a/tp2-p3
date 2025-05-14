package main.java.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.algoritmo.BFS;

public class Parque {

	private String nombre;
	private double latitud;
	private double longitud;
	private int zoom;	// Zoom inicial para la Vista
	private Set<Estacion> estaciones;
	private List<Sendero> senderos;
	private Map<Estacion, Set<Sendero>> mapaAdyacencia;

	public Parque(String nombre, double latitud, double longitud, int zoom) {
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
		this.zoom = zoom;
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
		return senderos.stream()
				.anyMatch(s -> s.obtenerOrigen() == nuevoSendero.obtenerOrigen()
							&& s.obtenerDestino() == nuevoSendero.obtenerDestino());
	}

	public boolean contieneEstacion(Estacion estacion) {
		return estaciones.contains(estacion);
	}

	public void eliminarEstacion(Estacion estacion) {
		if (!estaciones.remove(estacion)) {
			throw new IllegalArgumentException("La estación no existe en el mapa");
		}

		Set<Sendero> senderosAEliminar = new HashSet<>(mapaAdyacencia.get(estacion));
		senderos.removeAll(senderosAEliminar);

		mapaAdyacencia.remove(estacion);
		mapaAdyacencia.values().forEach(s -> s.removeAll(senderosAEliminar));
	}

	public void eliminarSendero(Sendero sendero) {
		if (!senderos.remove(sendero)) {
			throw new IllegalArgumentException("El sendero no existe en el mapa");
		}

		mapaAdyacencia.get(sendero.obtenerOrigen()).remove(sendero);
		mapaAdyacencia.get(sendero.obtenerDestino()).remove(sendero);
	}

	public double obtenerLatitud() {
		return latitud;
	}

	public double obtenerLongitud() {
		return longitud;
	}

	public int obtenerZoom() {
		return zoom;
	}

	// Devuelve un Set inmutable de estaciones
	public Set<Estacion> obtenerEstaciones() {
	    return Collections.unmodifiableSet(estaciones);
	}

	// Devuelve una List inmutable de senderos
	public List<Sendero> obtenerSenderos() {
	    return Collections.unmodifiableList(senderos);
	}

	// Devuelve un Set inmutable de senderos desde una estación. Si no hay, devuelve vacío
	public Set<Sendero> obtenerSenderosDesde(Estacion estacion) {
	    return Collections.unmodifiableSet(mapaAdyacencia.getOrDefault(estacion, new HashSet<>()));
	}

	public boolean esConexo() {
		return BFS.esConexo(this);
	}

	public String obtenerNombre() {
		return this.nombre;
	}
}
