package clases;

public class DesafioResuelto {
    private int codigoHabitacion;
    private int puntajeDesafio; // clave del desafío dentro de esa habitación

    public DesafioResuelto(int codigoHabitacion, int puntajeDesafio) {
        this.codigoHabitacion = codigoHabitacion;
        this.puntajeDesafio = puntajeDesafio;
    }

    public int getCodigoHabitacion() {
        return codigoHabitacion;
    }

    public int getPuntajeDesafio() {
        return puntajeDesafio;
    }

    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (obj != null && obj instanceof DesafioResuelto) {
            DesafioResuelto otro = (DesafioResuelto) obj;
            iguales = this.codigoHabitacion == otro.codigoHabitacion
                    && this.puntajeDesafio == otro.puntajeDesafio;
        }
        return iguales;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigoHabitacion) * 31 + Integer.hashCode(puntajeDesafio);
    }

    @Override
    public String toString() {
        return "(Hab " + codigoHabitacion + ": " + puntajeDesafio + " pts)";
    }
}