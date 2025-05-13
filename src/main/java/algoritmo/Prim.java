package main.java.algoritmo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import main.java.interfaz.AlgoritmoAGM;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;

public class Prim implements AlgoritmoAGM {

	@Override
	public List<Sendero> obtenerAGM(Parque parque) {
		if (parque.obtenerEstaciones().isEmpty()) {
			return new ArrayList<>();
		}

		Set<Estacion> visitadas = new HashSet<>();
		List<Sendero> agm = new ArrayList<>();

		PriorityQueue<Sendero> cola = new PriorityQueue<>(Comparator.comparingInt(Sendero::obtenerImpactoAmbiental));

		// Obtenemos una estación aleatoria del Set
		Estacion inicio = parque.obtenerEstaciones().iterator().next();
		visitadas.add(inicio);
		cola.addAll(parque.obtenerSenderosDesde(inicio));

		while (!cola.isEmpty() && visitadas.size() < parque.obtenerEstaciones().size()) {
			Sendero actual = cola.poll();

			Estacion origen = actual.obtenerOrigen();
			Estacion destino = actual.obtenerDestino();
			Estacion nueva = visitadas.contains(origen) ? destino : origen;

			if (!visitadas.contains(nueva)) {
				visitadas.add(nueva);
				agm.add(actual);

				for (Sendero s : parque.obtenerSenderosDesde(nueva)) {
					Estacion otro = s.obtenerOrigen().equals(nueva)
							? s.obtenerDestino()
							: s.obtenerOrigen();
					if (!visitadas.contains(otro)) {
						cola.add(s);
					}
				}
			}
		}
		return agm;
	}
}
