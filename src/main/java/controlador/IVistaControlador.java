package main.java.controlador;

import main.java.dto.SenderoDTO;

import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public interface IVistaControlador {

	Map<Integer, Coordinate> obtenerCoordenadasEstaciones();

	String obtenerNombreEstacion(int id);

	List<SenderoDTO> obtenerSenderos();

	List<SenderoDTO> obtenerAGM();

	int obtenerImpactoTotalAGM();

	boolean verificarConectividad();

	Coordinate obtenerCentroParque();

	int obtenerZoomInicial();
}
