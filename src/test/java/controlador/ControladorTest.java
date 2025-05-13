package test.java.controlador;

import org.junit.jupiter.api.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import main.java.controlador.ControladorParque;
import main.java.dto.SenderoDTO;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ControladorTest {
	
    private ControladorParque controlador;
    
    @Test
    public void testObtenerCoordenadasEstaciones() {
        Map<Integer, Coordinate> coords = controlador.obtenerCoordenadasEstaciones();
        assertEquals(2, coords.size());
        assertEquals(new Coordinate(10.0, 20.0), coords.get(0));
        assertEquals(new Coordinate(30.0, 40.0), coords.get(1));
    }

    @Test
    public void testObtenerNombreEstacion() {
        String nombre = controlador.obtenerNombreEstacion(0);
        assertEquals("A", nombre);

        String desconocido = controlador.obtenerNombreEstacion(99);
        assertEquals("Desconocido", desconocido);
    }

    @Test
    public void testObtenerSenderos() {
        List<SenderoDTO> senderos = controlador.obtenerSenderos();
        assertEquals(1, senderos.size());
        SenderoDTO dto = senderos.get(0);
        assertEquals(new Coordinate(10.0, 20.0), dto.obtenerOrigen());
        assertEquals(new Coordinate(30.0, 40.0), dto.obtenerDestino());
        assertEquals(5, dto.obtenerImpactoAmbiental());
    }
    //El test del obtenerAGM ya se testea en los test de ambos algoritmos
}
