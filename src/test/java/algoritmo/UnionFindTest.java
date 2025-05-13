package test.java.algoritmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.algoritmo.UnionFind;

public class UnionFindTest {

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
