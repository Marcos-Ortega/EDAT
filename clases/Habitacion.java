package clases;

import estructurasAuxiliares.*;
import estructurasAuxiliares.Conjuntista.ArbolAVL;

public class Habitacion implements Comparable<Habitacion> {
    private int codigo;
    private String nombre;
    private int planta;
    private int metros;
    private boolean tieneSalida;
    private ArbolAVL desafios; // cada habitacion conoce sus desafios

    // constructores
    public Habitacion(int unCodigo) {
        this.codigo = unCodigo;
        this.desafios = new ArbolAVL();
    }

    public Habitacion(int unCodigo, String nom, int unaPlanta, int losMetros, boolean siTieneSalida) {
        this.codigo = unCodigo;
        this.nombre = nom;
        this.planta = unaPlanta;
        this.metros = losMetros;
        this.tieneSalida = siTieneSalida;
        this.desafios = new ArbolAVL();
    }

    // visualizadores
    public int getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getPlanta() {
        return this.planta;
    }

    public int getMetros() {
        return this.metros;
    }

    public boolean tieneSalida() {
        return this.tieneSalida;
    }

    public ArbolAVL getDesafios() {
        return this.desafios;
    }

    // modificadores
    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public void setPlanta(int unaPlanta) {
        this.planta = unaPlanta;
    }

    public void setMetros(int losMetros) {
        this.metros = losMetros;
    }

    public void setTieneSalida(boolean siTieneSalida) {
        this.tieneSalida = siTieneSalida;
    }

    @Override
    public int compareTo(Habitacion otra) {
        return Integer.compare(this.codigo, otra.codigo);
    }

    @Override
    public boolean equals(Object otraHab) {
        boolean iguales = false;
        if (otraHab != null && otraHab instanceof Habitacion) {
            Habitacion otra = (Habitacion) otraHab;
            if (this.codigo == otra.codigo) {
                iguales = true;
            }
        }
        return iguales;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.codigo);
    }

    public String toString() {
        return "Habitacion [Codigo: " + codigo + ", Nombre: " + nombre + ", Planta: " + planta +
                ", Metros: " + metros + ", Salida Exterior: " + (tieneSalida ? "Sí" : "No") + "]";
    }
    // metodos de desafio

    // inserta un nuevo desafio en el avl
    public boolean altaDesafio(Desafio d) {
        return this.desafios.insertar(d);
    }

    // elimina un desafio del avl
    public boolean bajaDesafio(int puntaje) {
        return this.desafios.eliminar(new Desafio(puntaje));
    }

    // da la informacion de un desafio indicado
    public Desafio buscarDesafio(int puntaje) {
        return (Desafio) this.desafios.recuperar(new Desafio(puntaje));
    }

}