package test;

import org.junit.Test;
import static org.junit.Assert.*;
import modelo.Ciudad;
import modelo.Mapa;
import algoritmo.UnionFind;


public class TestPrueba {
	@Test
	public void testCiudad() {
		Mapa grafo = new Mapa();
		Ciudad buenosAires = new Ciudad(0, "Buenos Aires");
		Ciudad cordoba = new Ciudad(1, "Córdoba");
		grafo.agregarCiudad(buenosAires);
		grafo.agregarCiudad(cordoba);
		grafo.agregarConexion(buenosAires, cordoba, 10);
	}
	@Test
    public void testFind() {
        UnionFind uf = new UnionFind(5);
        assertEquals(0, uf.find(0));
        assertEquals(3, uf.find(3));
    }

    @Test
    public void testUnion() {
        UnionFind uf = new UnionFind(3);
        uf.union(0, 1);
        assertTrue(uf.conectados(0, 1));
        assertFalse(uf.conectados(0, 2));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testFindConIndiceInvalido() {
        UnionFind uf = new UnionFind(2);
        uf.find(5);
    }
}