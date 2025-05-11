package main.java.vista;

import main.java.controlador.IVistaControlador;
import main.java.modelo.Sendero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

public class VistaParque {

	private JFrame frame;
	private JMapViewer mapa;
	private JPanel panelMapa;
	private IVistaControlador controlador;

	public VistaParque(IVistaControlador controlador, Coordinate centroParque, int zoomInicial) {
		this.controlador = controlador;
		inicializar(centroParque, zoomInicial);
	}

	public void mostrar() {
		frame.setVisible(true);
		mostrarEstacionesYSenderos();
	}

	private void inicializar(Coordinate centroParque, int zoomInicial) {
		frame = new JFrame("Parque Nacional");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // Centra la ventana
		frame.getContentPane().setLayout(new BorderLayout());

		// Panel con el mapa
		mapa = new JMapViewer();
		mapa.setZoomControlsVisible(false);
		mapa.setDisplayPosition(centroParque, zoomInicial);

		panelMapa = new JPanel(new BorderLayout());
		panelMapa.add(mapa, BorderLayout.CENTER);

		frame.getContentPane().add(panelMapa, BorderLayout.CENTER);

		// Panel inferior con botones
		JPanel panelBotones = new JPanel();

		JButton btnConectividad = new JButton("Verificar conexión");
		btnConectividad.addActionListener(e -> {
			boolean conexo = controlador.verificarConectividad();
			String mensaje = conexo ? "El parque es conexo." : "El parque NO es conexo.";
			JOptionPane.showMessageDialog(frame, mensaje);
		});

		JButton btnAGM = new JButton("Mostrar AGM");
		btnAGM.addActionListener(e -> {
			List<Sendero> agm = controlador.obtenerAGM();
			int impactoTotal = calcularImpactoTotal(agm);
			dibujarSenderosDestacados(agm, Color.BLUE);
			JOptionPane.showMessageDialog(frame, "Impacto ambiental total: " + impactoTotal);
		});

		panelBotones.add(btnConectividad);
		panelBotones.add(btnAGM);

		frame.getContentPane().add(panelBotones, BorderLayout.SOUTH);
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

			// Crear la línea del sendero
			MapPolygonImpl linea = new MapPolygonImpl(List.of(c1, c2, c1));
			Color colorImpacto = colorPorImpacto(s.obtenerImpactoAmbiental());
			linea.setColor(colorImpacto);

			// Añadir la línea al mapa
			mapa.addMapPolygon(linea);

			/*
			 * // Crear un marcador de texto con el impacto ambiental String impactoTexto =
			 * "Impacto: " + s.obtenerImpactoAmbiental(); // Ubicación media del sendero
			 * Coordinate medio = new Coordinate((c1.getLat() + c2.getLat()) / 2,
			 * (c1.getLon() + c2.getLon()) / 2); MapMarkerDot marcadorImpacto = new
			 * MapMarkerDot(impactoTexto, medio); marcadorImpacto.setColor(colorImpacto);
			 * mapa.addMapMarker(marcadorImpacto);
			 */
		}
	}

	private Color colorPorImpacto(int impacto) {
		if (impacto <= 3) {
			return Color.GREEN; // Bajo impacto
		} else if (impacto <= 6) {
			return Color.YELLOW; // Impacto intermedio
		} else {
			return Color.RED; // Alto impacto
		}
	}

	private void dibujarSenderosDestacados(List<Sendero> senderos, Color color) {
		Map<Integer, Coordinate> mapaEstaciones = controlador.obtenerCoordenadasEstaciones();

		for (Sendero s : senderos) {
			Coordinate c1 = mapaEstaciones.get(s.obtenerEstacionOrigen().obtenerId());
			Coordinate c2 = mapaEstaciones.get(s.obtenerEstacionDestino().obtenerId());

			MapPolygonImpl linea = new MapPolygonImpl(List.of(c1, c2, c1));
			linea.setColor(color);
			mapa.addMapPolygon(linea);
		}
	}

	private int calcularImpactoTotal(List<Sendero> senderosAGM) {
		int impactoTotal = 0;
		for (Sendero sendero : senderosAGM) {
			impactoTotal += sendero.obtenerImpactoAmbiental();
		}
		return impactoTotal;
	}
}
