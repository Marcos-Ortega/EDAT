package clases;
import GrafoHabitaciones.estructurasAuxiliares.*;
public class Habitacion implements Comparable<Habitacion> {
    private int codigo;
    private String nombre;
    private int planta;
    private int metros;
    private boolean tieneSalida;
    private ArbolAVL desafios; // cada habitacion conoce sus desafios 

    public Habitacion(int codigo) {
        this.codigo = codigo;
    }

    public Habitacion(int codigo, String nombre, int planta, int metros, boolean tieneSalida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metros = metros;
        this.tieneSalida = tieneSalida;
        this.desafios = new ArbolAVL(); 
    }

    // Métodos observadores (Getters)
    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getPlanta() { return planta; }
    public int getMetros() { return metros; }
    public boolean tieneSalida() { return tieneSalida; }
    public ArbolAVL getDesafios() { return desafios; }

    // Métodos modificadores (Setters)
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPlanta(int planta) { this.planta = planta; }
    public void setMetros(int metros) { this.metros = metros; }
    public void setTieneSalida(boolean tieneSalida) { this.tieneSalida = tieneSalida; }

    @Override
    public int compareTo(Habitacion otra) { 
        return Integer.compare(this.codigo, otra.codigo);
    }

    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (obj != null && obj instanceof Habitacion) { 
            Habitacion otro = (Habitacion) obj; 
            if (this.codigo == otro.codigo) {
                iguales = true;
            }
        }
        return iguales;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.codigo);
    }

    @Override
    public String toString() {
        return "Habitacion [Codigo: " + codigo + ", Nombre: " + nombre + ", Planta: " + planta +
                ", Metros: " + metros + ", Salida Exterior: " + (tieneSalida ? "Sí" : "No") + "]";
    }
}