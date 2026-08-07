package estructurasAuxiliares.Lineales;

public class Lista {
    private Nodo cabecera;

    // constructor
    public Lista() {
        this.cabecera = null;
    }
    //insertar
    public boolean insertar(Object TipoElemento, int pos) {
        boolean exito = true;
        int longit = longitud();
        if (pos < 1 || pos > longit + 1) {//si pone una posicion negativa o se pasa de la longitud, no lo inserta y exito=false
            exito = false;
        } else {
            if (pos == 1) {//si es la primera posicion lo inserta
                this.cabecera = new Nodo(TipoElemento, this.cabecera);//el nuevo nodo le pega al nodo viejo
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {//busco el elemento que esta antes de esa posicion
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(TipoElemento, aux.getEnlace());//creo el nodo conectandolo al que esta de mi aux donde estoy parado
                aux.setEnlace(nuevo);//inserto el nodo en la posicion que pasaron por parametro
            }
        }
        return exito;

    }
    //eliminar
    public boolean eliminar(int pos) {
        boolean exito = true;
        int longit = longitud();
        if (pos < 1 || pos > longit) {//si pone una posicion negativa o se pasa de la longitud, no lo elimina y exito=false
            exito = false;
        } else {
            if (pos == 1) {//si es la primera posicion cambio la cabecera al siguiente enlace
                this.cabecera = this.cabecera.getEnlace();
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {//busco el elemento que esta antes de esa posicion
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());//seteo el enlace del elemento anterior al que quiero eliminar, al siguiente enlace del elemento a eliminar

            }
        }
        return exito;
    }
    //recuperar elemento
    public Object recuperar(int pos) {
        Nodo aux = this.cabecera;
        boolean bandera = false;
        int cont = 1, longit = longitud();
        Object recuperado = null;
        if ((pos > 0) && (pos <= longit)) {//reviso si la posicion esta dentro de mi rango
            while ((aux != null) && (!bandera)) {
                if (pos == cont) {//si mi contador coincide con la posicion, es porque estoy parado en el elemento que me piden
                    recuperado = aux.getElem();
                    bandera = true;
                } else {//sino, sumo el contador y paso al siguiente enlace
                    cont++;
                    aux = aux.getEnlace();
                }
            }
        }
        return recuperado;
    }
    //localizar
    public int localizar(Object TipoElemento) {
        int posElem = -1, i = 1;
        boolean bandera = false;
        Nodo aux = this.cabecera;
        while (aux != null && !bandera) {
            if (aux.getElem().equals(TipoElemento)) {//comparo si el elemento donde estoy parado es el que me estan pidiendo
                bandera = true;
                posElem = i;//la posicion en donde se encuentra
            } else {//sino, paso al siguiente nodo y sumo el contador de las posiciones
                aux = aux.getEnlace();
                i++;
            }
        }
        return posElem;
    }
    //vaciar
    public void vaciar() {
        this.cabecera = null;
    }
    //esVacia
    public boolean esVacia() {
        return this.cabecera == null;
    }
    //clonar
    public Lista clone() {
        Lista clonLista = new Lista();//creo una lista vacia
        if (this.cabecera != null) {
            Nodo cabeceraAux = this.cabecera;
            Nodo nuevoNodo = new Nodo(cabeceraAux.getElem(), null);//creo un nuevo nodo con el elemento de la lista original.
            clonLista.cabecera = nuevoNodo;//inserto el nodo en la cabecera de la lista clon
            cabeceraAux = cabeceraAux.getEnlace();
            Nodo cabeceraAuxClone = nuevoNodo;//creo un auxClon para insertar en la lista clon
            while (cabeceraAux != null) {
                nuevoNodo = new Nodo(cabeceraAux.getElem(), null);//nuevo nodo con el elemento de la lista original.
                cabeceraAuxClone.setEnlace(nuevoNodo);//seteo el enlace del auxClon con el nuevo nodo creado
                cabeceraAuxClone = nuevoNodo;//me paro en el nuevo nodo creado
                cabeceraAux = cabeceraAux.getEnlace();//avanzo al siguiente enlace de la lista original.
            }
        }
        return clonLista;
    }
    //longitud
    public int longitud() {
        int cont = 0;
        if (this.cabecera != null) {
            Nodo cabeceraAux = this.cabecera;
            cont=1;
            while (cabeceraAux.getEnlace() != null) {//mientras hayan elementos en la lista, los voy contando
                cont++;
                cabeceraAux = cabeceraAux.getEnlace();//avanzo al siguiente enlace
            }
        }
        return cont;
    }
    //toString
    public String toString() {
        String mostrar= "";
        Nodo aux = this.cabecera;

        while (aux != null) {
            mostrar += aux.getElem();
            aux = aux.getEnlace();

        }

        return mostrar;
    }
}
