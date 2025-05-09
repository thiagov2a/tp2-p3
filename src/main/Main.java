package main;

import algoritmo.Kruskal;
import modelo.Estacion;
import modelo.Mapa;
import modelo.Sendero;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// Crear el mapa del parque nacional
		Mapa parqueNacional = new Mapa();

		// Crear estaciones del parque (a partir de índice 0)
		Estacion miradorPrincipal = new Estacion(0, "Mirador Principal", 100, 50);
		Estacion refugioNorte = new Estacion(1, "Refugio Norte", 150, 120);
		Estacion cascadaEscondida = new Estacion(2, "Cascada Escondida", 200, 30);
		Estacion centroVisitantes = new Estacion(3, "Centro de Visitantes", 50, 100);
		Estacion lagunaAzul = new Estacion(4, "Laguna Azul", 180, 180);

		// Agregar estaciones al mapa
		parqueNacional.agregarEstacion(miradorPrincipal);
		parqueNacional.agregarEstacion(refugioNorte);
		parqueNacional.agregarEstacion(cascadaEscondida);
		parqueNacional.agregarEstacion(centroVisitantes);
		parqueNacional.agregarEstacion(lagunaAzul);

		// Conectar estaciones con senderos (origen, destino, impacto ambiental 1-10)
		parqueNacional.agregarSendero(centroVisitantes, miradorPrincipal, 3);
		parqueNacional.agregarSendero(miradorPrincipal, refugioNorte, 5);
		parqueNacional.agregarSendero(miradorPrincipal, cascadaEscondida, 2);
		parqueNacional.agregarSendero(refugioNorte, lagunaAzul, 8);
		parqueNacional.agregarSendero(cascadaEscondida, lagunaAzul, 4);
		parqueNacional.agregarSendero(centroVisitantes, refugioNorte, 7);

		// Verificar si el mapa es conexo
		if (!parqueNacional.esConexo()) {
			System.err.println("El parque tiene áreas no conectadas. "
					+ "No se puede calcular un árbol generador mínimo para todo el parque.");
			return;
		}

		// Selección de algoritmo
		Scanner scanner = new Scanner(System.in);
		System.out.println("Seleccione el algoritmo para calcular el AGM:");
		System.out.println("1. Kruskal");
		System.out.println("2. Prim");
		System.out.print("Opción: ");
		int opcion = scanner.nextInt();

		List<Sendero> senderosOptimos;
		long inicio = System.currentTimeMillis();

		if (opcion == 1) {
			// Calcular AGM con Kruskal
			Kruskal kruskal = new Kruskal();
			senderosOptimos = kruskal.obtenerAGM(parqueNacional);
			System.out.println("\n[Algoritmo de Kruskal]");
		} else {
			// Calcular AGM con Prim
			scanner.close();
			return;
		}

		long fin = System.currentTimeMillis();

		System.out.println("\nRed óptima de senderos con menor impacto ambiental:");
		System.out.println("--------------------------------------------------");

		int impactoTotal = 0;
		for (Sendero sendero : senderosOptimos) {
			System.out.print(sendero.toString());
			impactoTotal += sendero.obtenerImpactoAmbiental();
		}

		System.out.println("\nImpacto ambiental total: " + impactoTotal);
		System.out.println("Número de senderos a construir: " + senderosOptimos.size());
		System.out.println("Tiempo de cálculo: " + (fin - inicio) + " ms");

		System.out.println("\nEstadísticas del parque:");
		System.out.println("Estaciones: " + parqueNacional.obtenerEstaciones().size());
		System.out.println("Senderos posibles: " + parqueNacional.obtenerSenderos().size());
		System.out.println("Senderos en AGM: " + senderosOptimos.size());

		scanner.close();
	}
}
