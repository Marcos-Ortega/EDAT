package GrafoHabitaciones.estructurasAuxiliares;

public class nodoAVL {
    private Comparable elem;
    private int altura;
    private nodoAVL izquierdo;
    private nodoAVL derecho;

    public nodoAVL(Comparable unElem) {
        this.elem = unElem;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 0;
    }

    public Comparable getElem() {
        return elem;
    }

    public int getAltura() {
        return altura;
    }

    public nodoAVL getIzquierdo() {
        return izquierdo;
    }

    public nodoAVL getDerecho() {
        return derecho;
    }

    public void setElem(Comparable elem) {
        this.elem = elem;
    }
    
    public void recalcularAltura() {
        int alturaDer = 0;
        int alturaIzq = 0;
        if (this.izquierdo != null) {
            alturaIzq = this.izquierdo.getAltura();
        } else {
            alturaIzq = -1;
        }
        if (this.derecho != null) {
            alturaDer = this.derecho.getAltura();
        } else {
            alturaDer = -1;
        }
        this.altura= 1 + Math.max(alturaIzq, alturaDer);
    }

    public void setIzquierdo(nodoAVL izq) {
        this.izquierdo = izq;
    }

    public void setDerecho(nodoAVL der) {
        this.derecho = der;
    }
    //chequear balance, no lo pide en el tda, pero lo hago aparte
    public int getBalance(){
        int alturaDer = 0;
        int alturaIzq = 0;
        if (this.izquierdo != null) {
            alturaIzq = this.izquierdo.getAltura();
        } else {
            alturaIzq = -1;
        }
        if (this.derecho != null) {
            alturaDer = this.derecho.getAltura();
        } else {
            alturaDer = -1;
        }
        int balance = alturaIzq-alturaDer;
        return balance;
    }
}