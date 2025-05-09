package vista;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import controlador.IVistaControlador;
import modelo.Sendero;

public class VistaParque {

	private JFrame frame;
	private JMapViewer mapa;
	private JPanel panelMapa;
	private IVistaControlador controlador;

	public VistaParque(IVistaControlador controlador) {
		this.controlador = controlador;
		initialize();
	}

	public void mostrar() {
		frame.setVisible(true);
		mostrarEstacionesYSenderos();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panelMapa = new JPanel();
		panelMapa.setBounds(10, 11, 764, 540);
		frame.getContentPane().add(panelMapa);
		panelMapa.setLayout(null);

		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 764, 540);
		mapa.setZoomContolsVisible(true);
		mapa.setDisplayPosition(new Coordinate(-27.615, -67.825), 13);
		panelMapa.add(mapa);
	}

	private void mostrarEstacionesYSenderos() {
		Map<Integer, Coordinate> mapaEstaciones = controlador.obtenerCoordenadasEstaciones();

		for (Map.Entry<Integer, Coordinate> entry : mapaEstaciones.entrySet()) {
			String nombre = controlador.obtenerNombreEstacion(entry.getKey());
			mapa.addMapMarker(new MapMarkerDot(nombre, entry.getValue()));
		}

		for (Sendero s : controlador.obtenerSenderos()) {
			Coordinate c1 = mapaEstaciones.get(s.obtenerEstacionOrigen().obtenerId());
			Coordinate c2 = mapaEstaciones.get(s.obtenerEstacionDestino().obtenerId());

			MapPolygonImpl linea = new MapPolygonImpl(List.of(c1, c2, c1));
			linea.setColor(colorPorImpacto(s.obtenerImpactoAmbiental()));
			mapa.addMapPolygon(linea);
		}
	}

	private Color colorPorImpacto(int impacto) {
		if (impacto <= 3)
			return Color.GREEN;
		if (impacto <= 6)
			return Color.ORANGE;
		return Color.RED;
	}
}
