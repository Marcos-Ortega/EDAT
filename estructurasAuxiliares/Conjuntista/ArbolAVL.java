package estructurasAuxiliares.Conjuntista;

import clases.Habitacion;
import estructurasAuxiliares.Lineales.Lista;

public class ArbolAVL {
    private NodoAVL raiz;

    public boolean insertar(Comparable elem) {
        boolean[] exito = { true };
        this.raiz = insertarAux(this.raiz, elem, exito);
        return exito[0];
    }

    private NodoAVL insertarAux(NodoAVL n, Comparable elem, boolean[] exito) {
        if (n == null) {
            // si es nulo crea el nodo
            n = new NodoAVL(elem);
        } else {
            int comparacion = elem.compareTo(n.getElemento());

            if (comparacion == 0) {
                // elemento duplicado
                exito[0] = false;
            } else {
                if (comparacion < 0) {
                    // inserta a la izquierda
                    n.setIzquierdo(insertarAux(n.getIzquierdo(), elem, exito));
                } else {
                    // inserta a la derecha
                    n.setDerecho(insertarAux(n.getDerecho(), elem, exito));
                }

                if (exito[0]) {
                    // recalcula altura y balancea
                    n.recalcularAltura();
                    n = balancear(n);
                }
            }
        }
        return n;
    }

    public boolean eliminar(Comparable elemento) {
        boolean[] exito = { false };
        this.raiz = eliminarAux(this.raiz, elemento, exito);
        return exito[0];
    }

    private NodoAVL eliminarAux(NodoAVL n, Comparable elem, boolean[] exito) {
        if (n != null) {
            int comparacion = elem.compareTo(n.getElemento());
            if (comparacion < 0) {
                n.setIzquierdo(eliminarAux(n.getIzquierdo(), elem, exito));
            } else if (comparacion > 0) {
                n.setDerecho(eliminarAux(n.getDerecho(), elem, exito));
            } else {
                exito[0] = true;
                // caso hoja
                if (n.getIzquierdo() == null && n.getDerecho() == null) {
                    n = null;
                    // caso un solo hijo derecho
                } else if (n.getIzquierdo() == null) {
                    n = n.getDerecho();
                    // caso un solo hijo izquierdo
                } else if (n.getDerecho() == null) {
                    n = n.getIzquierdo();
                    // caso dos hijos
                } else {
                    Comparable sucesor = obtenerMinimo(n.getDerecho());
                    n.setElemento(sucesor);
                    boolean[] exitoAux = { false };
                    n.setDerecho(eliminarAux(n.getDerecho(), sucesor, exitoAux));
                }
            }
            if (n != null && exito[0]) {
                // recalcula altura y balancea al volver de la recursion
                n.recalcularAltura();
                n = balancear(n);
            }
        }
        return n;
    }

    // busca el menor elemento del subarbol
    private Comparable obtenerMinimo(NodoAVL n) {
        while (n.getIzquierdo() != null) {
            n = n.getIzquierdo();
        }
        return n.getElemento();
    }

    // calcula el balance del nodo actual
    public int getBalance(NodoAVL n) {
        int balance = 0;
        if (n != null) {
            int altIzq;
            if (n.getIzquierdo() != null) {
                altIzq = n.getIzquierdo().getAltura();
            } else {
                altIzq = -1;
            }

            int altDer;
            if (n.getDerecho() != null) {
                altDer = n.getDerecho().getAltura();
            } else {
                altDer = -1;
            }

            balance = altIzq - altDer;
        }
        return balance;
    }

    // rotacion simple a la derecha
    public NodoAVL rotarDerecha(NodoAVL n) {
        NodoAVL h = n.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        n.setIzquierdo(temp);
        h.setDerecho(n);
        n.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    // rotacion simple a la izquierda
    public NodoAVL rotarIzquierda(NodoAVL n) {
        NodoAVL h = n.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        n.setDerecho(temp);
        h.setIzquierdo(n);
        n.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    // aplica rotaciones si el nodo esta desbalanceado
    private NodoAVL balancear(NodoAVL n) {
        NodoAVL resultado = n;

        if (n != null) {
            int balance = getBalance(n);

            // desbalance a la izquierda
            if (balance > 1) {
                if (getBalance(n.getIzquierdo()) >= 0) {
                    resultado = rotarDerecha(n);
                } else {
                    n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
                    resultado = rotarDerecha(n);
                }
                // desbalance a la derecha
            } else if (balance < -1) {
                if (getBalance(n.getDerecho()) <= 0) {
                    resultado = rotarIzquierda(n);
                } else {
                    n.setDerecho(rotarDerecha(n.getDerecho()));
                    resultado = rotarIzquierda(n);
                }
            }
        }
        return resultado;
    }

    public boolean pertenece(Comparable elem) {
        return perteneceAux(elem, this.raiz);
    }

    private boolean perteneceAux(Comparable elem, NodoAVL n) {
        boolean pertenece = false;
        if (n != null) {
            int comparacion = elem.compareTo(n.getElemento());

            if (comparacion == 0) {
                pertenece = true;
            } else if (comparacion < 0) {
                pertenece = perteneceAux(elem, n.getIzquierdo());
            } else {
                pertenece = perteneceAux(elem, n.getDerecho());
            }
        }
        return pertenece;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    // devuelve el menor elemento del arbol
    public Comparable minimo() {
        Comparable resultado = null;
        if (this.raiz != null) {
            NodoAVL n = this.raiz;
            while (n.getIzquierdo() != null) {
                n = n.getIzquierdo();
            }
            resultado = n.getElemento();
        }
        return resultado;
    }

    // devuelve el mayor elemento del arbol
    public Comparable maximo() {
        Comparable resultado = null;
        if (this.raiz != null) {
            NodoAVL n = this.raiz;
            while (n.getDerecho() != null) {
                n = n.getDerecho();
            }
            resultado = n.getElemento();
        }
        return resultado;
    }

    // devuelve una lista con todos los elementos ordenados
    public Lista listar() {
        Lista unaLista = new Lista();
        listarAux(this.raiz, unaLista);
        return unaLista;
    }

    private void listarAux(NodoAVL n, Lista lis) {
        if (n != null) {
            listarAux(n.getIzquierdo(), lis);
            lis.insertar(n.getElemento(), lis.longitud() + 1);
            listarAux(n.getDerecho(), lis);
        }
    }

    // devuelve una lista con elementos dentro del rango indicado
    public Lista listarRango(Comparable min, Comparable max) {
        Lista unaLista = new Lista();
        listarRangoAux(this.raiz, min, max, unaLista);
        return unaLista;
    }

    private void listarRangoAux(NodoAVL n, Comparable min, Comparable max, Lista lis) {
        if (n != null) {
            int compMin = n.getElemento().compareTo(min);
            int compMax = n.getElemento().compareTo(max);
            if (compMin > 0) {
                listarRangoAux(n.getIzquierdo(), min, max, lis);
            }
            if (compMin >= 0 && compMax <= 0) {
                lis.insertar(n.getElemento(), lis.longitud() + 1);
            }
            if (compMax < 0) {
                listarRangoAux(n.getDerecho(), min, max, lis);
            }
        }
    }

    public ArbolAVL clone() {
        ArbolAVL clon = new ArbolAVL();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoAVL cloneAux(NodoAVL n) {
        NodoAVL nuevoNodo = null;
        if (n != null) {
            nuevoNodo = new NodoAVL(n.getElemento());
            nuevoNodo.setAltura(n.getAltura());
            nuevoNodo.setIzquierdo(cloneAux(n.getIzquierdo()));
            nuevoNodo.setDerecho(cloneAux(n.getDerecho()));
        }
        return nuevoNodo;
    }

    public Comparable recuperar(Comparable elem) {
        return recuperarAux(this.raiz, elem);
    }

    private Comparable recuperarAux(NodoAVL n, Comparable elem) {
        Comparable resultado = null;

        if (n != null) {
            int comparacion = elem.compareTo(n.getElemento());

            if (comparacion == 0) {
                resultado = n.getElemento();
            } else if (comparacion < 0) {
                resultado = recuperarAux(n.getIzquierdo(), elem);
            } else {
                resultado = recuperarAux(n.getDerecho(), elem);
            }
        }

        return resultado;
    }

    // version visual para ver la forma real del arbol
    public String toStringBonito() {
        String res;
        if (this.raiz == null) {
            res = "arbol avl vacio";
        } else {
            res = toStringBonitoAux(this.raiz, "", true);
        }
        return res;
    }

    private String toStringBonitoAux(NodoAVL n, String prefijo, boolean esIzquierdo) {
        String s = "";
        if (n != null) {
            s = s + toStringBonitoAux(n.getDerecho(), prefijo + (esIzquierdo ? "|   " : "    "), false);
            s = s + prefijo + "+-- " + ((Habitacion) n.getElemento()).toStringCorto() + "\n";
            s = s + toStringBonitoAux(n.getIzquierdo(), prefijo + (esIzquierdo ? "    " : "|   "), true);
        }
        return s;
    }

    // @Override
    public String toString() {
        String res;
        if (this.raiz == null) {
            res = "arbol avl vacio";
        } else {
            res = toStringAux(this.raiz);
        }
        return res;
    }

    private String toStringAux(NodoAVL n) {
        String s = "";
        if (n != null) {
            s = s + "Nodo: " + n.getElemento() + " (Altura: " + n.getAltura() + ")\n";

            if (n.getIzquierdo() != null) {
                s = s + "   HI: " + n.getIzquierdo().getElemento() + "\n";
            } else {
                s = s + "   HI: -\n";
            }

            if (n.getDerecho() != null) {
                s = s + "   HD: " + n.getDerecho().getElemento() + "\n";
            } else {
                s = s + "   HD: -\n";
            }

            s = s + toStringAux(n.getIzquierdo());
            s = s + toStringAux(n.getDerecho());
        }
        return s;
    }
}