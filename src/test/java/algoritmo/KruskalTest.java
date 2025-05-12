package test.java.algoritmo;

import main.java.modelo.Parque;
import main.java.modelo.Estacion;
import main.java.modelo.Sendero;
import main.java.algoritmo.Kruskal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KruskalTest {

    private Parque parque;
    private Estacion estacion1, estacion2, estacion3, estacion4;

    @BeforeEach
    void inicializarVariables() {
		parque = new Parque("Parque Nacional", -34.1, -58.4, 10);

		estacion1 = new Estacion(0, "Estacion A", -54.51, -48.11);
		estacion2 = new Estacion(1, "Estacion B", -74.12, -38.92);
		estacion3 = new Estacion(2, "Estacion C", -4.83, -84.32);
        estacion4 = new Estacion(3, "Estacion D", -14.93, -78.44);

        parque.agregarEstacion(estacion1);
        parque.agregarEstacion(estacion2);
        parque.agregarEstacion(estacion3);
        parque.agregarEstacion(estacion4);

        parque.agregarSendero(estacion1, estacion2, 4);
        parque.agregarSendero(estacion1, estacion3, 3);
        parque.agregarSendero(estacion2, estacion3, 1);
        parque.agregarSendero(estacion2, estacion4, 2);
        parque.agregarSendero(estacion3, estacion4, 5);
    }

    @Test
    void testObtenerAGM() {
        List<Sendero> agm = Kruskal.obtenerAGM(parque);
        assertEquals(3, agm.size());
        int impactoTotal = agm.stream().mapToInt(Sendero::obtenerImpactoAmbiental).sum();
        assertEquals(6, impactoTotal);
    }

    @Test
    void testAGMVacioSiNoHayEstaciones() {
        Parque parqueVacio = new Parque("", 0.0, 0.0, 0);
        List<Sendero> agm = Kruskal.obtenerAGM(parqueVacio);
        assertTrue(agm.isEmpty());
    }

    @Test
    void testTiempoDeEjecucion() {
        Kruskal.obtenerAGM(parque);
        double tiempo = new Kruskal().obtenerTiempoEjecucion();
        assertTrue(tiempo > 0);
    }
}