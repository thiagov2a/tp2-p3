package test.java.servicio;

import org.junit.jupiter.api.Test;
import main.java.modelo.Parque;
import main.java.servicio.ConsumoParque;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileWriter;

public class ConsumoParqueTest {

    @Test
    public void testCargarParque() throws Exception {
    	//Agregamos un parque al json, en este caso usamos uno de los disponibles y lo agregamos de manera temporal para no generar comflicto luego
        String json = """
        {
          "nombre": "Parque Nacional El Palmar",
          "centroLatitud": -31.860,
          "centroLongitud": -58.320,
          "zoomInicial": 14,
          "estaciones": [
            { "id": 0, "nombre": "Centro de Visitantes", "latitud": -31.850, "longitud": -58.320 },
            { "id": 1, "nombre": "Sendero de los Palmares", "latitud": -31.855, "longitud": -58.325 },
            { "id": 2, "nombre": "Mirador del Río", "latitud": -31.860, "longitud": -58.330 },
            { "id": 3, "nombre": "Estación del Bosque", "latitud": -31.865, "longitud": -58.315 },
            { "id": 4, "nombre": "Refugio de Aves", "latitud": -31.870, "longitud": -58.310 }
          ],
          "senderos": [
            { "origen": 0, "destino": 1, "impacto": 2 },
            { "origen": 1, "destino": 2, "impacto": 6 },
            { "origen": 2, "destino": 3, "impacto": 4 },
            { "origen": 3, "destino": 4, "impacto": 8 },
            { "origen": 0, "destino": 3, "impacto": 5 }
          ]
        }
        """;

        File tempFile = File.createTempFile("parque_el_palmar", ".json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(json);
        }
        Parque parque = ConsumoParque.cargarParqueDesdeJson(tempFile.getAbsolutePath());
        
        //Test para construirParque()
        assertNotNull(parque);
        assertEquals("Parque Nacional El Palmar", parque.obtenerNombre());
        assertEquals(-31.860, parque.obtenerCentroLatitud(), 0.001);
        assertEquals(-58.320, parque.obtenerCentroLongitud(), 0.001);
        assertEquals(14, parque.obtenerZoomInicial());
        
        //Test para cargarEstaciones()
        assertEquals(5, parque.obtenerEstaciones().size());
        assertEquals(5, parque.obtenerSenderos().size());
        
        //Test para cargarSenderos()
        boolean existeSendero = parque.obtenerSenderos().stream().anyMatch(s ->
                s.obtenerEstacionOrigen().obtenerNombre().equals("Centro de Visitantes") &&
                s.obtenerEstacionDestino().obtenerNombre().equals("Sendero de los Palmares") &&
                s.obtenerImpactoAmbiental() == 2
        );
        assertTrue(existeSendero);

        tempFile.delete();
    }
}