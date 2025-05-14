package test.java.servicio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import main.java.dto.ResultadoAGM;
import main.java.dto.SenderoDTO;
import main.java.interfaz.IAlgoritmoAGM;
import main.java.modelo.Sendero;
import main.java.servicio.ServicioParque;

public class ServicioParqueTest {

	private ServicioParque servicio;

	@Before
	public void setUp() {
		servicio = new ServicioParque("src/test/recursos/parque_test_conexo.json");
	}

	@Test
	public void testObtenerCoordenadasEstaciones() {
		Map<Integer, Coordinate> coords = servicio.obtenerCoordenadasEstaciones();
		assertEquals(3, coords.size());
		assertTrue(coords.containsKey(0));
		assertTrue(coords.containsKey(1));
		assertTrue(coords.containsKey(2));
	}

	@Test
	public void testObtenerNombreEstacionExistente() {
		String nombre = servicio.obtenerNombreEstacion(1);
		assertEquals("Estación B", nombre);
	}

	@Test
	public void testObtenerNombreEstacionInexistente() {
		String nombre = servicio.obtenerNombreEstacion(999);
		assertEquals("Desconocido", nombre);
	}

	@Test
	public void testObtenerSenderos() {
		List<SenderoDTO> senderos = servicio.obtenerSenderos();
		assertEquals(2, senderos.size());
		for (SenderoDTO s : senderos) {
			assertNotNull(s.obtenerOrigen());
			assertNotNull(s.obtenerDestino());
			assertTrue(s.obtenerImpactoAmbiental() >= 1 && s.obtenerImpactoAmbiental() <= 10);
		}
	}

	@Test
	public void testVerificarConectividad() {
		assertTrue(servicio.verificarConectividad());
	}

	@Test
	public void testObtenerCentroParque() {
		Coordinate centro = servicio.obtenerCentroParque();
		assertEquals(-34.0, centro.getLat(), 0.001);
		assertEquals(-58.0, centro.getLon(), 0.001);
	}

	@Test
	public void testObtenerZoom() {
		int zoom = servicio.obtenerZoom();
		assertEquals(13, zoom);
	}

	@Test
	public void testObtenerAGMConAlgoritmoMock() {
		IAlgoritmoAGM algoritmoMock = new IAlgoritmoAGM() {
			@Override
			public List<Sendero> obtenerAGM(main.java.modelo.Parque parque) {
				return parque.obtenerSenderos().subList(0, 1);
			}
		};

		ResultadoAGM resultado = servicio.obtenerAGM(algoritmoMock);
		assertEquals(1, resultado.obtenerSenderos().size());
		assertTrue(resultado.obtenerImpactoTotal() > 0);
		assertTrue(resultado.obtenerTiempoEjecucion() >= 0.0);
	}
}
