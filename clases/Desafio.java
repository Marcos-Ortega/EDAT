package clases;
public class Desafio implements Comparable<Desafio> {
    private int puntaje; // Clave dentro del AVL de la habitación
    private String nombre;
    private String tipo;

    public Desafio(int puntaje) {
        this.puntaje = puntaje;
    }

    public Desafio(int puntaje, String nom, String tipo) {
        this.puntaje = puntaje;
        this.nombre = nom;
        this.tipo = tipo;
    }

    // Métodos observadores (Getters)
    public int getPuntaje() { return puntaje; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }

    // Métodos modificadores (Setters)
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // (Compara los desafíos por su puntaje)
    @Override
    public int compareTo(Desafio otro) {
        return Integer.compare(this.puntaje, otro.puntaje);
    }

    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (obj != null && obj instanceof Desafio) {
            Desafio otro = (Desafio) obj;
            if (this.puntaje == otro.puntaje) {
                iguales = true;
            }
        }
        return iguales;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.puntaje);
    }

    @Override
    public String toString() {
        return "Desafio [Puntaje: " + puntaje + ", Nombre: " + nombre + ", Tipo: " + tipo + "]";
    }
}