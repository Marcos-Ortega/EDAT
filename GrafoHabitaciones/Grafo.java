package GrafoHabitaciones;
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
            //busco si existe la habitacion a eliminar
            while (aux != null && !encontrado) {
                if (aux.getElem().equals(codigoHabitacion)) {
                    encontrado = true;
                } else {
                    anterior = aux;
                    aux = aux.getSigVert();
                }
            }
            if (encontrado) {
                //si la habitacion existe, elimino todos los nodosAdyacentes que se vinculen con esa habitacion
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
                //despues de elimminar los nodos adyacentes q se vinculan a el, elimino la habitacion
                if (anterior != null) {
                    anterior.setSigVert(aux.getSigVert());
                } else {
                    this.inicio = aux.getSigVert();
                }
            }
        }
        return encontrado;
    }
}
