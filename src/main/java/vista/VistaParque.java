package main.java.vista;

import main.java.controlador.ControladorParque;
import main.java.dto.SenderoDTO;

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
	private ControladorParque controlador;

	public VistaParque() {
		String[] opciones = { "Parque El Palmar", "Parque Glaciares", "Parque Iguazú", "Parque Talampaya" };
		String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un parque", "Inicio",
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion == null) {
			System.exit(0);
		}

		String rutaJson = seleccionParque(seleccion);
		this.controlador = new ControladorParque(rutaJson);
	}

	private String seleccionParque(String seleccion) {
		return switch (seleccion) {
			case "Parque El Palmar" -> "src/main/recursos/parque_el_palmar.json";
			case "Parque Glaciares" -> "src/main/recursos/parque_glaciares.json";
			case "Parque Iguazú" -> "src/main/recursos/parque_iguazu.json";
			default -> "src/main/recursos/parque_talampaya.json";
		};
	}

	public void mostrar() {
		inicializar();
		frame.setVisible(true);
		mostrarEstacionesYSenderos();
	}

	private void inicializar() {
		Coordinate centroParque = controlador.obtenerCentroParque();
		int zoomInicial = controlador.obtenerZoomInicial();

		frame = new JFrame("Parque Nacional");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout());

		mapa = new JMapViewer();
		mapa.setZoomControlsVisible(false);
		mapa.setDisplayPosition(centroParque, zoomInicial);

		panelMapa = new JPanel(new BorderLayout());
		panelMapa.add(mapa, BorderLayout.CENTER);
		frame.getContentPane().add(panelMapa, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();

		JButton btnConectividad = new JButton("Verificar conexión");
		btnConectividad.addActionListener(e -> {
			boolean conexo = controlador.verificarConectividad();
			String mensaje = conexo ? "El parque es conexo." : "El parque NO es conexo.";
			JOptionPane.showMessageDialog(frame, mensaje);
		});

		JButton btnAGM = new JButton("Mostrar AGM");
		btnAGM.addActionListener(e -> {
			List<SenderoDTO> agm = controlador.obtenerAGM();
			int impactoTotal = controlador.obtenerImpactoTotalAGM();
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

		for (SenderoDTO s : controlador.obtenerSenderos()) {
			MapPolygonImpl linea = new MapPolygonImpl(List.of(s.getOrigen(), s.getDestino(), s.getOrigen()));
			linea.setColor(colorPorImpacto(s.getImpactoAmbiental()));
			mapa.addMapPolygon(linea);
		}
	}

	private void dibujarSenderosDestacados(List<SenderoDTO> senderos, Color color) {
		for (SenderoDTO s : senderos) {
			MapPolygonImpl linea = new MapPolygonImpl(List.of(s.getOrigen(), s.getDestino(), s.getOrigen()));
			linea.setColor(color);
			mapa.addMapPolygon(linea);
		}
	}

	private Color colorPorImpacto(int impacto) {
		if (impacto <= 3)
			return Color.GREEN;
		else if (impacto <= 6)
			return Color.YELLOW;
		else
			return Color.RED;
	}
}
