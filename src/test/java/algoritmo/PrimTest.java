package test.java.algoritmo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import main.java.algoritmo.Prim;
import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;

public class PrimTest {
	@Test
	public void testAGMPrim() {
	    Parque parque = new Parque("Parque Nacional", -34.5, -58.4, 12);

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

	    Prim prim = new Prim();
	    List<Sendero> agm = prim.obtenerAGM(parque);

	    assertEquals(3, agm.size());
	    int impacto = agm.stream().mapToInt(Sendero::obtenerImpactoAmbiental).sum();
	    assertEquals(15, impacto);

	    assertTrue(agm.stream().anyMatch(s -> s.obtenerImpactoAmbiental() == 3));
	    assertTrue(agm.stream().anyMatch(s -> s.obtenerImpactoAmbiental() == 5));
	    assertTrue(agm.stream().anyMatch(s -> s.obtenerImpactoAmbiental() == 7));
	}

	@Test
	public void testAGMPrimVacio() {
	    Parque parqueVacio = new Parque("", 0, 0, 10); 

	    Prim prim = new Prim();
	    List<Sendero> agm = prim.obtenerAGM(parqueVacio);

	    assertNotNull(agm);
	    assertTrue(agm.isEmpty());
	}
}
