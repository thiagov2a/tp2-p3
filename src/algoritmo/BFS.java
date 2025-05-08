package algoritmo;

import modelo.Estacion;
import modelo.Mapa;
import modelo.Sendero;

import java.util.*;

public class BFS {

	private static Queue<Estacion> cola;
	private static Set<Estacion> visitados;

	public static boolean esConexo(Mapa mapa) {
		if (mapa == null) {
			throw new IllegalArgumentException("El mapa no puede ser null.");
		}

		if (mapa.obtenerEstaciones().isEmpty()) {
			return true;
		}

		Estacion origen = mapa.obtenerEstaciones().iterator().next();
		Set<Estacion> alcanzables = buscarAlcanzables(mapa, origen);

		return alcanzables.size() == mapa.obtenerEstaciones().size();
	}

	public static Set<Estacion> buscarAlcanzables(Mapa mapa, Estacion origen) {
		if (!mapa.contieneEstacion(origen)) {
			throw new IllegalArgumentException("La estación de origen no está en el mapa.");
		}

		inicializarBusqueda(origen);

		while (!cola.isEmpty()) {
			Estacion actual = cola.poll(); // Selecciono y elimino
			for (Estacion vecino : obtenerVecinosNoVisitados(mapa, actual)) {
				visitados.add(vecino);
				cola.offer(vecino);
			}
		}
		return visitados;
	}

	private static void inicializarBusqueda(Estacion origen) {
		cola = new LinkedList<>();
		visitados = new HashSet<>();

		cola.offer(origen);
		visitados.add(origen);
	}

	private static List<Estacion> obtenerVecinosNoVisitados(Mapa mapa, Estacion estacion) {
		List<Estacion> vecinos = new ArrayList<>();

		for (Sendero sendero : mapa.obtenerSenderosDesde(estacion)) {
			Estacion vecino = sendero.obtenerEstacionOrigen().equals(estacion) ? sendero.obtenerEstacionDestino()
					: sendero.obtenerEstacionOrigen();

			if (!visitados.contains(vecino)) {
				vecinos.add(vecino);
			}
		}
		return vecinos;
	}
}
