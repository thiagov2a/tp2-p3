package test.java.algoritmo;

import main.java.algoritmo.BFS;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;

import org.junit.Test;

import static org.junit.Assert.*;

public class BFSTest {

	@Test
	public void testMapaVacio() {
		Parque mapa = new Parque("Parque Nacional");
		assertTrue(BFS.esConexo(mapa));
	}

	@Test
	public void testMapaConexo() {
		Parque mapa = crearMapaConexo();
		assertTrue(BFS.esConexo(mapa));
	}

	@Test
	public void testMapaNoConexo() {
		Parque mapa = crearMapaNoConexo();
		assertFalse(BFS.esConexo(mapa));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMapaNull() {
		BFS.esConexo(null);
	}

	private Parque crearMapaConexo() {
		Parque mapa = new Parque("Parque Nacional");
		Estacion a = new Estacion(0, "Estacion A", 0, 0);
		Estacion b = new Estacion(1, "Estacion B", 1, 1);

		mapa.agregarEstacion(a);
		mapa.agregarEstacion(b);
		mapa.agregarSendero(a, b, 1);

		return mapa;
	}

	private Parque crearMapaNoConexo() {
		Parque mapa = new Parque("Parque Nacional");
		Estacion a = new Estacion(0, "Estacion A", 0, 0);
		Estacion b = new Estacion(1, "Estacion B", 1, 1);

		mapa.agregarEstacion(a);
		mapa.agregarEstacion(b);
		// No agregamos senderos, por lo que no están conectados

		return mapa;
	}
}
