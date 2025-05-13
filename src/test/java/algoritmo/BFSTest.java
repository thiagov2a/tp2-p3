package test.java.algoritmo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.java.algoritmo.BFS;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;

public class BFSTest {

	private Parque parque;

	@Before
	public void setUp() {
		parque = new Parque("Parque Nacional", 0.0, 0.0, 0);
	}

	@Test
	public void testParqueVacioEsConexo() {
		// No tiene estaciones
		assertTrue(BFS.esConexo(parque));
	}

	@Test
	public void testUnaSolaEstacionEsConexo() {
		Estacion unica = new Estacion(0, "Unica", 0, 0);
		parque.agregarEstacion(unica);
		assertTrue(BFS.esConexo(parque));
	}

	@Test
	public void testDosEstacionesConectadasEsConexo() {
		Estacion a = new Estacion(0, "A", 0, 0);
		Estacion b = new Estacion(1, "B", 1, 1);
		parque.agregarEstacion(a);
		parque.agregarEstacion(b);
		parque.agregarSendero(a, b, 5);
		assertTrue(BFS.esConexo(parque));
	}

	@Test
	public void testDosEstacionesNoConectadasNoEsConexo() {
		Estacion a = new Estacion(0, "A", 0, 0);
		Estacion b = new Estacion(1, "B", 1, 1);
		parque.agregarEstacion(a);
		parque.agregarEstacion(b);
		// No se conectan
		assertFalse(BFS.esConexo(parque));
	}

	@Test
	public void testParqueConCicloEsConexo() {
		Estacion a = new Estacion(0, "A", 0, 0);
		Estacion b = new Estacion(1, "B", 1, 1);
		Estacion c = new Estacion(2, "C", 2, 2);
		parque.agregarEstacion(a);
		parque.agregarEstacion(b);
		parque.agregarEstacion(c);
		parque.agregarSendero(a, b, 2);
		parque.agregarSendero(b, c, 3);
		parque.agregarSendero(c, a, 4); // Forman un ciclo
		assertTrue(BFS.esConexo(parque));
	}

	@Test
	public void testParqueConDosComponentesNoEsConexo() {
		Estacion a = new Estacion(0, "A", 0, 0);
		Estacion b = new Estacion(1, "B", 1, 1);
		Estacion c = new Estacion(2, "C", 2, 2);
		Estacion d = new Estacion(3, "D", 3, 3);
		parque.agregarEstacion(a);
		parque.agregarEstacion(b);
		parque.agregarEstacion(c);
		parque.agregarEstacion(d);
		parque.agregarSendero(a, b, 1); // Componente conexo 1
		parque.agregarSendero(c, d, 1); // Componente conexo 2
		assertFalse(BFS.esConexo(parque));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExcepcionSiParqueEsNull() {
		BFS.esConexo(null);
	}

	@Test
	public void testTodosLosSenderosVisitados() {
		// Esto prueba que BFS visita todos los nodos a través de múltiples caminos
		Estacion a = new Estacion(0, "A", 0, 0);
		Estacion b = new Estacion(1, "B", 1, 1);
		Estacion c = new Estacion(2, "C", 2, 2);
		Estacion d = new Estacion(3, "D", 3, 3);

		parque.agregarEstacion(a);
		parque.agregarEstacion(b);
		parque.agregarEstacion(c);
		parque.agregarEstacion(d);

		parque.agregarSendero(a, b, 1);
		parque.agregarSendero(b, c, 1);
		parque.agregarSendero(a, d, 1);

		assertTrue(BFS.esConexo(parque));
	}

	@Test
	public void testParqueConEstacionesYSinSenderosNoEsConexo() {
		Estacion a = new Estacion(0, "A", 0, 0);
		Estacion b = new Estacion(1, "B", 1, 1);
		Estacion c = new Estacion(2, "C", 2, 2);
		parque.agregarEstacion(a);
		parque.agregarEstacion(b);
		parque.agregarEstacion(c);
		// No tiene senderos
		assertFalse(BFS.esConexo(parque));
	}
}
