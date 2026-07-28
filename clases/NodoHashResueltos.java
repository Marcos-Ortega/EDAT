package clases;

import GrafoHabitaciones.estructurasAuxiliares.Lista;

public class NodoHashResueltos {
    private String nombreEquipo;
    private Lista desafiosResueltos;
    private NodoHashResueltos siguiente;

    public NodoHashResueltos(String nombreEquipo, Lista desafiosResueltos, NodoHashResueltos siguiente) {
        this.nombreEquipo = nombreEquipo;
        this.desafiosResueltos = desafiosResueltos;
        this.siguiente = siguiente;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public Lista getDesafiosResueltos() {
        return desafiosResueltos;
    }

    public NodoHashResueltos getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoHashResueltos siguiente) {
        this.siguiente = siguiente;
    }
}