package clases;

import GrafoHabitaciones.estructurasAuxiliares.Lista;

public class TablaDesafioResueltos {
    private static final int TAMANIO_INICIAL = 31;
    private NodoHashResueltos[] tabla;

    public TablaDesafioResueltos() {
        this.tabla = new NodoHashResueltos[TAMANIO_INICIAL];
    }

    private int funcionHash(String clave) {
        int hash = Math.abs(clave.hashCode());
        return hash % TAMANIO_INICIAL;
    }

    // se llama cuando un equipo resuelve un desafío
    public boolean registrar(String nombreEquipo, int codHabitacion, int puntaje) {
        int pos = funcionHash(nombreEquipo);
        NodoHashResueltos aux = tabla[pos];
        boolean encontrado = false;

        // buscamos si el equipo ya tiene una lista cargada
        while (aux != null && !encontrado) {
            if (aux.getNombreEquipo().equalsIgnoreCase(nombreEquipo)) {
                encontrado = true;
            } else {
                aux = aux.getSiguiente();
            }
        }

        DesafioResuelto nuevo = new DesafioResuelto(codHabitacion, puntaje);
        boolean exito = false;

        if (encontrado) {
            // ya existe la entrada del equipo, evitamos duplicados
            if (aux.getDesafiosResueltos().localizar(nuevo) < 0) {
                aux.getDesafiosResueltos().insertar(nuevo, aux.getDesafiosResueltos().longitud() + 1);
                exito = true;
            }
        } else {
            // primera vez que este equipo resuelve algo, creamos su entrada
            Lista nuevaLista = new Lista();
            nuevaLista.insertar(nuevo, 1);
            tabla[pos] = new NodoHashResueltos(nombreEquipo, nuevaLista, tabla[pos]);
            exito = true;
        }
        return exito;
    }

    // para mostrarDesafiosResueltos(equipo)
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
        return (resultado != null) ? resultado : new Lista();
    }

    // para verificarDesafioResuelto(equipo, desafio, habitacion)
    public boolean yaResuelto(String nombreEquipo, int codHabitacion, int puntaje) {
        Lista lista = obtenerResueltos(nombreEquipo);
        return lista.localizar(new DesafioResuelto(codHabitacion, puntaje)) > 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Desafios Resueltos por Equipo ---\n");
        for (int i = 0; i < TAMANIO_INICIAL; i++) {
            NodoHashResueltos aux = tabla[i];
            while (aux != null) {
                sb.append(aux.getNombreEquipo()).append(": ").append(aux.getDesafiosResueltos()).append("\n");
                aux = aux.getSiguiente();
            }
        }
        return sb.toString();
    }
}