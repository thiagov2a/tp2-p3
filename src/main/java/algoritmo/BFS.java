package main.java.algoritmo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;

public class BFS {

	public static boolean esConexo(Parque mapa) {
		if (mapa == null) {
			throw new IllegalArgumentException("El mapa no puede ser null.");
		}

		Set<Estacion> estaciones = mapa.obtenerEstaciones();
		if (estaciones.isEmpty()) {
			return true; // Un grafo vacío es conexo por convención
		}

		Estacion origen = estaciones.iterator().next();
		Set<Estacion> visitados = new HashSet<>();

		Queue<Estacion> cola = new LinkedList<>();
		cola.add(origen);
		visitados.add(origen);

		while (!cola.isEmpty()) {
			Estacion actual = cola.poll();
			for (Sendero s : mapa.obtenerSenderosDesde(actual)) {
				Estacion vecino = obtenerOtroExtremo(actual, s);
				if (!visitados.contains(vecino)) {
					visitados.add(vecino);
					cola.add(vecino);
				}
			}
		}

		return visitados.size() == estaciones.size();
	}

	private static Estacion obtenerOtroExtremo(Estacion actual, Sendero s) {
		return s.obtenerOrigen().equals(actual) ? s.obtenerDestino() : s.obtenerOrigen();
	}
}
