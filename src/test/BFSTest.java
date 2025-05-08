package test;

import org.junit.Test;

import algoritmo.BFS;
import modelo.Estacion;
import modelo.Mapa;

import static org.junit.Assert.*;

public class BFSTest {

	@Test
	public void testMapaVacio() {
		Mapa mapa = new Mapa();
		assertTrue(BFS.esConexo(mapa));
	}

	@Test
	public void testMapaConexo() {
		Mapa mapa = crearMapaConexo();
		assertTrue(BFS.esConexo(mapa));
	}

	@Test
	public void testMapaNoConexo() {
		Mapa mapa = crearMapaNoConexo();
		assertFalse(BFS.esConexo(mapa));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMapaNull() {
		BFS.esConexo(null);
	}

	private Mapa crearMapaConexo() {
		Mapa mapa = new Mapa();
		Estacion a = new Estacion(0, "Estacion A", 0, 0);
		Estacion b = new Estacion(1, "Estacion B", 1, 1);

		mapa.agregarEstacion(a);
		mapa.agregarEstacion(b);
		mapa.agregarSendero(a, b, 1);

		return mapa;
	}

	private Mapa crearMapaNoConexo() {
		Mapa mapa = new Mapa();
		Estacion a = new Estacion(0, "Estacion A", 0, 0);
		Estacion b = new Estacion(1, "Estacion B", 1, 1);

		mapa.agregarEstacion(a);
		mapa.agregarEstacion(b);
		// No agregamos senderos, por lo que no están conectados

		return mapa;
	}
}
