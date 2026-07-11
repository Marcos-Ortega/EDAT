import GrafoHabitaciones.estructurasAuxiliares.Lista;

public class Grafo {
    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    public boolean insertarHabitacion(Object codigoHabitacion) {
        boolean exito = false;
        NodoVert nuevo;
        if (this.inicio == null) {
            nuevo = new NodoVert(codigoHabitacion, null, null);
            this.inicio = nuevo;
        } else {
            NodoVert aux = this.inicio;
            NodoVert anterior = null;
            while (aux != null && !exito) {
                if (aux.getElem().equals(codigoHabitacion)) {
                    exito = true;
                } else {
                    anterior = aux;
                    aux = aux.getSigVert();
                }
            }
            if (!exito) {
                nuevo = new NodoVert(codigoHabitacion, null, null);
                anterior.setSigVert(nuevo);
            }
        }
        return !exito;
    }

    public boolean eliminarHabitacion(Object codigoHabitacion) {
        boolean encontrado = false;
        NodoVert aux = this.inicio;
        NodoVert anterior = null;
        if (this.inicio != null) {
            // busco si existe la habitacion a eliminar
            while (aux != null && !encontrado) {
                if (aux.getElem().equals(codigoHabitacion)) {
                    encontrado = true;
                } else {
                    anterior = aux;
                    aux = aux.getSigVert();
                }
            }
            if (encontrado) {
                // si la habitacion existe, elimino todos los nodosAdyacentes que se vinculen
                // con esa habitacion
                NodoVert auxVert = this.inicio;
                NodoAdy auxAdy;
                NodoAdy anteriorAdy;
                NodoAdy siguiente;
                while (auxVert != null) {

                    auxAdy = auxVert.getPrimerAdy();
                    anteriorAdy = null;

                    while (auxAdy != null) {
                        siguiente = auxAdy.getSigAdyacente();
                        if (auxAdy.getVertice().getElem().equals(codigoHabitacion)) {
                            if (anteriorAdy == null) {
                                auxVert.setPrimerAdy(siguiente);
                            } else {
                                anteriorAdy.setSigAdyacente(siguiente);
                            }
                        } else {
                            anteriorAdy = auxAdy;
                        }
                        auxAdy = siguiente;
                    }
                    auxVert = auxVert.getSigVert();
                }
                // despues de elimminar los nodos adyacentes q se vinculan a el, elimino la
                // habitacion
                if (anterior != null) {
                    anterior.setSigVert(aux.getSigVert());
                } else {
                    this.inicio = aux.getSigVert();
                }
            }
        }
        return encontrado;
    }

    public boolean existeHabitacion(Object codigoHabitacion) {
        boolean exito = false;
        NodoVert aux = this.inicio;
        if (aux != null) {
            // va a recorrer hasta que encuentre la habitacion o ya no queden mas
            // habitaciones
            while (aux != null && !exito) {
                if (aux.getElem().equals(codigoHabitacion)) {
                    exito = true;
                } else {
                    aux = aux.getSigVert();
                }
            }
        }
        return exito;
    }

    public boolean existeCamino(Object codHab1, Object codHab2) {
        boolean existe = false, valido = false;
        NodoVert hab1 = buscarVertice(codHab1);
        NodoVert hab2 = buscarVertice(codHab2);
        if (this.inicio != null && hab1 != null && hab2 != null) {
            // creamos una lista para recorrer los caminos efectivamente
            Lista visitados = new Lista();
            existe = profundidadDesde(hab1, hab2, visitados);

        }
        return existe;
    }

    private boolean profundidadDesde(NodoVert hab1, NodoVert hab2, Lista visitados) {
        boolean existe = false;
        // verifica que la habitacion en la que estamos parados es la misma que la
        // segunda ingresada por parametro
        if (hab1.getElem().equals(hab2.getElem())) {
            existe = true;
        } else {
            // utilizamos la lista para que no se armen bucles y solo pasemos una vez por
            // cada camino
            visitados.insertar(hab1.getElem(), visitados.longitud() + 1);
            // avanzamos por todos los caminos que hayan desde la habitacion 1
            NodoAdy aux2 = hab1.getPrimerAdy();
            while ((aux2 != null) && (!existe)) {
                // si la habitacion no se encuntra en la lista (es decir que no analizamos
                // caminos todavia) vuelve a llamar al metodo
                if (visitados.localizar(aux2.getVertice().getElem()) < 0) {
                    existe = profundidadDesde(aux2.getVertice(), hab2, visitados);
                }
                aux2 = aux2.getSigAdyacente();

            }
        }
        return existe;
    }

    // vemos si hay alguna habitacion
    public boolean esVacio() {
        return this.inicio == null;
    }

    // eliminamos todas las habitaciones
    public void vaciar() {
        this.inicio = null;
    }

    // como en el grafo van a recibir el codigo de la habitacion y no el vertice en si, hay que buscarlos
    private NodoVert buscarVertice(Object codHab) {
        NodoVert vertice = null;
        boolean encontrado = false;
        if (this.inicio != null) {
            NodoVert aux = this.inicio;
            while (aux != null && !encontrado) {
                if (aux.getElem().equals(codHab)) {
                    encontrado = true;
                    vertice = aux;
                } else {
                    aux = aux.getSigVert();
                }
            }
        }
        return vertice;
    }
}
