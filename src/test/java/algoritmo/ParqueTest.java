package test.java.algoritmo;

import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class ParqueTest {

	private Parque parque;
	private Estacion estacion1, estacion2, estacion3;

	@BeforeEach
	public void inicializarVariables() {
		parque = new Parque("Parque Nacional", -34.5, -58.4, 10);
		estacion1 = new Estacion(1, "Estacion A", -34.51, -58.41);
		estacion2 = new Estacion(2, "Estacion B", -74.12, -38.92);
		estacion3 = new Estacion(3, "Estacion C", -4.83, -84.32);
	}

	@Test
	public void testAgregarEstaciones() {
		parque.agregarEstacion(estacion1);
		parque.agregarEstacion(estacion2);
		assertEquals(2, parque.obtenerEstaciones().size());
		assertTrue(parque.contieneEstacion(estacion1));
	}

	@Test
	void testAgregarEstacionDuplicada() {
		parque.agregarEstacion(estacion1);
		assertThrows(IllegalArgumentException.class, () -> parque.agregarEstacion(estacion1));
	}

	@Test
	void testAgregarSendero() {
		parque.agregarEstacion(estacion1);
		parque.agregarEstacion(estacion2);
		parque.agregarSendero(estacion1, estacion2, 5);
	}

	@Test
	void testAgregarSenderoRepetido() {
		parque.agregarEstacion(estacion1);
		parque.agregarEstacion(estacion2);
		parque.agregarSendero(estacion1, estacion2, 5);
		assertThrows(IllegalArgumentException.class, () -> parque.agregarSendero(estacion1, estacion2, 7));
	}

	@Test
	void testAgregarSenderoConEstacionesNoRegistradas() {
		assertThrows(IllegalArgumentException.class, () -> parque.agregarSendero(estacion1, estacion2, 5));
	}

	@Test
	void testEliminarEstacion() {
		parque.agregarEstacion(estacion1);
		parque.agregarEstacion(estacion2);
		parque.agregarSendero(estacion1, estacion2, 4);

		parque.eliminarEstacion(estacion1);
		assertFalse(parque.contieneEstacion(estacion1));
	}

	@Test
	void testEliminarSendero() {
		parque.agregarEstacion(estacion1);
		parque.agregarEstacion(estacion2);
		parque.agregarSendero(estacion1, estacion2, 2);
		Sendero s = parque.obtenerSenderos().get(0);
		parque.eliminarSendero(s);
		assertTrue(parque.obtenerSenderos().isEmpty());
	}

	@Test
	public void testObtenerSenderosDesde() {
		parque.agregarEstacion(estacion1);
		parque.agregarEstacion(estacion2);
		parque.agregarSendero(estacion1, estacion2, 2);
		Set<Sendero> desdeA = parque.obtenerSenderosDesde(estacion1);
		assertEquals(1, desdeA.size());
	}

	@Test
	public void testConectividad() {
		parque.agregarEstacion(estacion1);
		parque.agregarEstacion(estacion2);
		parque.agregarEstacion(estacion3);
		parque.agregarSendero(estacion1, estacion2, 1);
		parque.agregarSendero(estacion2, estacion3, 1);
		assertTrue(parque.esConexo());

		parque.eliminarSendero(parque.obtenerSenderos().get(0));
		assertFalse(parque.esConexo());
	}

}
