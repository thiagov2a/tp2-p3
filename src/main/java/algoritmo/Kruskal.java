package main.java.algoritmo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.java.interfaz.IAlgoritmoAGM;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;

public class Kruskal implements IAlgoritmoAGM {

	@Override
	public List<Sendero> obtenerAGM(Parque parque) {
		if (parque.obtenerEstaciones().isEmpty()) {
			return new ArrayList<>();
		}

		// Copia para no modificar la lista original
		List<Sendero> senderos = new ArrayList<>(parque.obtenerSenderos());
		senderos.sort(Comparator.comparingInt(Sendero::obtenerImpactoAmbiental));

		List<Sendero> agm = new ArrayList<>();
		UnionFind uf = new UnionFind(parque.obtenerEstaciones().size());

		for (Sendero sendero : senderos) {
			int origen = sendero.obtenerOrigen().obtenerId();
			int destino = sendero.obtenerDestino().obtenerId();

			if (uf.find(origen) != uf.find(destino)) {
				uf.union(origen, destino);
				agm.add(sendero);

				if (agm.size() == parque.obtenerEstaciones().size() - 1) {
					break; // AGM completo
				}
			}
		}
		return agm;
	}
}
