package main.java.vista;

import main.java.controlador.ControladorParque;
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
	private ControladorParque controlador;

	public VistaParque() {
		String[] opciones = { "Parque El Palmar", "Parque Glaciares", "Parque Iguazú", "Parque Talampaya" };
		String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un parque", "Inicio",
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion == null) {
			System.exit(0);
		}

		String rutaJson = seleccionParque(seleccion);

		ControladorParque ctrl = new ControladorParque(rutaJson);
		this.controlador = ctrl;
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

			MapPolygonImpl linea = new MapPolygonImpl(List.of(c1, c2, c1));
			Color colorImpacto = colorPorImpacto(s.obtenerImpactoAmbiental());
			linea.setColor(colorImpacto);
			mapa.addMapPolygon(linea);
		}
	}
	
    private void bloquearZoom() {
        mapa.addMouseListener(new java.awt.event.MouseAdapter() {});
        mapa.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {});
        mapa.addMouseWheelListener(e -> {});  // Ignorar zoom rueda
    }

	private Color colorPorImpacto(int impacto) {
		if (impacto <= 3)
			return Color.GREEN;
		else if (impacto <= 6)
			return Color.YELLOW;
		else
			return Color.RED;
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
		return senderosAGM.stream()
				.mapToInt(Sendero::obtenerImpactoAmbiental)
				.sum();
	}
}
