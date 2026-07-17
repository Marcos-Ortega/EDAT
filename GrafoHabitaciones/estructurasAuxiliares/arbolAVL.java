package GrafoHabitaciones.estructurasAuxiliares;
public class arbolAVL {
    private nodoAVL raiz;

    public arbolAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new nodoAVL(elem);
            exito = true;
        } else {
            nodoAVL nuevo;
            nuevo = insertarAux(this.raiz, elem);
            if (nuevo != null) {
                exito = true;
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private nodoAVL insertarAux(nodoAVL n, Comparable elem) {
        boolean exito = true;
        nodoAVL nuevo = null;
        // elemetno repetido
        if (n != null) {
            if (elem.compareTo(n.getElem()) == 0) {
                exito = false;
            } else if (elem.compareTo(n.getElem()) < 0) { // elemento que hay que ingresar es menor al elemento que
                                                          // existe en el arbol
                n.setIzquierdo(insertarAux(n.getIzquierdo(), elem));
            } else {// elemento que hay que ingresar es mayor al elemento que existe en el arbol
                n.setDerecho(insertarAux(n.getDerecho(), elem));
            }
        } else {
            nuevo = new nodoAVL(elem);
        }
        if (exito) {
            n.recalcularAltura();
            int balance = n.getBalance();
            if (balance > 1 && n.getIzquierdo().getBalance() >= 0) {
                n = rotacionSimpleDerecha(n);
            }
            if (balance < -1 && n.getDerecho().getBalance() <= 0) {
                n = rotacionSimpleIzquierda(n);
            }
            if (balance > 1 && n.getIzquierdo().getBalance() < 0) {
                n.setIzquierdo(rotacionSimpleIzquierda(n.getIzquierdo()));
                n = rotacionSimpleDerecha(n);
            }
            if (balance > 1 && n.getDerecho().getBalance() > 0) {
                n.setIzquierdo(rotacionSimpleDerecha(n.getIzquierdo()));
                n = rotacionSimpleIzquierda(n);
            }
        }
        return nuevo;
    }

    private nodoAVL rotacionSimpleDerecha(nodoAVL r) {
        nodoAVL h = r.getIzquierdo();
        nodoAVL temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private nodoAVL rotacionSimpleIzquierda(nodoAVL r) {
        nodoAVL h = r.getDerecho();
        nodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
}