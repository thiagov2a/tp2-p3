package main.java;

import javax.swing.JOptionPane;

import main.java.controlador.ControladorParque;

public class Main {

	// TODO: Mover elección del parque a la vista
	public static void main(String[] args) {
		String[] opciones = { "Parque A", "Parque B", "Parque C", "Parque D" };
		String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un parque", "Inicio",
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion != null) {
			String rutaJson = switch (seleccion) {
			case "Parque A" -> "src/main/recursos/parqueA.json";
			case "Parque B" -> "src/main/recursos/parqueB.json";
			case "Parque C" -> "src/main/recursos/parqueC.json";
			default -> "src/main/recursos/parqueD.json";
			};

			ControladorParque controlador = new ControladorParque();
			controlador.iniciarAplicacion();
		}
	}
}
