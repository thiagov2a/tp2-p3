package controlador;

import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import modelo.Sendero;

public interface IVistaControlador {

	public Map<Integer, Coordinate> obtenerCoordenadasEstaciones();

	public String obtenerNombreEstacion(int id);

	public List<Sendero> obtenerSenderos();

	public List<Sendero> obtenerAGM();

	public boolean verificarConectividad();
}
