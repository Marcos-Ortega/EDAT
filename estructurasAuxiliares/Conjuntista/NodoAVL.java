package estructurasAuxiliares.Conjuntista;
public class NodoAVL {
    private Comparable elem;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    public NodoAVL(Comparable elem) {
        this.elem = elem;
        altura = 0;
        izquierdo = null;
        derecho = null;
    }

    public Comparable getElemento() {
        return this.elem;
    }

    public int getAltura() {
        return this.altura;
    }

    public NodoAVL getIzquierdo() {
        return this.izquierdo;
    }

    public NodoAVL getDerecho() {
        return this.derecho;
    }

    public void setElemento(Comparable elem) {
        this.elem = elem;
    }

   public void recalcularAltura() {
    int altIzq,altDer;
    if (this.izquierdo != null) {
        altIzq = this.izquierdo.getAltura();
    } else {
        altIzq = -1;
    }
    if (this.derecho != null) {
        altDer = this.derecho.getAltura();
    } else {
        altDer = -1;
    }
    if (altIzq > altDer) {
        this.altura = 1 + altIzq;
    } else {
        this.altura = 1 + altDer;
    }
}

    public void setIzquierdo(NodoAVL n) {
        this.izquierdo = n;
    }

    public void setDerecho(NodoAVL n) {
        this.derecho = n;
    }

    public void setAltura(int nuevaAltura) {
        this.altura = nuevaAltura;
    }

}
