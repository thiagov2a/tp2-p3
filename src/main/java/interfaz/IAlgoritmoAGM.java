package main.java.interfaz;

import java.util.List;

import main.java.modelo.Sendero;
import main.java.modelo.Parque;

public interface IAlgoritmoAGM {

	List<Sendero> obtenerAGM(Parque parque);
}
