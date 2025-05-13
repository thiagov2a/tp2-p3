package main.java.servicio;

import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConsumoParque {

	public static Parque cargarParqueDesdeJson(String rutaArchivo) {
		try (FileReader reader = new FileReader(rutaArchivo)) {
			JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

			Parque parque = construirParque(json);
			Map<Integer, Estacion> estaciones = cargarEstaciones(json.getAsJsonArray("estaciones"), parque);
			cargarSenderos(json.getAsJsonArray("senderos"), parque, estaciones);

			return parque;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al cargar el parque: " + e.getMessage());
		}
	}

	private static Parque construirParque(JsonObject json) {
		String nombre = json.get("nombre").getAsString();
		double centroLatitud = json.get("centroLatitud").getAsDouble();
		double centroLongitud = json.get("centroLongitud").getAsDouble();
		int zoomInicial = json.get("zoomInicial").getAsInt();
		return new Parque(nombre, centroLatitud, centroLongitud, zoomInicial);
	}

	private static Map<Integer, Estacion> cargarEstaciones(JsonArray estacionesJson, Parque parque) {
		Map<Integer, Estacion> estaciones = new HashMap<>();

		for (int i = 0; i < estacionesJson.size(); i++) {
			JsonObject obj = estacionesJson.get(i).getAsJsonObject();

			int id = obj.get("id").getAsInt();
			String nombre = obj.get("nombre").getAsString();
			double latitud = obj.get("latitud").getAsDouble();
			double longitud = obj.get("longitud").getAsDouble();

			Estacion estacion = new Estacion(id, nombre, latitud, longitud);
			parque.agregarEstacion(estacion);
			estaciones.put(id, estacion);
		}
		return estaciones;
	}

	private static void cargarSenderos(JsonArray senderosJson, Parque parque, Map<Integer, Estacion> estaciones) {
		for (int i = 0; i < senderosJson.size(); i++) {
			JsonObject obj = senderosJson.get(i).getAsJsonObject();

			int origenId = obj.get("origen").getAsInt();
			int destinoId = obj.get("destino").getAsInt();
			int impacto = obj.get("impacto").getAsInt();

			Estacion origen = estaciones.get(origenId);
			Estacion destino = estaciones.get(destinoId);

			parque.agregarSendero(origen, destino, impacto);
		}
	}
}
