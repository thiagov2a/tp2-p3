package main.vista;

import main.algoritmo.Kruskal;
import main.modelo.Ciudad;
import main.modelo.Conexion;
import main.modelo.Mapa;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear el grafo
        Mapa grafo = new Mapa();

        // Crear ciudades
        Ciudad buenosAires = new Ciudad(0, "Buenos Aires");
        Ciudad cordoba = new Ciudad(1, "Córdoba");
        Ciudad rosario = new Ciudad(2, "Rosario");
        Ciudad mendoza = new Ciudad(3, "Mendoza");

        // Agregar ciudades al grafo
        grafo.agregarCiudad(buenosAires);
        grafo.agregarCiudad(cordoba);
        grafo.agregarCiudad(rosario);
        grafo.agregarCiudad(mendoza);

        // Conectar ciudades (origen, destino, impacto ambiental)
        grafo.agregarConexion(buenosAires, cordoba, 10);
        grafo.agregarConexion(buenosAires, rosario, 8);
        grafo.agregarConexion(rosario, cordoba, 5);
        grafo.agregarConexion(cordoba, mendoza, 7);
        grafo.agregarConexion(buenosAires, mendoza, 20);

        // Aplicar algoritmo de Kruskal para obtener el árbol generador mínimo
        Kruskal kruskal = new Kruskal();
        List<Conexion> mst = kruskal.obtenerArbolGeneradorMinimo(grafo);

        // Mostrar resultado
        System.out.println("Red de ciudades óptima (con menor impacto ambiental):");
        for (Conexion conexion : mst) {
            System.out.println(conexion.getOrigen().getNombre() + " <--> " +
                               conexion.getDestino().getNombre() +
                               " | Impacto ambiental: " + conexion.getImpactoAmbiental());
        }
    }
}
