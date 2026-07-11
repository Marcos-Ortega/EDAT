package GrafoHabitaciones.estructurasAuxiliares;

public class Lista {
    private Nodo cabecera;

    // constructor
    public Lista() {
        this.cabecera = null;
    }

    public boolean insertar(Object TipoElemento, int pos) {
        boolean exito = true;
        int longit = longitud();
        if (pos < 1 || pos > longit + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(TipoElemento, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(TipoElemento, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        return exito;

    }

    public boolean eliminar(int pos) {
        boolean exito = true;
        int longit = longitud();
        if (pos < 1 || pos > longit) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());

            }
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Nodo aux = this.cabecera;
        boolean bandera = false;
        int cont = 1, longit = longitud();
        Object recuperado = null;
        if ((pos > 0) && (pos <= longit)) {
            while ((aux != null) && (!bandera)) {
                if (pos == cont) {
                    recuperado = aux.getElem();
                    bandera = true;
                } else {
                    cont++;
                    aux = aux.getEnlace();
                }
            }
        }
        return recuperado;
    }

    public int localizar(Object TipoElemento) {
        int posElem = -1, i = 1;
        boolean bandera = false;
        Nodo aux = this.cabecera;
        while (aux != null && !bandera) {
            if (aux.getElem().equals(TipoElemento)) {
                bandera = true;
                posElem = i;
            } else {
                aux = aux.getEnlace();
                i++;
            }
        }
        return posElem;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public Lista clone() {
        Lista clonLista = new Lista();
        if (this.cabecera != null) {
            Nodo cabeceraAux = this.cabecera;
            Nodo nuevoNodo = new Nodo(cabeceraAux.getElem(), null);
            clonLista.cabecera = nuevoNodo;
            cabeceraAux = cabeceraAux.getEnlace();
            Nodo cabeceraAuxClone = nuevoNodo;
            while (cabeceraAux != null) {
                nuevoNodo = new Nodo(cabeceraAux.getElem(), null);
                cabeceraAuxClone.setEnlace(nuevoNodo);
                cabeceraAuxClone = nuevoNodo;
                cabeceraAux = cabeceraAux.getEnlace();
            }
        }
        return clonLista;
    }

    public int longitud() {
        int cont = 0;
        if (this.cabecera != null) {
            Nodo cabeceraAux = this.cabecera;
            cont=1;
            while (cabeceraAux.getEnlace() != null) {
                cont++;
                cabeceraAux = cabeceraAux.getEnlace();
            }
        }
        return cont;
    }

    public String toString() {
        String mostrar = "[";
        Nodo aux = this.cabecera;

        while (aux != null) {
            mostrar += aux.getElem();
            aux = aux.getEnlace();

            if (aux != null) {
                mostrar += ",";
            }
        }

        mostrar += "]";
        return mostrar;
    }
}
