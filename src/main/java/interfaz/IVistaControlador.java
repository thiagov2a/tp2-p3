package main.java.interfaz;

import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import main.java.dto.ResultadoAGM;
import main.java.dto.SenderoDTO;

public interface IVistaControlador {

	Map<Integer, Coordinate> obtenerCoordenadasEstaciones();

	String obtenerNombreEstacion(int id);

	List<SenderoDTO> obtenerSenderos();

	ResultadoAGM obtenerAGM(AlgoritmoAGM algoritmo);

	boolean verificarConectividad();

	Coordinate obtenerCentroParque();

	int obtenerZoom();
}
