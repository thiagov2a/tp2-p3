package main.java.algoritmo;

import main.java.modelo.Sendero;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.interfaz.AlgoritmoAGM;

import java.util.*;

public class Prim implements AlgoritmoAGM {

	@Override
	public List<Sendero> obtenerAGM(Parque parque) {
	    if (parque.obtenerEstaciones().isEmpty()) {
	        return new ArrayList<>();
	    }

	    Set<Estacion> visitadas = new HashSet<>();
	    List<Sendero> agm = new ArrayList<>();

	    PriorityQueue<Sendero> cola = new PriorityQueue<>(Comparator.comparingInt(Sendero::obtenerImpactoAmbiental));

	    // Obtener una estación inicial arbitraria del Set
	    Estacion inicio = parque.obtenerEstaciones().iterator().next();
	    visitadas.add(inicio);
	    cola.addAll(parque.obtenerSenderosDesde(inicio));

	    while (!cola.isEmpty() && visitadas.size() < parque.obtenerEstaciones().size()) {
	        Sendero actual = cola.poll();

	        Estacion origen = actual.obtenerEstacionOrigen();
	        Estacion destino = actual.obtenerEstacionDestino();
	        Estacion nueva = visitadas.contains(origen) ? destino : origen;

	        if (!visitadas.contains(nueva)) {
	            visitadas.add(nueva);
	            agm.add(actual);

	            for (Sendero s : parque.obtenerSenderosDesde(nueva)) {
	                Estacion otro = s.obtenerEstacionOrigen().equals(nueva)
	                        ? s.obtenerEstacionDestino()
	                        : s.obtenerEstacionOrigen();
	                if (!visitadas.contains(otro)) {
	                    cola.add(s);
	                }
	            }
	        }
	    }
	    return agm;
	}
}
