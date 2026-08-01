package estructurasAuxiliares.Grafo;

public class NodoVert {
    private Object elem;
    private NodoVert sigVert;
    private NodoAdy primerAdy;

    public NodoVert(Object elemento, NodoVert nodo, NodoAdy nodoAdy) {
        this.elem = elemento;
        this.sigVert = nodo;
        this.primerAdy=nodoAdy;
    }

    public Object getElem() {
        return elem;
    }

    public NodoVert getSigVert() {
        return sigVert;
    }
    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
    public void setElem(Object elemento) {
        this.elem = elemento;
    }

    public void setSigVert(NodoVert nodo) {
        this.sigVert = nodo;
    }
        public void setPrimerAdy(NodoAdy nodoAdy) {
        this.primerAdy = nodoAdy;
    }
}
