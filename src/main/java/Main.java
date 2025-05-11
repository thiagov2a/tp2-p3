package main.java;

import javax.swing.JOptionPane;

import main.java.controlador.ControladorParque;

public class Main {

	// TODO: Mover elección del parque a la vista
	public static void main(String[] args) {
		String[] opciones = { "Parque El Palmar", "Parque Glaciares", "Parque Iguazú", "Parque Talampaya" };
		String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un parque", "Inicio",
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion != null) {
			String rutaJson = switch (seleccion) {
			case "Parque El Palmar" -> "src/main/recursos/parque_el_palmar.json";
			case "Parque Glaciares" -> "src/main/recursos/parque_glaciares.json";
			case "Parque Iguazú" -> "src/main/recursos/parque_iguazu.json";
			default -> "src/main/recursos/parque_talampaya.json";
			};

			ControladorParque controlador = new ControladorParque(rutaJson);
			controlador.iniciarAplicacion();
		}
	}
}
