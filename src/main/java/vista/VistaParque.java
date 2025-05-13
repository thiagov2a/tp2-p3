package main.java.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import main.java.algoritmo.Kruskal;
import main.java.algoritmo.Prim;
import main.java.controlador.ServicioParque;
import main.java.dto.ResultadoAGM;
import main.java.dto.SenderoDTO;
import main.java.interfaz.AlgoritmoAGM;

public class VistaParque {

	private JFrame frame;
	private JMapViewer mapa;
	private JPanel panelMapa;
	private ServicioParque controlador;

	public VistaParque() {
		String[] opciones = { "Parque El Palmar", "Parque Glaciares", "Parque Iguazú", "Parque Talampaya" };
		String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un parque", "Inicio",
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion == null) {
			System.exit(0);
		}

		String rutaJson = seleccionParque(seleccion);
		this.controlador = new ServicioParque(rutaJson);
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
		int zoomInicial = controlador.obtenerZoom();

		frame = new JFrame("Parque Nacional");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout());

		mapa = new JMapViewer();
		mapa.setZoomControlsVisible(false);
		mapa.setDisplayPosition(centroParque, zoomInicial);
		mapa.setEnabled(false);

		panelMapa = new JPanel(new BorderLayout());
		panelMapa.add(mapa, BorderLayout.CENTER);
		frame.getContentPane().add(panelMapa, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());

		JButton btnAGM = new JButton("Calcular AGM");
		btnAGM.addActionListener(e -> {
			if (!controlador.verificarConectividad()) {
				JOptionPane.showMessageDialog(frame, "El parque no es conexo. No se puede calcular el AGM.");
				return;
			}

			String[] opcionesAlg = { "Prim", "Kruskal" };
			String algoritmo = (String) JOptionPane.showInputDialog(frame, "Seleccione el algoritmo", "Algoritmo AGM",
					JOptionPane.QUESTION_MESSAGE, null, opcionesAlg, opcionesAlg[0]);

			if (algoritmo != null) {
				AlgoritmoAGM algoritmoAGM = algoritmo.equals("Prim") ? new Prim() : new Kruskal();

				ResultadoAGM resultado = controlador.obtenerAGM(algoritmoAGM);
				dibujarSenderosDestacados(resultado.obtenerSenderos());

				JOptionPane.showMessageDialog(frame, "Impacto total: " + resultado.obtenerImpactoTotal()
						+ "\nTiempo de ejecución: " + resultado.obtenerTiempoEjecucion() + " ms");
			}
		});

		JButton btnVolver = new JButton("Cambiar Parque");
		btnVolver.addActionListener(e -> {
			frame.dispose();
			new VistaParque().mostrar();
		});

		panelBotones.add(btnAGM);
		panelBotones.add(btnVolver);
		frame.getContentPane().add(panelBotones, BorderLayout.SOUTH);
	}

	private void mostrarEstacionesYSenderos() {
		Map<Integer, Coordinate> mapaEstaciones = controlador.obtenerCoordenadasEstaciones();

		for (Map.Entry<Integer, Coordinate> entry : mapaEstaciones.entrySet()) {
			String nombre = controlador.obtenerNombreEstacion(entry.getKey());
			mapa.addMapMarker(new MapMarkerDot(nombre, entry.getValue()));
		}

		for (SenderoDTO s : controlador.obtenerSenderos()) {
			MapPolygonImpl linea = new MapPolygonImpl(
					List.of(s.obtenerOrigen(), s.obtenerDestino(), s.obtenerOrigen()));
			linea.setColor(colorPorImpacto(s.obtenerImpactoAmbiental()));
			mapa.addMapPolygon(linea);
		}
	}

	private void dibujarSenderosDestacados(List<SenderoDTO> senderos) {
		limpiarMapa();

		for (SenderoDTO s : senderos) {
			MapPolygonImpl linea = new MapPolygonImpl(
					List.of(s.obtenerOrigen(), s.obtenerDestino(), s.obtenerOrigen()));
			linea.setColor(colorPorImpacto(s.obtenerImpactoAmbiental()));
			mapa.addMapPolygon(linea);
		}
	}

	private void limpiarMapa() {
		mapa.removeAllMapPolygons();
		// mapa.removeAllMapMarkers();
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
