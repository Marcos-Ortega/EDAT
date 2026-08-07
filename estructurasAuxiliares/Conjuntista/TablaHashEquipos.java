package estructurasAuxiliares.Conjuntista;

import clases.Equipo;
import clases.Habitacion;

public class TablaHashEquipos {
    private static final int TAMANIO_INICIAL = 40;
    private NodoHash[] tabla;

    public TablaHashEquipos() {
        this.tabla = new NodoHash[TAMANIO_INICIAL];
    }

    // Función de dispersión (Hash) basada en la clave (Nombre del equipo)
    // java toma la cadena de texto y calcula un número entero a partir de las
    // letras de la clave
    private int funcionHash(String clave) {
        int hash = Math.abs(clave.toLowerCase().hashCode());
        return hash % TAMANIO_INICIAL;
    }

    public boolean insertar(Equipo equipo) {
        boolean insertado = false;

        // calculamos la posicion (indice) del arreglo con la funcion Hash
        int pos = funcionHash(equipo.getNombre());

        // apuntamos al primer nodo de la lista en la posición 'pos'
        NodoHash aux = tabla[pos];
        boolean existe = false;

        // recorremos la lista para verificar que el equipo no exista previamente
        while (aux != null && !existe) {
            if (aux.getEquipo().getNombre().equalsIgnoreCase(equipo.getNombre())) {
                existe = true; // si se encontró un duplicado, frenamos el recorrido
            } else {
                aux = aux.getSiguiente(); // avanzamos al siguiente nodo
            }
        }

        // si no existe un equipo con ese nombre, lo insertamos al principio de la lista
        if (!existe) {
            tabla[pos] = new NodoHash(equipo, tabla[pos]);
            insertado = true;
        }
        return insertado;
    }

    public Equipo buscar(String nombreEquipo) {
        // variable para almacenar el equipo encontrado (inicia en null por si no
        // existe)
        Equipo equipoEncontrado = null;

        // calculamos la posición (indice) del arreglo con la funcion Hash
        int pos = funcionHash(nombreEquipo);

        // apuntamos al primer nodo de la lista encadenada en la posición 'pos'
        NodoHash aux = tabla[pos];

        // recorremos la lista buscando la coincidencia por nombre
        // el bucle frena si llegamos al final de la lista (null) o si ya encontramos el
        // equipo
        while (aux != null && equipoEncontrado == null) {
            // comparamos el nombre ingresado con el del equipo guardado en el nodo actual
            if (aux.getEquipo().getNombre().equalsIgnoreCase(nombreEquipo)) {
                equipoEncontrado = aux.getEquipo(); // guardamos el equipo encontrado para detener el bucle
            } else {
                aux = aux.getSiguiente(); // avanzamos al siguiente nodo de la lista
            }
        }

        return equipoEncontrado;
    }

    public boolean eliminar(String nombreEquipo) {
        boolean eliminado = false;

        // calculamos la posicion (indice) del arreglo con la funcion Hash
        int pos = funcionHash(nombreEquipo);

        // apuntamos al primer nodo de la lista encadenada
        NodoHash aux = tabla[pos];
        NodoHash ant = null;

        // recorremos la lista buscando el equipo a eliminar
        while (aux != null && !eliminado) {
            if (aux.getEquipo().getNombre().equalsIgnoreCase(nombreEquipo)) {

                // caso 1: el elemento a eliminar es el primero de la lista
                if (ant == null) {
                    tabla[pos] = aux.getSiguiente();
                } else {
                    // caso 2: el elemento a eliminar esta en el medio o al final
                    ant.setSiguiente(aux.getSiguiente());
                }

                eliminado = true; // marcamos la eliminacion para frenar el bucle
            } else {
                ant = aux; // guardamos el nodo actual como anterior
                aux = aux.getSiguiente(); // avanzamos al siguiente nodo
            }
        }

        return eliminado;
    }

    public boolean existeEquipoEnHabitacion(int codigoHabitacion) {
    boolean encontrado = false;
        int i = 0;
    while ( i < tabla.length && !encontrado) {
        NodoHash aux = tabla[i];
        while (aux != null && !encontrado) {
            Habitacion hab = aux.getEquipo().getHabitacionActual();
            if (hab != null && hab.getCodigo() == codigoHabitacion) {
                encontrado = true;
            }
            aux = aux.getSiguiente();
        }
        i++;
    }

    return encontrado;
}
public String toString() {

    String texto = "---Equipos Registrados--- \n";

    // recorremos todas las posiciones del arreglo (toda la tabla hash)
    for (int i = 0; i < TAMANIO_INICIAL; i++) {
        NodoHash aux = tabla[i];

        //recorremos la lista encadenada de equipos
        while (aux != null) {
            texto += aux.getEquipo() + "\n";
            aux = aux.getSiguiente();
        }
    }

    return texto;
}
}