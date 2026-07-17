import GrafoHabitaciones.estructurasAuxiliares.Lista;
public class ArbolAVL {
    private NodoAVL raiz;

    public boolean insertar(Comparable elem) {
        boolean[] exito = { true };
        this.raiz = insertarAux(this.raiz, elem, exito);
        return exito[0];
    }

    private NodoAVL insertarAux(NodoAVL n, Comparable elem, boolean[] exito) {
        // Caso base si llega a un lugar vacío, se crea el nuevo nodo
        if (n == null) {
            n = new NodoAVL(elem);
        } else {
            // compara el elemento a insertar con el elemento del nodo actual
            int comparacion = elem.compareTo(n.getElemento());

            // el elemento ya existe en el árbol
            if (comparacion == 0) {
                exito[0] = false;
            } else {
                // el elemento es menor, se inserta en el subárbol izquierdo
                if (comparacion < 0) {
                    n.setIzquierdo(insertarAux(n.getIzquierdo(), elem, exito));

                    // el elemento es mayor, se inserta en el subárbol derecho
                } else {
                    n.setDerecho(insertarAux(n.getDerecho(), elem, exito));
                }

                // si se pudo insertar se actualiza la altura
                if (exito[0]) {
                    actualizarAltura(n);

                    // verifica si el nodo quedó desbalanceado y si es necesario hace las rotaciones
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
                // caso 1: hoja
                if (n.getIzquierdo() == null && n.getDerecho() == null) {
                    n = null;

                    // caso 2: solo hijo derecho
                } else if (n.getIzquierdo() == null) {
                    n = n.getDerecho();

                    // caso 2: solo hijo izquierdo
                } else if (n.getDerecho() == null) {
                    n = n.getIzquierdo();

                    // caso 3: dos hijos
                } else {
                    // busco el sucesor (mínimo del subárbol derecho)
                    Comparable sucesor = obtenerMinimo(n.getDerecho());
                    n.setElemento(sucesor);
                    // elimino el sucesor de forma recursiva, para que la
                    // recursion actualice altura y balancee todos los nodos
                    // del camino al volver
                    boolean[] exitoAux = { false };
                    n.setDerecho(eliminarAux(n.getDerecho(), sucesor, exitoAux));
                }
            }
            if (n != null && exito[0]) {
                actualizarAltura(n);
                n = balancear(n);
            }
        }
        return n;
    }

    private Comparable obtenerMinimo(NodoAVL n) {
        while (n.getIzquierdo() != null) {
            n = n.getIzquierdo();
        }
        return n.getElemento();
    }

    public int getBalance(NodoAVL n) {
        int balance = 0;
        if (n != null) {
            int altIzq;
            int altDer;
            // Controlamos el hijo izquierdo
            if (n.getIzquierdo() != null) {
                altIzq = n.getIzquierdo().getAltura();
            } else {
                altIzq = -1;
            }
            // Controlamos el hijo derecho
            if (n.getDerecho() != null) {
                altDer = n.getDerecho().getAltura();
            } else {
                altDer = -1;
            }
            balance = altIzq - altDer;
        }
        return balance;
    }

    public NodoAVL rotarDerecha(NodoAVL n) {
        NodoAVL h = n.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        n.setIzquierdo(temp);
        h.setDerecho(n);

        actualizarAltura(n);
        actualizarAltura(h);
        return h;
    }

    public NodoAVL rotarIzquierda(NodoAVL n) {
        NodoAVL h = n.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        n.setDerecho(temp);
        h.setIzquierdo(n);

        actualizarAltura(n);
        actualizarAltura(h);
        return h;
    }

    private void actualizarAltura(NodoAVL n) {
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

            // Seteamos 1 más el máximo entre las dos alturas
            if (altIzq > altDer) {
                n.setAltura(1 + altIzq);
            } else {
                n.setAltura(1 + altDer);
            }
        }
    }

    private NodoAVL balancear(NodoAVL n) {
        NodoAVL resultado = n; // si esta balanceado devolvemos el mismo nodo

        if (n != null) {
            int balance = getBalance(n);

            // caso 1 desbalanceado a la izquierda
            if (balance > 1) {
                if (getBalance(n.getIzquierdo()) >= 0) {
                    // Caso Izquierda - Izquierda
                    resultado = rotarDerecha(n);
                } else {
                    // caso Izquierda - Derecha
                    n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
                    resultado = rotarDerecha(n);
                }
            }
            // Caso 2 desbalanceado a la derecha
            else if (balance < -1) {
                if (getBalance(n.getDerecho()) <= 0) {
                    // caso Derecha - Derecha
                    resultado = rotarIzquierda(n);
                } else {
                    // caso Derecha - Izquierda
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
                pertenece = true; // Lo encontramos
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
            // Creamos el nuevo nodo con el mismo elemento
            nuevoNodo = new NodoAVL(n.getElemento());
            // Seteamos la altura que ya tenía
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

}
//ejje