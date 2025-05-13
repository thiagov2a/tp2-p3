package test.java.algoritmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.java.algoritmo.Kruskal;
import main.java.algoritmo.UnionFind;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;
import main.java.servicio.ConsumoParque;

public class KruskalTest {

	private Parque parqueConexo;
	private Parque parqueDesconexo;
	private Kruskal kruskal;

	@Before
	public void setUp() {
		parqueConexo = ConsumoParque.cargarParqueDesdeJson("src/test/recursos/parque_test_conexo.json");
		parqueDesconexo = ConsumoParque.cargarParqueDesdeJson("src/test/recursos/parque_test_desconexo.json");
		kruskal = new Kruskal();
	}

	@Test
	public void testAGMParqueConexo() {
		List<Sendero> agm = kruskal.obtenerAGM(parqueConexo);

		// Verificar que el número de senderos en el AGM es correcto (n-1)
		int estaciones = parqueConexo.obtenerEstaciones().size();
		assertEquals(estaciones - 1, agm.size());
		assertTrue(esArbol(agm, estaciones));
	}

	@Test
	public void testAGMParqueDesconexo() {
		List<Sendero> agm = kruskal.obtenerAGM(parqueDesconexo);

		// Verificar que no se puede obtener un AGM completo (menos de n-1 senderos)
		int estaciones = parqueDesconexo.obtenerEstaciones().size();
		assertTrue(agm.size() < estaciones - 1);
	}

	@Test
	public void testAGMParqueVacio() {
		Parque parqueVacio = new Parque("Parque Vacio", 0.0, 0.0, 0);
		List<Sendero> agm = kruskal.obtenerAGM(parqueVacio);
		assertEquals(0, agm.size());
	}

	private boolean esArbol(List<Sendero> senderos, int cantidadEstaciones) {
		UnionFind uf = new UnionFind(cantidadEstaciones);
		for (Sendero sendero : senderos) {
			int u = sendero.obtenerOrigen().obtenerId();
			int v = sendero.obtenerDestino().obtenerId();
			if (uf.conectados(u, v)) {
				return false;
			}
			uf.union(u, v);
		}
		return true;
	}
}
