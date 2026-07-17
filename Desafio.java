
public class Desafio {
    private int puntaje;
    private String nombre;
    private String tipo;

    public Desafio(int puntaje) {
        this.puntaje=puntaje;
    }

    public Desafio(int puntaje, String nom, String tipo) {
        this.puntaje = puntaje;
        this.nombre = nom;
        this.tipo = tipo;
    }

    // Métodos observadores (Getters)
    public int getPuntaje() {
        return puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    // Métodos modificadores (Setters)
    // Nota: El puntaje es la clave por eso no pongo su setter
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
