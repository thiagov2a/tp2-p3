package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mapa {

	private Set<Ciudad> ciudades;
	private List<Conexion> conexiones;

	public Mapa() {
		ciudades = new HashSet<>();
		conexiones = new ArrayList<>();
	}

	public void agregarCiudad(Ciudad ciudad) {
		ciudades.add(ciudad);
	}

	public void agregarConexion(Ciudad origen, Ciudad destino, double impactoAmbiental) {
		if (!ciudades.contains(origen) || !ciudades.contains(destino)) {
			throw new IllegalArgumentException("Ambas ciudades deben estar registradas en el mapa.");
		}
		conexiones.add(new Conexion(origen, destino, impactoAmbiental));
	}

	public Set<Ciudad> obtenerCiudades() {
		return ciudades;
	}

	public List<Conexion> obtenerConexiones() {
		return conexiones;
	}
}
