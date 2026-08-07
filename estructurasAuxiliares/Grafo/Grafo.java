package estructurasAuxiliares.Grafo;

import estructurasAuxiliares.Lineales.Lista;

public class Grafo {
    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object vert) {
        boolean exito = false;
        NodoVert nuevo;
        if (this.inicio == null) {
            nuevo = new NodoVert(vert, null, null);
            this.inicio = nuevo;
        } else {
            NodoVert aux = this.inicio;
            NodoVert anterior = null;
            while (aux != null && !exito) {
                if (aux.getElem().equals(vert)) {
                    exito = true;
                } else {
                    anterior = aux;
                    aux = aux.getSigVert();
                }
            }
            if (!exito) {
                nuevo = new NodoVert(vert, null, null);
                anterior.setSigVert(nuevo);
            }
        }
        return !exito;
    }

    public boolean eliminarVertice(Object vert) {
        boolean encontrado = false;
        NodoVert aux = this.inicio;
        NodoVert anterior = null;
        if (this.inicio != null) {
            // busco si existe la habitacion a eliminar
            while (aux != null && !encontrado) {
                if (aux.getElem().equals(vert)) {
                    encontrado = true;
                } else {
                    anterior = aux;
                    aux = aux.getSigVert();
                }
            }
            if (encontrado) {

                NodoAdy auxAdy = aux.getPrimerAdy();

                while (auxAdy != null) {
                    eliminarArcoVerticeAux(auxAdy.getVertice(), aux);
                    auxAdy = auxAdy.getSigAdyacente();
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

    private void eliminarArcoVerticeAux(NodoVert vert, NodoVert eliminado) {
        NodoAdy actual = vert.getPrimerAdy();
        NodoAdy anterior = null;
        boolean encontrado = false;
        while (actual != null && !encontrado) {
            if (actual.getVertice() == eliminado) {
                encontrado = true;
                if (anterior == null) {
                    vert.setPrimerAdy(actual.getSigAdyacente());
                } else {
                    anterior.setSigAdyacente(actual.getSigAdyacente());
                }
            } else {
                anterior = actual;
                actual = actual.getSigAdyacente();
            }
        }
    }

    public boolean existeVertice(Object vert) {
        boolean exito = false;
        NodoVert aux = this.inicio;
        if (aux != null) {
            // va a recorrer hasta que encuentre la habitacion o ya no queden mas
            // habitaciones
            while (aux != null && !exito) {
                if (aux.getElem().equals(vert)) {
                    exito = true;
                } else {
                    aux = aux.getSigVert();
                }
            }
        }
        return exito;
    }

    public boolean existeCamino(Object vert1, Object vert2) {
        boolean existe = false;
        NodoVert v1 = buscarVertice(vert1);
        NodoVert v2 = buscarVertice(vert2);
        if (this.inicio != null && v1 != null && v2 != null) {
            // creamos una lista para recorrer los caminos efectivamente
            Lista visitados = new Lista();
            existe = profundidadDesde(v1, v2, visitados);
        }
        return existe;
    }

    private boolean profundidadDesde(NodoVert v1, NodoVert v2, Lista visitados) {
        boolean existe = false;
        // verifica que la habitacion en la que estamos parados es la misma que la
        // segunda ingresada por parametro
        if (v1.getElem().equals(v2.getElem())) {
            existe = true;
        } else {
            // utilizamos la lista para que no se armen bucles y solo pasemos una vez por
            // cada camino
            visitados.insertar(v1.getElem(), visitados.longitud() + 1);
            // avanzamos por todos los caminos que hayan desde la habitacion 1
            NodoAdy aux2 = v1.getPrimerAdy();
            while ((aux2 != null) && (!existe)) {
                // si la habitacion no se encuntra en la lista (es decir que no analizamos
                // caminos todavia) vuelve a llamar al metodo
                if (visitados.localizar(aux2.getVertice().getElem()) < 0) {
                    existe = profundidadDesde(aux2.getVertice(), v2, visitados);
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

    // como en el grafo van a recibir el codigo de la habitacion y no el vertice en
    // si, hay que buscarlos
    private NodoVert buscarVertice(Object vert) {
        NodoVert vertice = null;
        boolean encontrado = false;
        if (this.inicio != null) {
            NodoVert aux = this.inicio;
            while (aux != null && !encontrado) {
                if (aux.getElem().equals(vert)) {
                    encontrado = true;
                    vertice = aux;
                } else {
                    aux = aux.getSigVert();
                }
            }
        }
        return vertice;
    }

    public boolean eliminarArco(Object vert1, Object vert2) {
        boolean eliminado = false;
        if (this.inicio != null) {
            // busco las habitaciones por su codigo
            NodoVert v1 = buscarVertice(vert1);
            NodoVert v2 = buscarVertice(vert2);
            // si ambas habitaciones existen busco si hay un arco entre ella y lo elimino
            if ((v1 != null) && (v2 != null)) {
                // llamamos al metodo para buscar y elminar dos veces porque es no dirigido
                eliminado = eliminarAdy(v1, v2);
                // si se elimino para un lado del grafo elimino para el otro
                if (eliminado) {
                    eliminado = eliminarAdy(v2, v1);
                }
            }
        }
        return eliminado;
    }

    private boolean eliminarAdy(NodoVert v1, NodoVert v2) {
        boolean eliminado = false;
        NodoAdy anterior = null;
        NodoAdy aux2 = v1.getPrimerAdy();
        // recorremos hasta que encontremos la habitacion a eliminar o no queden mas
        // habitaciones
        while ((aux2 != null) && (!eliminado)) {
            if (aux2.getVertice().getElem().equals(v2.getElem())) {
                // verificamos si el que se tiene que eliminar es el primer adyacente
                if (anterior == null) {
                    v1.setPrimerAdy(aux2.getSigAdyacente());
                } else {
                    anterior.setSigAdyacente(aux2.getSigAdyacente());
                }
                eliminado = true;
            } else {
                // avanzamos si no es la habitacion que queremos
                anterior = aux2;
                aux2 = aux2.getSigAdyacente();
            }

        }
        return eliminado;
    }

    public boolean insertarArco(Object vert1, Object vert2, int etiqueta) {
        NodoVert v1 = buscarVertice(vert1);
        NodoVert v2 = buscarVertice(vert2);
        boolean exito = false;
        if (v1 != null && v2 != null) {
            boolean verificarArco = verificarArcoAux(v1, v2);
            // busco si existe un arco entre las habitaciones
            if (!verificarArco) {
                // si no existe el arco, lo creo
                NodoAdy nuevoAdy1 = new NodoAdy(v2, v1.getPrimerAdy(), etiqueta);
                NodoAdy nuevoAdy2 = new NodoAdy(v1, v2.getPrimerAdy(), etiqueta);
                v1.setPrimerAdy(nuevoAdy1);
                v2.setPrimerAdy(nuevoAdy2);
                exito = true;
            }
            // si el arco ya existe, no creo uno nuevo y retorno falso, ya que no tiene
            // sentido crear arcos paralelos
        }
        return exito;
    }

    private boolean verificarArcoAux(NodoVert vert1, NodoVert vert2) {
        NodoAdy auxAdy = vert1.getPrimerAdy();
        boolean encontrado = false;
        while (auxAdy != null && !encontrado) {
            // busco si existe un arco entre las habitaciones
            if (auxAdy.getVertice().getElem().equals(vert2.getElem())) {
                encontrado = true;
            } else {
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return encontrado;
    }

    public NodoAdy getAdyacentes(Object codigoHabitacion) {
        NodoVert vertice = buscarVertice(codigoHabitacion);
        NodoAdy resultado = null;
        if (vertice != null) {
            resultado = vertice.getPrimerAdy();
        }
        return resultado;
    }

    public boolean existeArco(Object vert1, Object vert2) {
        boolean existe = false;
        NodoVert v1 = buscarVertice(vert1);
        NodoVert v2 = buscarVertice(vert2);
        if (v1 != null && v2 != null) {
            // reutilizo mi metodo privado que verifica la existencia de arcos
            existe = verificarArcoAux(v1, v2);
        }
        return existe;
    }
    public int getEtiqueta(Object vert1, Object vert2) {
        int etiqueta = -1;//si el arco no existe retorna -1

        NodoVert v1 = buscarVertice(vert1);
        NodoVert v2 = buscarVertice(vert2);

        if (v1 != null && v2 != null) {
            NodoAdy aux = v1.getPrimerAdy();

            while (aux != null && etiqueta == -1) {
                if (aux.getVertice().getElem().equals(vert2)) {
                    etiqueta = aux.getEtiqueta();
                } else {
                    aux = aux.getSigAdyacente();
                }
            }
        }

        return etiqueta;
    }
    // metodos para el menu

    public boolean sePuedeLlegar(Object origen, Object destino, int puntajeDisponible) {
        boolean exito = false;
        // va a entrar solo si existe ese codigo en el grafo
        if (existeVertice(origen) && existeVertice(destino)) {
            // creamos la lista y llamamos al metodo privado que recorre el grafo
            Lista visitados = new Lista();
            exito = sePuedeLlegarAux(origen, destino, puntajeDisponible, visitados);
        }
        return exito;
    }

    private boolean sePuedeLlegarAux(Object actual, Object destino, int puntajeDisponible, Lista visitados) {
        boolean exito = false;
        // ya estamos parados en el destino
        if (actual.equals(destino)) {
            exito = true;
        } else {
            // insertamos el vertice en la lista
            visitados.insertar(actual, visitados.longitud() + 1);
            NodoAdy auxAdy = getAdyacentes(actual);
            // si existe un adyacente y si todavia no llegamos a destino entra
            while (auxAdy != null && !exito) {
                // va a entrar si todavia hay el suficiente puntaje que requiere el vertice
                if (visitados.localizar(auxAdy.getVertice().getElem()) < 0
                        && puntajeDisponible >= auxAdy.getEtiqueta()) {
                    // llamamos recursivamente restando el puntaje que saco ese vertice
                    exito = sePuedeLlegarAux(auxAdy.getVertice().getElem(), destino,
                            puntajeDisponible - auxAdy.getEtiqueta(), visitados);
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return exito;
    }

    public boolean minimoPuntaje(Object origen, Object destino, int[] mejorPuntaje, Lista[] mejorCamino) {
        boolean exito = false;
        // va a entrar solo si existe ese codigo en el grafo
        if (existeVertice(origen) && existeVertice(destino)) {
            // creamos dos listas para ir guardando los vertices que ya accedimos para no
            // recorrer de mas y la otra para guardar el camino que hacemos
            Lista visitados = new Lista();
            Lista caminoActual = new Lista();
            // guarda el valor mas alto que java puede asignar
            mejorPuntaje[0] = Integer.MAX_VALUE;
            // llamamos al metodo privado que recorre
            minimoPuntajeAux(origen, destino, 0, visitados, caminoActual, mejorPuntaje, mejorCamino);
            // si efectivamente existe un camino mejor camino lo debe tener
            if (mejorCamino[0] != null) {
                exito = true;
            }
        }
        return exito;
    }

    private void minimoPuntajeAux(Object actual, Object destino, int acumulado, Lista visitados,
            Lista caminoActual, int[] mejorPuntaje, Lista[] mejorCamino) {

        // inserta el primer vertice en ambas listas porque es el de origen
        visitados.insertar(actual, visitados.longitud() + 1);
        caminoActual.insertar(actual, caminoActual.longitud() + 1);
        //entra si ya estamos parados en destino
        if (actual.equals(destino)) {
            //se fija que el puntaje que acumulamos es mejor al que ya teniamos, si lo es asigna el nuevo camino, si no lo es mantiene el viejo
            if (acumulado < mejorPuntaje[0]) {
                mejorPuntaje[0] = acumulado;
                // clona el camino actual para asignarlo como el mejor
                mejorCamino[0] = caminoActual.clone();
            }
        } else {
            NodoAdy auxAdy = getAdyacentes(actual);
            //mientras existan adyacentes los va a recorrer
            while (auxAdy != null) {
                //en acumulado va a sumando los puntos que necesita entre cada vertice
                if (visitados.localizar(auxAdy.getVertice().getElem()) < 0) {
                    minimoPuntajeAux(auxAdy.getVertice().getElem(), destino,
                            acumulado + auxAdy.getEtiqueta(), visitados, caminoActual,
                            mejorPuntaje, mejorCamino);
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }

        visitados.eliminar(visitados.localizar(actual));
        caminoActual.eliminar(caminoActual.localizar(actual));
    }

    public Lista sinPasarPor(Object origen, Object destino, Object evitar, int limitePuntos) {
        Lista caminos = new Lista();
        //entra si existen todos los vertices, y si el origen y el destino son distons del que debemos evitar
        if (existeVertice(origen) && existeVertice(destino) && existeVertice(evitar) && !origen.equals(evitar)&& !destino.equals(evitar)) {
            Lista visitados = new Lista();
            Lista caminoActual = new Lista();
            //insertamos primero el que debemos evitar para que al hacer el recorrido siempre piense que ya pasamos por ese y no entre justamente porque es el que debemos evitar
            visitados.insertar(evitar, visitados.longitud() + 1);
            //llamamos al metodo que recorre e indica camino
            sinPasarPorAux(origen, destino, 0, limitePuntos, visitados, caminoActual, caminos);
        }
        return caminos;
    }

    private void sinPasarPorAux(Object actual, Object destino, int acumulado, int limitePuntos,
            Lista visitados, Lista caminoActual, Lista caminos) {

        visitados.insertar(actual, visitados.longitud() + 1);
        caminoActual.insertar(actual, caminoActual.longitud() + 1);
        //estamos parados a donde tenemos que llegar 
        if (actual.equals(destino)) {
            //inserta el camino que hicimos recien acumulando si antes ya habiamos encontrado otro
            caminos.insertar(caminoActual.clone(), caminos.longitud() + 1);
        } else {
            NodoAdy auxAdy = getAdyacentes(actual);
            //recorremos mientras existan adayacentes
            while (auxAdy != null) {
                //verifica que ese vertice no este en la lista (que ya lo hayamos visitado) y que todavia no lleguemos al tope de puntos
                if (visitados.localizar(auxAdy.getVertice().getElem()) < 0 && acumulado + auxAdy.getEtiqueta() <= limitePuntos) {
                    sinPasarPorAux(auxAdy.getVertice().getElem(), destino,
                            acumulado + auxAdy.getEtiqueta(), limitePuntos, visitados, caminoActual, caminos);
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        visitados.eliminar(visitados.localizar(actual));
        caminoActual.eliminar(caminoActual.localizar(actual));
    }

    /*
     * //metodo si se puede llegar a modificar el puntaje entre algunas habitaciones
     * public boolean modificarPuntajeArco(Object vert1, Object vert2, int
     * nuevoPuntaje) {
     * NodoVert v1 = buscarVertice(vert1);
     * NodoVert v2 = buscarVertice(vert2);
     * boolean exito = false;
     * if (v1 != null && v2 != null) {
     * NodoAdy ady1 = buscarAdyacente(v1, v2);
     * NodoAdy ady2 = buscarAdyacente(v2, v1);
     * if (ady1 != null && ady2 != null) {
     * ady1.setEtiqueta(nuevoPuntaje);
     * ady2.setEtiqueta(nuevoPuntaje);
     * exito = true;
     * }
     * }
     * return exito;
     * }
     * private NodoAdy buscarAdyacente(NodoVert v1, NodoVert v2){
     * NodoAdy ady=null;
     * NodoAdy auxAdy=v1.getPrimerAdy();
     * boolean encontrado = false;
     * while (auxAdy != null && !encontrado) {
     * if (auxAdy.getVertice().getElem().equals(v2.getElem())) {
     * ady=auxAdy;
     * encontrado = true;
     * } else {
     * auxAdy = auxAdy.getSigAdyacente();
     * }
     * }
     * return ady;
     * }
     */
    @Override
    public String toString() {
        String res;
        if (this.inicio == null) {
            res = "grafo vacio";
        } else {
            res = toStringAux(this.inicio);
        }
        return res;
    }
    private String toStringAux(NodoVert v){
        String res = "----GRAFO-----\n";
        NodoVert aux=v;
        while(aux!=null){
            res += "("+aux.getElem()+")\n";
            NodoAdy auxAdy=aux.getPrimerAdy();
            while (auxAdy!=null) {
                res+="|___ "+auxAdy.getVertice().getElem()+"("+auxAdy.getEtiqueta()+")";
                res+="\n";
                auxAdy=auxAdy.getSigAdyacente();
            }
            res+="\n";
            aux=aux.getSigVert();
        }
        return res;
    }
    /* private String toStringAux(NodoVert vert) {
        String s = "";
        if (vert != null) {
            s = s + "Vertice: " + vert.getElem() + "\n";

            NodoAdy auxAdy = vert.getPrimerAdy();
            if (auxAdy == null) {
                s = s + "   Sin adyacentes\n";
            } else {
                while (auxAdy != null) {
                    s = s + "   ADY: " + auxAdy.getVertice().getElem() +
                            " (Puntaje: " + auxAdy.getEtiqueta() + ")\n";
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }

            s = s + toStringAux(vert.getSigVert());
        }
        return s;
    } */
}
