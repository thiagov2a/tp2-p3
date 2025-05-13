package test.java.servicio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.servicio.ConsumoParque;

public class ConsumoParqueTest {

	private Parque parque;

	@Before
	public void setUp() {
		parque = ConsumoParque.cargarParqueDesdeJson("src/test/recursos/parque_test_conexo.json");
	}

	@Test
	public void testCargarParque() {
		assertNotNull(parque);
		assertEquals("Parque de Prueba", parque.obtenerNombre());
		assertEquals(-34.0, parque.obtenerLatitud(), 0.001);
		assertEquals(-58.0, parque.obtenerLongitud(), 0.001);
		assertEquals(13, parque.obtenerZoom());
	}

	@Test
	public void testCargarEstaciones() {
		Set<Estacion> estaciones = parque.obtenerEstaciones();
		assertEquals(3, estaciones.size());
	}

	@Test
	public void testCargarSenderos() {
		assertEquals(2, parque.obtenerSenderos().size());
	}
}
