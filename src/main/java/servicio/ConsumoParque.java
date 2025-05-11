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
		Parque parque;

		try (FileReader reader = new FileReader(rutaArchivo)) {
			JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

			// Crear el parque
			String nombreParque = json.get("nombre").getAsString();
			parque = new Parque(nombreParque);

			// Parsear estaciones
			JsonArray estacionesJson = json.getAsJsonArray("estaciones");
			Map<Integer, Estacion> estacionesPorId = new HashMap<>();

			for (int i = 0; i < estacionesJson.size(); i++) {
				JsonObject est = estacionesJson.get(i).getAsJsonObject();

				int id = est.get("id").getAsInt();
				String nombre = est.get("nombre").getAsString();
				double latitud = est.get("latitud").getAsDouble();
				double longitud = est.get("longitud").getAsDouble();

				Estacion estacion = new Estacion(id, nombre, latitud, longitud);
				parque.agregarEstacion(estacion);
				estacionesPorId.put(id, estacion);
			}

			// Parsear senderos
			JsonArray senderosJson = json.getAsJsonArray("senderos");

			for (int i = 0; i < senderosJson.size(); i++) {
				JsonObject s = senderosJson.get(i).getAsJsonObject();

				int origenId = s.get("origen").getAsInt();
				int destinoId = s.get("destino").getAsInt();
				int impacto = s.get("impacto").getAsInt();

				Estacion origen = estacionesPorId.get(origenId);
				Estacion destino = estacionesPorId.get(destinoId);

				parque.agregarSendero(origen, destino, impacto);
			}

			return parque;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al cargar el parque: " + e.getMessage());
		}
	}
}
