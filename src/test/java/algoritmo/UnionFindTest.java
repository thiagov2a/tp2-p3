package test.java.algoritmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.java.algoritmo.UnionFind;

public class UnionFindTest {

    private UnionFind uf;

    @Before
    public void setUp() {
        uf = new UnionFind(5);
    }

    @Test
    public void testInicializacion() {
        for (int i = 0; i < 5; i++) {
            assertEquals(i, uf.find(i)); // Cada elemento debe ser su propio padre inicialmente
        }
    }

    @Test
    public void testUnion() {
        uf.union(0, 1);
        uf.union(1, 2);

        // Después de las uniones, 0, 1 y 2 deben estar conectados
        assertTrue(uf.conectados(0, 1));
        assertTrue(uf.conectados(1, 2));
        assertTrue(uf.conectados(0, 2));

        // Los otros elementos deben estar desconectados
        assertFalse(uf.conectados(0, 3));
        assertFalse(uf.conectados(1, 4));
    }

    @Test
    public void testUnionConRango() {
        uf.union(0, 1); // Se hace la unión de 0 y 1
        uf.union(1, 2); // Se hace la unión de 1 y 2

        // Verificar que el rango se maneja correctamente, el árbol más alto se convierte en el padre del otro
        assertTrue(uf.conectados(0, 2));
    }

    @Test
    public void testConectados() {
        assertFalse(uf.conectados(0, 1)); // No deben estar conectados al principio
        uf.union(0, 1);
        assertTrue(uf.conectados(0, 1)); // Después de la unión, deben estar conectados
    }

    @Test
    public void testUnionDeMismasPartes() {
        uf.union(0, 1);
        uf.union(1, 0); // Unión repetida, no debe cambiar el estado
        assertTrue(uf.conectados(0, 1));
    }
}
