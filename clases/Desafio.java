package clases;
public class Desafio implements Comparable<Desafio> {
    private int puntaje; // Clave dentro del AVL de la habitación
    private String nombre;
    private String tipo;

    //constructores
    public Desafio(int unPuntaje) {
        this.puntaje = unPuntaje;
    }

    public Desafio(int unPuntaje, String nom, String unTipo) {
        this.puntaje = unPuntaje;
        this.nombre = nom;
        this.tipo = unTipo;
    }

    // visualizadores
    public int getPuntaje() {
        return this.puntaje;
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getTipo() {
        return this.tipo;
    }

    // Modificadores
    public void setNombre(String nom) {
        this.nombre = nom;
    }
    public void setTipo(String unTipo) {
        this.tipo = unTipo;
    }

    // (Compara dos desafíos segun su puntaje)
    public int compareTo(Desafio otro) {
        int comparTo=-1;
        if (this.puntaje> otro.puntaje) {
            comparTo=1;
        }else if(this.puntaje==otro.puntaje){
            comparTo =0;
        }
        return comparTo;
    }

        // compara si dos desafios son iguales
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

    public String toString() {
        return "[Desafio [Puntaje: " + puntaje + ", Nombre: " + nombre + ", Tipo: " + tipo + "] \n";
    }
}