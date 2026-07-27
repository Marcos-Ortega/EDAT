package clases;


public class NodoHash {
    private Equipo equipo;
    private NodoHash siguiente;

    public NodoHash(Equipo equipo, NodoHash siguiente) {
        this.equipo = equipo;
        this.siguiente = siguiente;
    }

    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }
    public NodoHash getSiguiente() { return siguiente; }
    public void setSiguiente(NodoHash siguiente) { this.siguiente = siguiente; }
}