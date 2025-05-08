package algoritmo;

import modelo.Conexion;
import modelo.Mapa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

	public List<Conexion> obtenerArbolGeneradorMinimo(Mapa mapa) {
		List<Conexion> arbol = new ArrayList<>();
		List<Conexion> conexiones = mapa.obtenerConexiones().stream()
				.sorted(Comparator.comparingDouble(Conexion::getImpactoAmbiental)).toList();

		UnionFind uf = new UnionFind(mapa.obtenerCiudades().size());

		for (Conexion conexion : conexiones) {
			int origen = conexion.getOrigen().getIndice();
			int destino = conexion.getDestino().getIndice();

			if (uf.find(origen) != uf.find(destino)) {
				uf.union(origen, destino);
				arbol.add(conexion);

				if (arbol.size() == mapa.obtenerCiudades().size() - 1) {
					break;
				}
			}
		}

		return arbol;
	}
}
