package test.java.algoritmo;

import main.java.modelo.Parque;
import main.java.modelo.Estacion;
import main.java.modelo.Sendero;
import main.java.algoritmo.Kruskal;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KruskalTest {

	@Test
    public void testAGM() {
        Parque parque = new Parque("Reserva", -34.5, -58.4, 12);

        Estacion e0 = new Estacion(0, "Estacion A", -14.59, -54.15);
        Estacion e1 = new Estacion(1, "Estacion B", -30.72, -15.69);
        Estacion e2 = new Estacion(2, "Estacion C", -72.13, -82.36);
        Estacion e3 = new Estacion(3, "Estacion D", -36.41, -38.42);

        parque.agregarEstacion(e0);
        parque.agregarEstacion(e1);
        parque.agregarEstacion(e2);
        parque.agregarEstacion(e3);

        parque.agregarSendero(e0, e1, 10);
        parque.agregarSendero(e1, e2, 5);
        parque.agregarSendero(e2, e3, 3);
        parque.agregarSendero(e0, e3, 7);
        parque.agregarSendero(e1, e3, 8);

        Kruskal kruskal = new Kruskal();
        List<Sendero> agm = kruskal.obtenerAGM(parque);
        assertEquals(3, agm.size());
        int impactoTotal = agm.stream().mapToInt(Sendero::obtenerImpactoAmbiental).sum();
        assertEquals(15, impactoTotal); 
    }

    @Test
    public void testAGMVacio() {
        Parque parque = new Parque("",0,0,0);

        Kruskal kruskal = new Kruskal();
        List<Sendero> agm = kruskal.obtenerAGM(parque);

        assertNotNull(agm);
        assertTrue(agm.isEmpty());
    }
}