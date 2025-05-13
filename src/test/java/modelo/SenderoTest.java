package test.java.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.java.modelo.Estacion;
import main.java.modelo.Sendero;

public class SenderoTest {

	private Estacion origen;
	private Estacion destino;

	@Before
	public void setUp() {
		origen = new Estacion(0, "Estacion A", 0, 0);
		destino = new Estacion(1, "Estacion B", 1, 1);
	}

	@Test
	public void testCrearSenderoValido() {
		Sendero sendero = new Sendero(origen, destino, 5);
		assertNotNull(sendero);
		assertEquals(origen, sendero.obtenerOrigen());
		assertEquals(destino, sendero.obtenerDestino());
		assertEquals(5, sendero.obtenerImpactoAmbiental());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCrearSenderoMismaEstacion() {
		// Intentar crear un sendero con la misma estación como origen y destino
		new Sendero(origen, origen, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCrearSenderoImpactoInvalido() {
		// Intentar crear un sendero con un impacto fuera del rango
		new Sendero(origen, destino, 0); // Impacto 0 es inválido
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCrearSenderoImpactoInvalidoAlto() {
		// Intentar crear un sendero con un impacto fuera del rango
		new Sendero(origen, destino, 11); // Impacto 11 es inválido
	}

	@Test
	public void testContieneEstacion() {
		Sendero sendero = new Sendero(origen, destino, 5);
		assertTrue(sendero.contieneEstacion(origen)); // Debe contener la estación de origen
		assertTrue(sendero.contieneEstacion(destino)); // Debe contener la estación de destino
	}

	@Test
	public void testNoContieneOtraEstacion() {
		Estacion estacionC = new Estacion(2, "Estacion C", 2, 2);
		Sendero sendero = new Sendero(origen, destino, 5);
		assertFalse(sendero.contieneEstacion(estacionC)); // No debe contener una estación no relacionada
	}
}
