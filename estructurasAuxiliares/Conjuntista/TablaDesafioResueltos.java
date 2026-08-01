package estructurasAuxiliares.Conjuntista;

import clases.Desafio;
import estructurasAuxiliares.Lineales.Lista;

public class TablaDesafioResueltos {
    private static final int TAMANIO_INICIAL = 40;
    private NodoHashResueltos[] tabla;

    public TablaDesafioResueltos() {
        this.tabla = new NodoHashResueltos[TAMANIO_INICIAL];
    }

    private int funcionHash(String clave) {
        int hash = Math.abs(clave.hashCode());
        return hash % TAMANIO_INICIAL;
    }

    public boolean agregar(String nombreEquipo, Desafio desafio) {

        int pos = funcionHash(nombreEquipo);
        NodoHashResueltos aux = tabla[pos];
        boolean encontrado = false;

        while (aux != null && !encontrado) {
            if (aux.getNombreEquipo().equalsIgnoreCase(nombreEquipo)) {
                encontrado = true;
            } else {
                aux = aux.getSiguiente();
            }
        }

        boolean exito = false;

        if (encontrado) {
            // ya existe el equipo en la tabla, reviso si ya tiene ese desafio
            boolean yaEsta = false;
            Lista lista = aux.getDesafiosResueltos();
            for (int i = 1; i <= lista.longitud() && !yaEsta; i++) {
                Desafio d = (Desafio) lista.recuperar(i);
                if (d == desafio) {
                    yaEsta = true;
                }
            }
            if (!yaEsta) {
                lista.insertar(desafio, lista.longitud() + 1);
                exito = true;
            }
        } else {
            // primera vez que este equipo resuelve un desafio
            Lista nuevaLista = new Lista();
            nuevaLista.insertar(desafio, 1);
            tabla[pos] = new NodoHashResueltos(nombreEquipo, nuevaLista, tabla[pos]);
            exito = true;
        }

        return exito;
    }

    public Lista obtenerResueltos(String nombreEquipo) {

        int pos = funcionHash(nombreEquipo);
        NodoHashResueltos aux = tabla[pos];
        Lista resultado = null;

        while (aux != null && resultado == null) {
            if (aux.getNombreEquipo().equalsIgnoreCase(nombreEquipo)) {
                resultado = aux.getDesafiosResueltos();
            } else {
                aux = aux.getSiguiente();
            }
        }

        if (resultado == null) {
            resultado = new Lista();
        }

        return resultado;
    }

    public boolean yaResuelto(String nombreEquipo, Desafio desafio) {

        Lista lista = obtenerResueltos(nombreEquipo);
        boolean encontrado = false;

        for (int i = 1; i <= lista.longitud() && !encontrado; i++) {
            Desafio d = (Desafio) lista.recuperar(i);
            if (d == desafio) {
                encontrado = true;
            }
        }

        return encontrado;
    }

    public String toString() {

        String texto = "--- Desafios Resueltos por Equipo ---\n";

        for (int i = 0; i < TAMANIO_INICIAL; i++) {
            NodoHashResueltos aux = tabla[i];
            while (aux != null) {
                texto += aux.getNombreEquipo() + ": " + aux.getDesafiosResueltos() + "\n";
                aux = aux.getSiguiente();
            }
        }

        return texto;
    }
}