package main.java.algoritmo;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import main.java.modelo.Estacion;
import main.java.modelo.Parque;
import main.java.modelo.Sendero;

public class Prim {
    private Parque parque;
    private Set<Estacion> estacionesVisitadas;
    private Set<Sendero> senderosVisitados;
    private static double tiempoEjecucion;

    public Prim(Parque parque) {
        this.parque = parque;
        this.estacionesVisitadas = new HashSet<>();
        this.senderosVisitados = new HashSet<>();
    }

    public void ejecutarPrim(Estacion estacionInicial) {
        long tiempoInicio = System.nanoTime();

        estacionesVisitadas.add(estacionInicial);
        PriorityQueue<Sendero> colaPrioridad = new PriorityQueue<>((a, b) -> Integer.compare(a.obtenerImpactoAmbiental(), b.obtenerImpactoAmbiental()));

        for (Sendero sendero : parque.obtenerSenderosDesde(estacionInicial)) {
            colaPrioridad.add(sendero);
        }

        while (!colaPrioridad.isEmpty() && estacionesVisitadas.size() < parque.obtenerEstaciones().size()) {
            Sendero senderoMinimo = colaPrioridad.poll();
            Estacion estacionNuevo = obtenerEstacionNuevo(senderoMinimo);

            if (!estacionesVisitadas.contains(estacionNuevo)) {
                senderosVisitados.add(senderoMinimo);
                estacionesVisitadas.add(estacionNuevo);

                for (Sendero sendero : parque.obtenerSenderosDesde(estacionNuevo)) {
                    if (!senderosVisitados.contains(sendero)) {
                        colaPrioridad.add(sendero);
                    }
                }
            }
        }
        tiempoEjecucion = System.nanoTime() - tiempoInicio;

    }

    private Estacion obtenerEstacionNuevo(Sendero sendero) {
        if (estacionesVisitadas.contains(sendero.obtenerEstacionOrigen())) {
            return sendero.obtenerEstacionDestino();
        } else {
            return sendero.obtenerEstacionOrigen();
        }
    }

    public Set<Estacion> getEstacionesVisitadas() {
        return estacionesVisitadas;
    }

    public Set<Sendero> getSenderosVisitados() {
        return senderosVisitados;
    }
    public double obtenerTiempoEjecucion() {
        return tiempoEjecucion;
    }
}
