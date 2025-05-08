package algoritmo;

import modelo.Sendero;
import modelo.Mapa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

	public List<Sendero> obtenerAGM(Mapa mapa) {
		if (mapa.obtenerEstaciones().isEmpty()) {
			return new ArrayList<>();
		}

		// Copia para no modificar la lista original
		List<Sendero> senderos = new ArrayList<>(mapa.obtenerSenderos());
		senderos.sort(Comparator.comparingInt(Sendero::obtenerImpactoAmbiental));

		List<Sendero> arbol = new ArrayList<>();
		UnionFind uf = new UnionFind(mapa.obtenerEstaciones().size());

		for (Sendero sendero : senderos) {
			int origen = sendero.obtenerEstacionOrigen().obtenerId();
			int destino = sendero.obtenerEstacionDestino().obtenerId();

			if (uf.find(origen) != uf.find(destino)) {
				uf.union(origen, destino);
				arbol.add(sendero);

				if (arbol.size() == mapa.obtenerEstaciones().size() - 1) {
					break; // AGM completo
				}
			}
		}
		return arbol;
	}
}
