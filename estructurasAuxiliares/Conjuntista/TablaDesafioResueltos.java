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
        // buscamos si existe el equipo dentro de la tabla ya creada
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
                // verifico si ya resolvio ese desafio
                Desafio d = (Desafio) lista.recuperar(i);
                if (d == desafio) {
                    yaEsta = true;
                }
            }
            // si no lo reslvio antes, le inserto el desafio (se lo marco como resuelto)
            if (!yaEsta) {
                lista.insertar(desafio, lista.longitud() + 1);
                exito = true;
            }
        } else {
            // primera vez que este equipo resuelve un desafio, insertamos al equipo en la
            // tabla y le marcamos el desafion como resuelto
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
        boolean encontrado = false;
        Lista resultado = new Lista();

        while (aux != null && !encontrado) {
            //si encuentro el equipo en la tabla dejo de recorrer y a resultado le asigno todos los desafios que resolvio ese equipo
            if (aux.getNombreEquipo().equalsIgnoreCase(nombreEquipo)) {
                resultado = aux.getDesafiosResueltos();
                encontrado = true;
            } else {
                //avanzo la siguiente
                aux = aux.getSiguiente();
            }
        }

        return resultado;
    }

    public boolean yaResuelto(String nombreEquipo, Desafio desafio) {

        Lista lista = obtenerResueltos(nombreEquipo);
        boolean encontrado = false;
        int i = 1;
        //recorremos la lista de desafios que reoslvio el equipo y nos fijamos si en ella esta el desafio indicado
        while ((!encontrado) && (i <= lista.longitud())) {
            Desafio d = (Desafio) lista.recuperar(i);
            if (d == desafio) {
                encontrado = true;
            }
            i++;
        }

        return encontrado;
    }

    @Override
    public String toString() {
        String texto = "--- Desafios Resueltos por Equipo ---\n";

        for (int i = 0; i < TAMANIO_INICIAL; i++) {
            NodoHashResueltos aux = tabla[i];
            while (aux != null) {
                texto = texto + "\nEquipo: " + aux.getNombreEquipo() + "\n";

                Lista lista = aux.getDesafiosResueltos();
                if (lista.esVacia()) {
                    texto = texto + "   (sin desafios resueltos)\n";
                } else {
                    for (int j = 1; j <= lista.longitud(); j++) {
                        Desafio d = (Desafio) lista.recuperar(j);
                        texto = texto + "   - [" + d.getPuntaje() + " pts] " + d.getNombre()
                                + " (" + d.getTipo() + ")\n";
                    }
                }

                aux = aux.getSiguiente();
            }
        }

        return texto;
    }
}