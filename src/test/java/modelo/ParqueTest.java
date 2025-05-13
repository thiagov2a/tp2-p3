package test.java.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;

public class ParqueTest {

    private Parque parque;

    @Before
    public void setUp() {
        parque = new Parque("Parque Nacional", 0.0, 0.0, 10);
    }

    @Test
    public void testAgregarEstacion() {
        Estacion estacion = new Estacion(0, "Estacion A", 0, 0);
        parque.agregarEstacion(estacion);
        assertTrue(parque.contieneEstacion(estacion));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarEstacionDuplicada() {
        Estacion estacion = new Estacion(0, "Estacion A", 0, 0);
        parque.agregarEstacion(estacion);
        parque.agregarEstacion(estacion); // Duplicada, debe lanzar excepción
    }

    @Test
    public void testEliminarEstacion() {
        Estacion estacion = new Estacion(0, "Estacion A", 0, 0);
        parque.agregarEstacion(estacion);
        parque.eliminarEstacion(estacion);
        assertFalse(parque.contieneEstacion(estacion));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarEstacionNoExistente() {
        Estacion estacion = new Estacion(0, "Estacion A", 0, 0);
        parque.eliminarEstacion(estacion); // No está registrada, debe lanzar excepción
    }

    @Test
    public void testAgregarSendero() {
        Estacion origen = new Estacion(0, "Estacion A", 0, 0);
        Estacion destino = new Estacion(1, "Estacion B", 1, 1);
        parque.agregarEstacion(origen);
        parque.agregarEstacion(destino);
        parque.agregarSendero(origen, destino, 5);
        assertTrue(parque.obtenerSenderosDesde(origen).size() > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarSenderoConEstacionNoRegistrada() {
        Estacion origen = new Estacion(0, "Estacion A", 0, 0);
        Estacion destino = new Estacion(1, "Estacion B", 1, 1);
        parque.agregarSendero(origen, destino, 5); // Las estaciones aún no están agregadas, debe lanzar excepción
    }

    @Test
    public void testEliminarSendero() {
        Estacion origen = new Estacion(0, "Estacion A", 0, 0);
        Estacion destino = new Estacion(1, "Estacion B", 1, 1);
        parque.agregarEstacion(origen);
        parque.agregarEstacion(destino);
        parque.agregarSendero(origen, destino, 5);
        Sendero sendero = parque.obtenerSenderosDesde(origen).iterator().next();
        parque.eliminarSendero(sendero);
        assertTrue(parque.obtenerSenderosDesde(origen).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarSenderoNoExistente() {
        Estacion origen = new Estacion(0, "Estacion A", 0, 0);
        Estacion destino = new Estacion(1, "Estacion B", 1, 1);
        Sendero sendero = new Sendero(origen, destino, 5);
        parque.eliminarSendero(sendero); // El sendero no existe, debe lanzar excepción
    }

    @Test
    public void testEsConexoConEstacionesYSenderosConectados() {
        Estacion a = new Estacion(0, "A", 0, 0);
        Estacion b = new Estacion(1, "B", 1, 1);
        parque.agregarEstacion(a);
        parque.agregarEstacion(b);
        parque.agregarSendero(a, b, 5);
        assertTrue(parque.esConexo());
    }

    @Test
    public void testEsConexoConEstacionesSinSenderos() {
        Estacion a = new Estacion(0, "A", 0, 0);
        Estacion b = new Estacion(1, "B", 1, 1);
        parque.agregarEstacion(a);
        parque.agregarEstacion(b);
        // No hay senderos, no es conexo
        assertFalse(parque.esConexo());
    }

    @Test
    public void testObtenerCentroLatitud() {
        assertEquals(0.0, parque.obtenerLatitud(), 0.001);
    }

    @Test
    public void testObtenerCentroLongitud() {
        assertEquals(0.0, parque.obtenerLongitud(), 0.001);
    }

    @Test
    public void testObtenerZoomInicial() {
        assertEquals(10, parque.obtenerZoom());
    }

    @Test
    public void testObtenerNombre() {
        assertEquals("Parque Nacional", parque.obtenerNombre());
    }
}
