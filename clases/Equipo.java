package clases;
public class Equipo {
    private String nombre;
    private int puntajeExigido;
    private int puntajeAcumulado;
    private Habitacion habitacionActual;
    private int puntajeActualHab;

    public Equipo(String nombre) {
        this.nombre= nombre;
    }

    public Equipo(String nombre, int puntajeE, int puntajeA, Habitacion habActual, int puntajeT) {
        this.nombre = nombre;
        this.puntajeExigido = puntajeE;
        this.puntajeAcumulado = puntajeA;
        this.habitacionActual = habActual;
        this.puntajeActualHab = puntajeT;
    }

    // Métodos observadores (Getters)
    public String getNombre() {
        return nombre;
    }

    public int getPuntajeExigido() {
        return puntajeExigido;
    }

    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    public Habitacion getHabitacionActual() {
        return habitacionActual;
    }

    public int getPuntajeActualHab() {
        return puntajeActualHab;
    }

    // Métodos modificadores (Setters)
    // Nota: No incluimos setter para 'nombre' porque las claves no deben
    // modificarse
    public void setPuntajeExigido(int puntajeExigido) {
        this.puntajeExigido = puntajeExigido;
    }

    public void setPuntajeAcumulado(int puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
    }

    public void setHabitacionActual(Habitacion habitacionActual) {
        this.habitacionActual = habitacionActual;
    }

    public void setPuntajeActualHab(int puntajeActual) {
        this.puntajeActualHab = puntajeActual;
    }

    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (obj != null && obj instanceof Equipo) { // instanceof verifica si el objeto pertenece a la clase equipo
            Equipo otro = (Equipo) obj;
            if (this.nombre.equals(otro.nombre)) {
                iguales = true;
            }
        }
        return iguales;
    }

    @Override
    public int hashCode() {
        int codigo = 0;
        if (nombre != null) {
            codigo = nombre.hashCode();
        }
        return codigo;
    }

    @Override
    public String toString() {
        return "Equipo: " + nombre + " | Exigido: " + puntajeExigido +
                " | Acumulado: " + puntajeAcumulado +
                " | Hab. Actual: " + (habitacionActual != null ? habitacionActual.getCodigo() : "Ninguna") +
                " | Puntaje en Hab: " + puntajeActualHab;
    }
}
