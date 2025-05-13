package test.java.controlador;

import main.java.controlador.ControladorParque;
import main.java.dto.SenderoDTO;
import main.java.modelo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ControladorTest {

    private ControladorParque controlador;

    @BeforeEach
    public void setUp() {
        Parque parque = new Parque("Parque Nacional", -34.5, -58.4, 12);

        Estacion e0 = new Estacion(0,"Estacion A", -14.59, -54.15);
        Estacion e1 = new Estacion(1,"Estacion B", -30.72, -15.69);
        Estacion e2 = new Estacion(2,"Estacion C", -72.13, -82.36);
        Estacion e3 = new Estacion(3,"Estacion D", -36.41, -38.42);

        parque.agregarEstacion(e0);
        parque.agregarEstacion(e1);
        parque.agregarEstacion(e2);
        parque.agregarEstacion(e3);

        parque.agregarSendero(e0, e1, 10);
        parque.agregarSendero(e1, e2, 5);
        parque.agregarSendero(e2, e3, 3);
        parque.agregarSendero(e0, e3, 7);
        parque.agregarSendero(e1, e3, 8);

    }

    @Test
    public void testObtenerCoordenadasEstaciones() {
        Map<Integer, Coordinate> coords = controlador.obtenerCoordenadasEstaciones();

        assertEquals(4, coords.size());
        assertEquals(new Coordinate(-14.59, -54.15), coords.get(0));
        assertEquals(new Coordinate(-30.72, -15.69), coords.get(1));
    }

    @Test
    public void testObtenerNombreEstacion() {
        assertEquals("Estacion A", controlador.obtenerNombreEstacion(0));
        assertEquals("Estacion C", controlador.obtenerNombreEstacion(2));
        assertEquals("Desconocido",controlador.obtenerNombreEstacion(9999));
    }

    @Test
    public void testObtenerSenderos() {
        List<SenderoDTO> senderos = controlador.obtenerSenderos();
        assertEquals(5, senderos.size());

        boolean contieneUno = senderos.stream().anyMatch(s ->
            s.obtenerImpactoAmbiental() == 10 &&
            s.obtenerOrigen().equals(new Coordinate(-14.59, -54.15)) &&
            s.obtenerDestino().equals(new Coordinate(-30.72, -15.69))
        );
        assertTrue(contieneUno);
    }
    //El test del obtenerAGM ya se testea en los test de ambos algoritmos
}
