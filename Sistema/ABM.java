package Sistema;

import java.util.Scanner;

import clases.*;
import estructurasAuxiliares.*;
import estructurasAuxiliares.Conjuntista.ArbolAVL;
import estructurasAuxiliares.Conjuntista.TablaDesafioResueltos;
import estructurasAuxiliares.Conjuntista.TablaHashEquipos;
import estructurasAuxiliares.Grafo.Grafo;
import estructurasAuxiliares.Grafo.NodoAdy;
import estructurasAuxiliares.Lineales.Lista;

public class ABM {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArbolAVL avlHabitaciones = new ArbolAVL();
        Grafo grafo = new Grafo();
        TablaHashEquipos tablaEquipos = new TablaHashEquipos();
        TablaDesafioResueltos desResueltos = new TablaDesafioResueltos(); // hacer cuando terminen desafios

        int opc;
        CargarArchivo.cargarArchivo(avlHabitaciones, grafo, tablaEquipos, desResueltos);
        do {
            System.out.println("1. ABM Habitaciones");
            System.out.println("2. ABM Desafios");
            System.out.println("3. ABM Equipos");
            System.out.println("4. Consultas Habitaciones");
            System.out.println("5. Consultas Desafios");
            System.out.println("6. Consultas Equipos");
            System.out.println("7. Mostrar Sistema");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            opc = sc.nextInt();

            switch (opc) {
                case 1:
                    menuHabitaciones(sc, avlHabitaciones, grafo, tablaEquipos);
                    break;

                case 2:
                    menuDesafios(sc, avlHabitaciones);
                    break;

                case 3:
                    menuEquipos(sc, tablaEquipos, avlHabitaciones);
                    break;

                case 4:
                    menuConsultasHabitaciones(sc, avlHabitaciones, grafo);
                    break;

                case 5:
                    menuConsultasDesafios(sc, avlHabitaciones, tablaEquipos, desResueltos);
                    break;

                case 6:
                    // Consulta equipos
                    break;

                case 7:
                    System.out.println(avlHabitaciones);
                    System.out.println(grafo);
                    System.out.println(tablaEquipos);
                    System.out.println(desResueltos);
                    break;

                case 0:
                    System.out.println("Saliendo..");
                    break;

                default:
                    System.out.println("Opcion incorrecta.");

            }

        } while (opc != 0);

        sc.close();
    }

    // Menu Habitaciones
    public static void menuHabitaciones(Scanner sc, ArbolAVL avl, Grafo grafo, TablaHashEquipos eq) {

        int op;
        do {

            System.out.println("\n- ABM HABITACIONES -");
            System.out.println("1. Alta Habitacion");
            System.out.println("2. Baja Habitacion");
            System.out.println("3. Modificacion Habitacion");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            op = sc.nextInt();

            switch (op) {

                case 1:
                    altaHabitacion(sc, avl, grafo);
                    break;

                case 2:
                    bajaHabitacion(sc, avl, grafo, eq);
                    break;

                case 3:
                    modificarHabitacion(sc, avl);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (op != 0);

    }

    public static void altaHabitacion(Scanner sc, ArbolAVL avl, Grafo grafo) {

        int codigo, planta, metros;
        String nombre;
        String tiene;
        boolean tieneSalida = false;

        System.out.println("Ingrese el codigo de la Habitacion: ");
        codigo = sc.nextInt();
        sc.nextLine();

        System.out.println("Ingrese el nombre de la Habitacion: ");
        nombre = sc.nextLine();

        System.out.println("Ingrese la planta de la Habitacion: ");
        planta = sc.nextInt();

        System.out.println("Ingrese los metros cuadrados de la Habitacion: ");
        metros = sc.nextInt();
        sc.nextLine();

        System.out.println("Tiene salida al exterior? (Si/No): ");
        tiene = sc.nextLine();

        if (tiene.equalsIgnoreCase("si")) {
            tieneSalida = true;
        }

        Habitacion nuevaHabitacion = new Habitacion(codigo, nombre, planta, metros, tieneSalida);

        if (avl.insertar(nuevaHabitacion)) {
            grafo.insertarVertice(codigo);
            System.out.println("Habitacion agregada exitosamente.");
            log.registrar("Se crea la habitacion " + codigo);
        } else {
            System.out.println("Ya existe una habitacion con ese codigo.");
            log.registrar("Error al crear habitacion, ya existe " + codigo);
        }
    }

    public static void bajaHabitacion(Scanner sc, ArbolAVL avl, Grafo grafo, TablaHashEquipos eq) {

        int codigoBuscar;

        System.out.println("Ingrese el codigo de la habitacion: ");
        codigoBuscar = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigoBuscar));

        if (habActual != null) {
            if (eq.existeEquipoEnHabitacion(codigoBuscar)) {
                System.out.println("No se puede eliminar: hay equipos ubicados en esta habitacion.");
                log.registrar("Error al eliminar habitacion " + codigoBuscar + ", hay equipos dentro");
            }
            else if (habActual.tieneSalida()) {// preguntar con el grupo que pasa ocn una habitacion de entrada
                System.out.println("No se puede eliminar esta habitacion.");
                log.registrar("Error al eliminar habitacion " + codigoBuscar);
            } else {
                avl.eliminar(habActual);
                grafo.eliminarVertice(codigoBuscar);
                log.registrar("Se borro la habitacion " + codigoBuscar);
                System.out.println("Habitacion eliminada correctamente.");
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("No se encontro la habitacion " + codigoBuscar);
        }
    }

    public static void modificarHabitacion(Scanner sc, ArbolAVL avl) {

        int codigoBuscar;

        System.out.println("Ingrese el codigo de la habitacion: ");
        codigoBuscar = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigoBuscar));

        if (habActual != null) {

            int opcMod;

            do {

                System.out.println("\n--- MODIFICAR HABITACION ---");
                System.out.println("1. Modificar Nombre");
                System.out.println("2. Modificar Planta");
                System.out.println("3. Modificar Metros");
                System.out.println("4. Modificar Salida");
                System.out.println("0. Volver");
                System.out.print("Opcion: ");

                opcMod = sc.nextInt();
                sc.nextLine();

                switch (opcMod) {

                    case 1:
                        System.out.println("Ingrese el nuevo nombre: ");
                        String nuevoNombre = sc.nextLine();
                        habActual.setNombre(nuevoNombre);
                        System.out.println("Nombre modificado.");
                        log.registrar("Se modifico el nombre de la habitacion " + codigoBuscar);
                        break;

                    case 2:
                        System.out.println("Ingrese la nueva planta: ");
                        int nuevaPlanta = sc.nextInt();
                        habActual.setPlanta(nuevaPlanta);
                        System.out.println("Planta modificada.");
                        log.registrar("Se modifico la planta de la habitacion " + codigoBuscar);
                        break;

                    case 3:
                        System.out.println("Ingrese los nuevos metros cuadrados: ");
                        int nuevosMetros = sc.nextInt();
                        habActual.setMetros(nuevosMetros);
                        System.out.println("Metros modificados.");
                        log.registrar("Se modificaron los metros de la habitacion " + codigoBuscar);
                        break;

                    case 4:
                        System.out.println("Ingrese si tiene salida (Si/No): ");
                        String tiene = sc.nextLine();

                        if (tiene.equalsIgnoreCase("si")) {
                            habActual.setTieneSalida(true);
                            System.out.println("Se cambio tiene salida.");
                            log.registrar("Se modifico la salida de la habitacion " + codigoBuscar);
                        } else if (tiene.equalsIgnoreCase("no")) {
                            habActual.setTieneSalida(false);
                            System.out.println("Se cambio tiene salida.");
                            log.registrar("Se modifico la salida de la habitacion " + codigoBuscar);
                        } else {
                            System.out.println("No se pudo determinar si tiene salida.");
                            log.registrar("No se pudo modificar la salida de la habitacion " + codigoBuscar);
                        }

                        break;

                    case 0:
                        break;

                    default:
                        System.out.println("Opcion incorrecta.");
                        log.registrar("Inserto opcion invalida en modificar habitacion.");
                }

            } while (opcMod != 0);

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("No se puede modificar habitacion " + codigoBuscar + ", no existe la habitacion.");
        }
    }

    // Consulta sobre habitaciones
    public static void menuConsultasHabitaciones(Scanner sc, ArbolAVL avl, Grafo grafo) {
        int opc;
        do {
            System.out.println("\n----- CONSULTAS HABITACIONES -----");
            System.out.println("1. Mostrar Habitacion");
            System.out.println("2. Habitaciones Contiguas");
            System.out.println("3. Es Posible Llegar");
            System.out.println("4. Minimo Puntaje");
            System.out.println("5. Sin Pasar Por");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    mostrarHabitacion(sc, avl);
                    break;
                case 2:
                    habitacionesContiguas(sc, avl, grafo);
                    break;
                case 3:
                    esPosibleLlegar(sc, grafo, avl);
                    break;
                case 4:
                    minimoPuntaje(sc, grafo, avl);
                    break;
                case 5:
                    sinPasarPor(sc, grafo, avl);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opc != 0);
    }

    // case 1
    public static void mostrarHabitacion(Scanner sc, ArbolAVL avl) {
        int codigo;
        System.out.println("ingrese el codigo de la habitacion: ");
        codigo = sc.nextInt();
        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigo));
        if (habActual != null) {
            System.out.println(habActual);
            log.registrar("habitacion con el codigo: " + codigo);
        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("No se pudo mostrar, la habitacion " + codigo + " no existe");
        }
    }

    // case 2
    public static void habitacionesContiguas(Scanner sc, ArbolAVL avl, Grafo grafo) {

        int codigo;

        System.out.println("Ingrese el codigo de la habitacion: ");
        codigo = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigo));

        if (habActual != null) {
            NodoAdy auxAdy = grafo.getAdyacentes(codigo);
            if (auxAdy != null) {
                while (auxAdy != null) {
                    Object codigo2 = auxAdy.getVertice().getElem();
                    System.out.println("el codigo de la habitacion: " + codigo2);
                    int puntaje = auxAdy.getEtiqueta();
                    System.out.println("el puntaje para pasar estaa habitacion es: " + puntaje);
                    auxAdy = auxAdy.getSigAdyacente();
                }
                log.registrar("Se mostraron las contiguas de la habitacion " + codigo);
            } else {
                System.out.println("la habitacion: " + codigo + ", no tiene contigua");
                log.registrar("La habitacion " + codigo + " no tiene contiguas");
            }
        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("No se pudo mostrar contiguas, la habitacion " + codigo + " no existe");
        }
    }

    // case 3
    public static void esPosibleLlegar(Scanner sc, Grafo grafo, ArbolAVL avl) {
        int cod1, cod2, k;

        System.out.println("Ingrese el codigo de la habitacion de origen: ");
        cod1 = sc.nextInt();

        System.out.println("Ingrese el codigo de la habitacion de destino: ");
        cod2 = sc.nextInt();

        System.out.println("Ingrese el puntaje k: ");
        k = sc.nextInt();

        Habitacion hab1 = (Habitacion) avl.recuperar(new Habitacion(cod1));
        Habitacion hab2 = (Habitacion) avl.recuperar(new Habitacion(cod2));

        if (hab1 != null && hab2 != null) {
            Lista visitados = new Lista();
            boolean existe = puedeLlegarAux(cod1, cod2, k, grafo, visitados);
            if (existe) {
                System.out.println("es posible llegar de la habitacion de origen a la habitacion de destino.");
                log.registrar("es posible llegar de la habitacion: " + cod1 + " a la habitacion: " + cod2);
            } else {
                System.out.println("no es posible llegar de la habitacion de origen a la de destino.");
                log.registrar("no es posible llegar de la habitacion: " + cod1 + " a la habitacion: " + cod2);
            }
        } else {
            System.out.println("Una de las dos habitaciones no existe.");
            log.registrar("No se pudo evaluar esPosibleLlegar, alguna habitacion no existe");
        }
    }

    private static boolean puedeLlegarAux(int actual, int destino, int puntajeDis, Grafo grafo, Lista visitados) {
        boolean exito = false;
        if (actual == destino) {
            exito = true;
        } else {
            visitados.insertar(actual, visitados.longitud() + 1);
            NodoAdy auxAdy = grafo.getAdyacentes(actual);
            while (auxAdy != null && !exito) {
                if (visitados.localizar(auxAdy.getVertice().getElem()) < 0 && puntajeDis >= auxAdy.getEtiqueta()) {
                    exito = puedeLlegarAux((Integer) auxAdy.getVertice().getElem(), destino,
                            puntajeDis - auxAdy.getEtiqueta(), grafo, visitados);
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return exito;
    }

    // case 4
    public static void minimoPuntaje(Scanner sc, Grafo grafo, ArbolAVL avl) {
        int cod1, cod2;

        System.out.println("Ingrese el codigo de la habitacion de origen: ");
        cod1 = sc.nextInt();

        System.out.println("Ingrese el codigo de la habitacion de destino: ");
        cod2 = sc.nextInt();

        Habitacion hab1 = (Habitacion) avl.recuperar(new Habitacion(cod1));
        Habitacion hab2 = (Habitacion) avl.recuperar(new Habitacion(cod2));

        if (hab1 != null && hab2 != null) {

            Lista visitados = new Lista();
            Lista caminoActual = new Lista();
            int[] mejorPuntaje = { Integer.MAX_VALUE };
            Lista[] mejorCamino = { null };

            buscarMinimo(cod1, cod2, 0, grafo, visitados, caminoActual, mejorPuntaje, mejorCamino);

            if (mejorCamino[0] != null) {
                System.out.println("El puntaje minimo es: " + mejorPuntaje[0]);
                System.out.println("El camino es: " + mejorCamino[0]);
                log.registrar("Se calculo el puntaje minimo entre " + cod1 + " y " + cod2);
            } else {
                System.out.println("No existe camino entre esas dos habitaciones.");
                log.registrar("No existe camino entre " + cod1 + " y " + cod2);
            }

        } else {
            System.out.println("Una de las dos habitaciones no existe.");
            log.registrar("No se pudo evaluar minimoPuntaje, alguna habitacion no existe");
        }
    }

    private static void buscarMinimo(int actual, int destino, int acumulado, Grafo grafo, Lista visitados,
            Lista caminoActual, int[] mejorPuntaje, Lista[] mejorCamino) {

        visitados.insertar(actual, visitados.longitud() + 1);
        caminoActual.insertar(actual, caminoActual.longitud() + 1);

        if (actual == destino) {
            if (acumulado < mejorPuntaje[0]) {
                mejorPuntaje[0] = acumulado;
                mejorCamino[0] = caminoActual.clone();
            }
        } else {
            NodoAdy auxAdy = grafo.getAdyacentes(actual);
            while (auxAdy != null) {
                if (visitados.localizar(auxAdy.getVertice().getElem()) < 0) {
                    buscarMinimo((Integer) auxAdy.getVertice().getElem(), destino,
                            acumulado + auxAdy.getEtiqueta(), grafo, visitados, caminoActual,
                            mejorPuntaje, mejorCamino);
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }

        visitados.eliminar(visitados.localizar(actual));
        caminoActual.eliminar(caminoActual.localizar(actual));
    }

    // case 5
    public static void sinPasarPor(Scanner sc, Grafo grafo, ArbolAVL avl) {
        int cod1, cod2, cod3, p;
        System.out.println("ingrese el codigo de la habitacion 1: ");
        cod1 = sc.nextInt();
        System.out.println("ingrese el codigo de la habitacion 2: ");
        cod2 = sc.nextInt();
        System.out.println("ingrese el codigo de la habitacion 3: ");
        cod3 = sc.nextInt();
        System.out.println("ingrese el tope de puntos: ");
        p = sc.nextInt();

        Habitacion hab1 = (Habitacion) avl.recuperar(new Habitacion(cod1));
        Habitacion hab2 = (Habitacion) avl.recuperar(new Habitacion(cod2));
        Habitacion hab3 = (Habitacion) avl.recuperar(new Habitacion(cod3));

        if (hab1 != null && hab2 != null && hab3 != null && cod2 != cod3) {
            Lista visitados = new Lista();
            Lista caminoActual = new Lista();

            visitados.insertar(cod3, visitados.longitud() + 1);
            Lista caminos = new Lista();
            buscarCaminos(cod1, cod2, 0, p, grafo, visitados, caminoActual, caminos);
            if (caminos.esVacia()) {
                System.out.println("No hay caminos posibles con esas condiciones.");
                log.registrar("No hay caminos de " + cod1 + " a " + cod2 + " sin pasar por " + cod3);
            } else {
                System.out.println("Los caminos encontrados son:");
                for (int i = 1; i <= caminos.longitud(); i++) {
                    System.out.println(caminos.recuperar(i));
                }
                log.registrar("Se mostraron los caminos de " + cod1 + " a " + cod2 + " sin pasar por " + cod3);
            }
        } else {
            System.out.println("error, alguna de las habitaciones no existe.");
            log.registrar("alguna habitacion, no existe");
        }
    }

    private static void buscarCaminos(int actual, int destino, int acumulado, int p, Grafo grafo, Lista visitados,
            Lista caminoActual, Lista caminos) {
        visitados.insertar(actual, visitados.longitud() + 1);
        caminoActual.insertar(actual, caminoActual.longitud() + 1);

        if (actual == destino) {
            caminos.insertar(caminoActual.clone(), caminos.longitud() + 1);
        } else {
            NodoAdy auxAdy = grafo.getAdyacentes(actual);
            while (auxAdy != null) {
                if (visitados.localizar(auxAdy.getVertice().getElem()) < 0
                        && acumulado + auxAdy.getEtiqueta() <= p) {
                    buscarCaminos((Integer) auxAdy.getVertice().getElem(), destino,
                            acumulado + auxAdy.getEtiqueta(), p, grafo, visitados, caminoActual, caminos);
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }

        visitados.eliminar(visitados.localizar(actual));
        caminoActual.eliminar(caminoActual.localizar(actual));

    }

    // MENU DESAFIOS
    public static void menuDesafios(Scanner sc, ArbolAVL avl) {

        int op;

        do {

            System.out.println("\n----- ABM DESAFIOS -----");
            System.out.println("1. Alta Desafio");
            System.out.println("2. Baja Desafio");
            System.out.println("3. Modificacion Desafio");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            op = sc.nextInt();

            switch (op) {

                case 1:
                    altaDesafio(sc, avl);
                    break;

                case 2:
                    bajaDesafio(sc, avl);
                    break;

                case 3:
                    modificarDesafio(sc, avl);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (op != 0);

    }

    public static void altaDesafio(Scanner sc, ArbolAVL avl) {

        int codigoHab;
        int puntaje;
        String nombre;
        String tipo;

        System.out.println("Ingrese el codigo de la habitacion del desafio: ");
        codigoHab = sc.nextInt();

        // busco la habitacion, el desafio se guarda dentro del avl propio de esa
        // habitacion
        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigoHab));

        if (habActual != null) {

            System.out.println("Ingrese el puntaje que otorga el desafio: ");
            puntaje = sc.nextInt();
            sc.nextLine();

            System.out.println("Ingrese el nombre del desafio: ");
            nombre = sc.nextLine();

            System.out.println("Ingrese el tipo del desafio: ");
            tipo = sc.nextLine();

            Desafio nuevoDesafio = new Desafio(puntaje, nombre, tipo);

            if (habActual.getDesafios().insertar(nuevoDesafio)) {
                System.out.println("Desafio agregado exitosamente.");
                log.registrar("Se creo el Desafio " + nombre + " en habitacion " + codigoHab);
            } else {
                System.out.println("Ya existe un desafio con ese puntaje en esta habitacion.");
                log.registrar("Ya existe un desafio con puntaje " + puntaje + " en habitacion " + codigoHab);
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitacion " + codigoHab + " no existe");
        }
    }

    public static void bajaDesafio(Scanner sc, ArbolAVL avl) {

        int codigoHab;
        int puntajeBuscar;

        System.out.println("Ingrese el codigo de la habitacion del desafio: ");
        codigoHab = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigoHab));

        if (habActual != null) {

            System.out.println("Ingrese el puntaje del desafio a eliminar: ");
            puntajeBuscar = sc.nextInt();

            Desafio desafioActual = (Desafio) habActual.getDesafios().recuperar(new Desafio(puntajeBuscar));

            if (desafioActual != null) {
                habActual.getDesafios().eliminar(desafioActual);
                System.out.println("Desafio eliminado correctamente.");
                log.registrar(
                        "Se elimino el desafio con puntaje de " + puntajeBuscar + " de la habitacion " + codigoHab);
            } else {
                System.out.println("El desafio no existe en esta habitacion.");
                log.registrar("Desafio con puntaje " + puntajeBuscar + " no existe en la habitacion " + codigoHab);
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitacion " + codigoHab + " no existe");
        }
    }

    public static void modificarDesafio(Scanner sc, ArbolAVL avl) {

        int codigoHab;
        int puntajeBuscar;

        System.out.println("Ingrese el codigo de la habitacion del desafio: ");
        codigoHab = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigoHab));

        if (habActual != null) {

            System.out.println("Ingrese el puntaje del desafio a modificar: ");
            puntajeBuscar = sc.nextInt();
            sc.nextLine();

            Desafio desafioActual = (Desafio) habActual.getDesafios().recuperar(new Desafio(puntajeBuscar));

            if (desafioActual != null) {

                int opcMod;

                do {

                    System.out.println("\n--- MODIFICAR DESAFIO ---");
                    System.out.println("1. Modificar Nombre");
                    System.out.println("2. Modificar Tipo");
                    System.out.println("0. Volver");
                    System.out.print("Opcion: ");

                    opcMod = sc.nextInt();
                    sc.nextLine();

                    switch (opcMod) {

                        case 1:
                            System.out.println("Ingrese el nuevo nombre: ");
                            String nuevoNombre = sc.nextLine();
                            desafioActual.setNombre(nuevoNombre);
                            System.out.println("Nombre modificado.");
                            log.registrar("Se cambio el nombre del Desafio con puntaje " + puntajeBuscar
                                    + " de la habitacion " + codigoHab);
                            break;

                        case 2:
                            System.out.println("Ingrese el nuevo tipo: ");
                            String nuevoTipo = sc.nextLine();
                            desafioActual.setTipo(nuevoTipo);
                            System.out.println("Tipo modificado.");
                            log.registrar("Se cambio el tipo del Desafio con puntaje " + puntajeBuscar
                                    + " de la habitacion " + codigoHab);
                            break;

                        case 0:
                            break;

                        default:
                            System.out.println("Opcion incorrecta.");
                            log.registrar("Opcion incorrecta en modificar desafio");
                    }

                } while (opcMod != 0);

            } else {
                System.out.println("El desafio no existe en esta habitacion.");
                log.registrar("Desafio con puntaje " + puntajeBuscar + " no existe en la habitacion " + codigoHab);
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitacion " + codigoHab + " no existe");
        }
    }

    // operaciones desafio Punto 4

    // MENU CONSULTAS DESAFIOS
    public static void menuConsultasDesafios(Scanner sc, ArbolAVL avl, TablaHashEquipos tablaEquipos,
            TablaDesafioResueltos desResueltos) {

        int op;

        do {
            System.out.println("\n----- CONSULTAS DESAFIOS -----");
            System.out.println("1. Mostrar Desafio");
            System.out.println("2. Mostrar Desafios Resueltos de un Equipo");
            System.out.println("3. Verificar si un Equipo resolvio un Desafio");
            System.out.println("4. Mostrar Desafios por Tipo y Rango");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            op = sc.nextInt();

            switch (op) {
                case 1:
                    mostrarDesafio(sc, avl);
                    break;
                case 2:
                    mostrarDesafiosResueltos(sc, desResueltos);
                    break;
                case 3:
                    verificarDesafioResuelto(sc, avl, desResueltos);
                    break;
                case 4:
                    mostrarDesafiosTipo(sc, avl);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (op != 0);
    }

    // punto a
    public static void mostrarDesafio(Scanner sc, ArbolAVL avl) {

        int codigoHab;
        int puntajeBuscar;

        System.out.println("Ingrese el codigo de la habitacion del desafio: ");
        codigoHab = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigoHab));

        if (habActual != null) {

            System.out.println("Ingrese el puntaje del desafio a mostrar: ");
            puntajeBuscar = sc.nextInt();

            Desafio desafioActual = (Desafio) habActual.getDesafios().recuperar(new Desafio(puntajeBuscar));

            if (desafioActual != null) {
                System.out.println(desafioActual);
                log.registrar("Se mostro el desafio " + desafioActual);
            } else {
                System.out.println("El desafio no existe en esta habitacion.");
                log.registrar("El desafio " + desafioActual + "no existe en la habitacion " + habActual);
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("No existe en la habitacion ingresada por el usuario");
        }
    }

    // punto b
    public static void mostrarDesafiosResueltos(Scanner sc, TablaDesafioResueltos desResueltos) {

        String nombreEquipo;
        sc.nextLine();
        System.out.println("Ingrese el nombre del equipo: ");
        nombreEquipo = sc.nextLine();

        Lista resueltos = desResueltos.obtenerResueltos(nombreEquipo);

        if (resueltos.esVacia()) {
            System.out.println("El equipo " + nombreEquipo + " todavia no resolvio ningun desafio.");
            log.registrar("El equipo" + nombreEquipo + "no resolvio desafios");
        } else {
            System.out.println(resueltos);
            log.registrar("Se mostro los desafios resuletos por el equipo" + nombreEquipo);
        }
    }

    // punto c
    public static void verificarDesafioResuelto(Scanner sc, ArbolAVL avl, TablaDesafioResueltos desResueltos) {

        String nombreEquipo;
        int codigoHab, puntaje;
        sc.nextLine();
        System.out.println("Ingrese el nombre del equipo: ");
        nombreEquipo = sc.nextLine();

        System.out.println("Ingrese el codigo de la habitacion del desafio: ");
        codigoHab = sc.nextInt();

        System.out.println("Ingrese el puntaje del desafio: ");
        puntaje = sc.nextInt();

        Habitacion hab = (Habitacion) avl.recuperar(new Habitacion(codigoHab));

        if (hab == null) {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitaccion ingresada por el ususario no existe");
        } else {

            Desafio desafio = (Desafio) hab.getDesafios().recuperar(new Desafio(puntaje));

            if (desafio == null) {
                System.out.println("Ese desafio no existe en esa habitacion.");
                log.registrar("El desafip ingresado por el ususario no existe en la habitacion" + hab);
            } else {

                boolean resuelto = desResueltos.yaResuelto(nombreEquipo, desafio);

                if (resuelto) {
                    System.out.println("El equipo " + nombreEquipo + " YA resolvio ese desafio.");
                    log.registrar("Se indica que el equipo ya resolvio el desafio");
                } else {
                    System.out.println("El equipo " + nombreEquipo + " NO resolvio ese desafio.");
                    log.registrar("Se indica que el equipo no resolvio el desafio");
                }
            }
        }
    }

    // punto d
    public static void mostrarDesafiosTipo(Scanner sc, ArbolAVL avl) {

        int codigoHab, a, b;
        String tipo;

        System.out.println("Ingrese el codigo de la habitacion: ");
        codigoHab = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigoHab));

        if (habActual != null) {

            System.out.println("Ingrese el puntaje minimo (a): ");
            a = sc.nextInt();

            System.out.println("Ingrese el puntaje maximo (b): ");
            b = sc.nextInt();
            sc.nextLine();

            System.out.println("Ingrese el tipo de desafio a buscar: ");
            tipo = sc.nextLine();

            Lista enRango = habActual.getDesafios().listarRango(new Desafio(a), new Desafio(b));

            boolean encontroAlguno = false;
            for (int i = 1; i <= enRango.longitud(); i++) {
                Desafio d = (Desafio) enRango.recuperar(i);
                if (d.getTipo().equalsIgnoreCase(tipo)) {
                    System.out.println(d);
                    encontroAlguno = true;
                    log.registrar("Se muestran los desafios del tipo ingresado que estan dentro del rango ingresado");
                }
            }
            if (!encontroAlguno) {
                System.out.println("No hay desafios de tipo " + tipo + " en ese rango.");
                log.registrar("No hay desafios del tipo " + tipo + "en el rango ingresado");
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitacion ingresada por el usuario no existe");
        }
    }

    // MENU EQUIPOS
    public static void menuEquipos(Scanner sc, TablaHashEquipos tabla, ArbolAVL avl) {

        int op;

        do {

            System.out.println("\n----- ABM EQUIPOS -----");
            System.out.println("1. Alta Equipos");
            System.out.println("2. Baja Equipos");
            System.out.println("3. Modificacion Equipos");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            op = sc.nextInt();

            switch (op) {

                case 1:
                    altaEquipo(sc, tabla, avl);
                    break;

                case 2:
                    bajaEquipo(sc, tabla);
                    break;

                case 3:
                    modificarEquipo(sc, tabla, avl);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (op != 0);

    }

    public static void altaEquipo(Scanner sc, TablaHashEquipos tabla, ArbolAVL avl) {

        String nombre;
        int puntajeExigido;
        int codigoHabInicial;
        sc.nextLine();
        System.out.println("Ingrese el nombre del equipo: ");
        nombre = sc.nextLine();

        System.out.println("Ingrese el puntaje exigido para salir de la casa: ");
        puntajeExigido = sc.nextInt();

        System.out.println("Ingrese el codigo de la habitacion inicial del equipo: ");
        codigoHabInicial = sc.nextInt();

        // busco la habitacion donde arranca el equipo
        Habitacion habInicial = (Habitacion) avl.recuperar(new Habitacion(codigoHabInicial));

        if (habInicial != null) {

            // arranca con puntaje acumulado 0 y puntaje actual en la habitacion 0
            Equipo nuevoEquipo = new Equipo(nombre, puntajeExigido, 0, habInicial, 0);

            if (tabla.insertar(nuevoEquipo)) {
                System.out.println("Equipo agregado exitosamente.");
                log.registrar("Se creo correctamente el equipo " + nombre);
            } else {
                System.out.println("Ya existe un equipo con ese nombre.");
                log.registrar("Error al crear equipo " + nombre + ". Ya existe equipo con ese nombre");
            }

        } else {
            System.out.println("La habitacion no existe.");
        }
    }

    public static void bajaEquipo(Scanner sc, TablaHashEquipos tabla) {

        String nombreBuscar;
        sc.nextLine();
        System.out.println("Ingrese el nombre del equipo a eliminar: ");
        nombreBuscar = sc.nextLine();

        Equipo equipoActual = (Equipo) tabla.buscar(nombreBuscar);

        if (equipoActual != null) {
            tabla.eliminar(equipoActual.getNombre());
            System.out.println("Equipo eliminado correctamente.");
            log.registrar("Se elimino el equipo " + nombreBuscar);
        } else {
            System.out.println("El equipo no existe.");
            log.registrar("No existe el equipo " + nombreBuscar + " para eliminar");
        }
    }

    public static void modificarEquipo(Scanner sc, TablaHashEquipos tabla, ArbolAVL avl) {

        String nombreBuscar;
        sc.nextLine();
        System.out.println("Ingrese el nombre del equipo: ");
        nombreBuscar = sc.nextLine();

        Equipo equipoActual = (Equipo) tabla.buscar(nombreBuscar);

        if (equipoActual != null) {

            int opcMod;

            do {

                System.out.println("\n--- MODIFICAR EQUIPO ---");
                System.out.println("1. Modificar Puntaje Exigido");
                System.out.println("2. Modificar Habitacion Actual");
                System.out.println("0. Volver");
                System.out.print("Opcion: ");

                opcMod = sc.nextInt();

                switch (opcMod) {

                    case 1:
                        System.out.println("Ingrese el nuevo puntaje exigido: ");
                        int nuevoPuntajeExigido = sc.nextInt();
                        equipoActual.setPuntajeExigido(nuevoPuntajeExigido);
                        System.out.println("Puntaje exigido modificado.");
                        log.registrar("Cambio el puntaje exigido del equipo " + nombreBuscar);
                        break;

                    case 2:
                        System.out.println("Ingrese el codigo de la nueva habitacion: ");
                        int nuevoCodigoHab = sc.nextInt();

                        Habitacion nuevaHab = (Habitacion) avl.recuperar(new Habitacion(nuevoCodigoHab));

                        if (nuevaHab != null) {
                            equipoActual.setHabitacionActual(nuevaHab);
                            System.out.println("Habitacion actual modificada.");
                            log.registrar("Se cambio la Habitacion actual del equipo " + nombreBuscar);
                        } else {
                            System.out.println("La habitacion no existe.");
                            log.registrar("Error al cambiar habitacion actual del equipo " + nombreBuscar
                                    + ". Habitacion no existe");
                        }
                        break;

                    case 0:
                        break;

                    default:
                        System.out.println("Opcion incorrecta.");
                }

            } while (opcMod != 0);

        } else {
            System.out.println("El equipo no existe.");
            log.registrar("El equipo " + nombreBuscar + " a modificar no existe.");
        }
    }

    public static void mostrarInfoEquipo(TablaHashEquipos tablaEquipos, String nombreEquipo) {
        Equipo eq = tablaEquipos.buscar(nombreEquipo);

        if (eq != null) {
            System.out.println(eq.toString());
            log.registrar("Se consultó la información del equipo: " + nombreEquipo);
        } else {
            System.out.println("error: El equipo '" + nombreEquipo + "' no se encuentra registrado");
            log.registrar("Error al consultar equipo: '" + nombreEquipo + "' no se encuentra registrado");
        }
    }

    public static void posiblesDesafios(TablaHashEquipos tablaEquipos, Grafo grafoMapa, String nombreEquipo,
            int codHabitacionDestino) {
        // buscamos el equipo en la tabla hash por su nombre
        Equipo eq = tablaEquipos.buscar(nombreEquipo);

        if (eq == null) {
            System.out.println("Error: El equipo '" + nombreEquipo + "' no existe");
            log.registrar("Error en posiblesDesafios: El equipo '" + nombreEquipo + "' no existe");
        } else {
            // Obtenemos directamente la Habitacion actual desde el objeto Equipo
            Habitacion habActual = eq.getHabitacionActual();

            if (habActual == null) {
                System.out.println("Error: El equipo no tiene una habitación asignada actualmente");
                log.registrar(
                        "Error en posiblesDesafios: El equipo '" + nombreEquipo + "' no tiene habitación asignada");
            } else {
                // obtenemos el codigo de la habitacion actual
                int codHabActual = habActual.getCodigo();

                // verificamos la conexion en el grafo pasando los codigos de las habitaciones
                if (!grafoMapa.existeCamino(codHabActual, codHabitacionDestino)) {
                    System.out.println(" La habitación " + codHabitacionDestino +
                            " no es adyacente a la ubicación actual (" + codHabActual + ").");
                    log.registrar("Consulta posiblesDesafios: La habitación " + codHabitacionDestino +
                            " no es adyacente a la habitación " + codHabActual + " para el equipo " + nombreEquipo);
                } else {
                    // obtenemos el AVL de desafios directamente desde la habitacion actual
                    ArbolAVL avlDesafios = habActual.getDesafios();

                    if (avlDesafios == null || avlDesafios.esVacio()) {
                        System.out.println("La habitación " + codHabActual + " no tiene desafíos disponibles.");
                        log.registrar("Consulta posiblesDesafios: La habitación " + codHabActual
                                + " no tiene desafíos disponibles");
                    } else {
                        // listamos los desafios ordenados por puntaje
                        Lista listaDesafios = avlDesafios.listar();

                        System.out.println("\nDESAFÍOS DISPONIBLES EN HABITACIÓN " + codHabActual);
                        System.out.println(
                                "Objetivo: acumular puntos para pasar a la habitación " + codHabitacionDestino);

                        for (int i = 1; i <= listaDesafios.longitud(); i++) {
                            Desafio des = (Desafio) listaDesafios.recuperar(i);
                            System.out.println(" - [Puntaje: " + des.getPuntaje() + "] Nombre: " + des.getNombre()
                                    + " | Tipo: " + des.getTipo());
                        }

                        log.registrar("Se consultaron los posibles desafíos para el equipo " + nombreEquipo +
                                " en la habitación " + codHabActual + " hacia la habitación " + codHabitacionDestino);
                    }
                }
            }
        }
    }

    public static boolean jugarDesafio(TablaHashEquipos tablaEquipos, String nombreEquipo, int puntajeDesafio) {
        boolean exito = false;

        // buscamos el equipo en la Tabla Hash
        Equipo eq = tablaEquipos.buscar(nombreEquipo);

        if (eq == null) {
            System.out.println("ERROR: El equipo '" + nombreEquipo + "' no existe.");
            log.registrar("Error en jugarDesafio: El equipo '" + nombreEquipo + "' no existe");
        } else {
            // obtenemos la habitacion actual desde el objeto Equipo
            Habitacion habActual = eq.getHabitacionActual();

            if (habActual == null) {
                System.out.println("error, el equipo no se encuentra en ninguna habitacion");
                log.registrar("Error en jugarDesafio: El equipo '" + nombreEquipo
                        + "' no se encuentra en ninguna habitación");
            } else {
                // obtenemos el AVL de desafios de la habitacion
                ArbolAVL avlDesafios = habActual.getDesafios();

                // creamos un desafio con la clave (puntaje) para buscarlo en el AVL
                Desafio desafioBuscado = new Desafio(puntajeDesafio);
                Desafio desafioEncontrado = (Desafio) avlDesafios.recuperar(desafioBuscado);

                if (desafioEncontrado == null) {
                    System.out.println(
                            "error, no existe un desafío con puntaje " + puntajeDesafio + " en la habitación actual.");
                    log.registrar("Error en jugarDesafio: No existe desafío con puntaje " + puntajeDesafio +
                            " en la habitación " + habActual.getCodigo());
                } else {
                    // si el desafio existe sumamos los puntos al equipo
                    int puntos = desafioEncontrado.getPuntaje();

                    // actualizamos tanto el acumulado global como el de la habitación actual
                    eq.setPuntajeAcumulado(eq.getPuntajeAcumulado() + puntos);
                    eq.setPuntajeActualHab(eq.getPuntajeActualHab() + puntos);

                    System.out.println("Desafío resuelto exitosamente por " + nombreEquipo);
                    System.out.println("Se sumaron " + puntos + " puntos. Puntaje acumulado en esta habitación: "
                            + eq.getPuntajeActualHab());

                    log.registrar("El equipo " + nombreEquipo + " resolvió un desafío de " + puntos +
                            " puntos en la habitación " + habActual.getCodigo());

                    exito = true;
                }
            }
        }

        return exito;
    }

    public static boolean cambiarDeHabitacion(TablaHashEquipos tablaEquipos, Grafo grafoMapa, String nombreEquipo,
            Habitacion habDestino, int puntajeRequerido) {
        boolean exito = false;

        // buscamos el equipo en la tabla hash por su nombre
        Equipo eq = tablaEquipos.buscar(nombreEquipo);

        if (eq == null) {
            System.out.println("error, el equipo '" + nombreEquipo + "' no existe.");
            log.registrar("Error en cambiarDeHabitacion: El equipo '" + nombreEquipo + "' no existe");
        } else if (habDestino == null) {
            System.out.println("error, la habitación de destino ingresada no es válida.");
            log.registrar("Error en cambiarDeHabitacion: Habitación de destino no válida");
        } else {
            // obtenemos la habitación actual donde se encuentra el equipo
            Habitacion habOrigen = eq.getHabitacionActual();

            if (habOrigen == null) {
                System.out.println("Eror, el equipo no tiene asignada una habitación actual.");
                log.registrar(
                        "Error en cambiarDeHabitacion: El equipo " + nombreEquipo + " no tiene habitación asignada");
            } else {
                int codOrigen = habOrigen.getCodigo();
                int codDestino = habDestino.getCodigo();

                // verificamos si la habitacion destino es contigua
                boolean esContigua = grafoMapa.existeCamino(codOrigen, codDestino);

                // verificamos si el puntaje acumulado en la habitación actual alcanza
                boolean puntajeSuficiente = (eq.getPuntajeActualHab() >= puntajeRequerido);

                if (!esContigua) {
                    System.out.println("Rechazado. La habitación " + codDestino +
                            " no es contigua a la ubicación actual (" + codOrigen + ").");
                    log.registrar("Rechazado cambiarDeHabitacion: Habitación " + codDestino +
                            " no es contigua a " + codOrigen + " para el equipo " + nombreEquipo);
                } else if (!puntajeSuficiente) {
                    System.out.println("Rechazado. Puntaje insuficiente en la habitación actual ("
                            + eq.getPuntajeActualHab() + " / " + puntajeRequerido + " requeridos).");
                    log.registrar("Rechazado cambiarDeHabitacion: Puntaje insuficiente (" + eq.getPuntajeActualHab() +
                            "/" + puntajeRequerido + ") para el equipo " + nombreEquipo);
                } else {
                    // si cumple ambas condiciones actualizamos los datos del equipo
                    eq.setHabitacionActual(habDestino);

                    // reiniciamos el puntaje acumulado de la habitación para la nueva hab
                    eq.setPuntajeActualHab(0);

                    System.out.println(
                            "Exito, el equipo " + nombreEquipo + " avanzó a la habitación " + codDestino + ".");
                    log.registrar("El equipo " + nombreEquipo + " cambió exitosamente de la habitación " +
                            codOrigen + " a la habitación " + codDestino);

                    exito = true;
                }
            }
        }

        return exito;
    }

    public static boolean puedeSalir(TablaHashEquipos tablaEquipos, String nombreEquipo, int puntajeParaGanar) {
        boolean puedeGanar = false;

        // buscamos el equipo en la tabla hash por su nombre
        Equipo eq = tablaEquipos.buscar(nombreEquipo);

        if (eq == null) {
            System.out.println("error. El equipo '" + nombreEquipo + "' no existe.");
            log.registrar("Error en puedeSalir: El equipo '" + nombreEquipo + "' no existe");
        } else {
            // obtenemos la habitacion actual donde se encuentra el equipo
            Habitacion habActual = eq.getHabitacionActual();

            if (habActual == null) {
                System.out.println("error. El equipo no se encuentra en ninguna habitación.");
                log.registrar(
                        "Error en puedeSalir: El equipo " + nombreEquipo + " no se encuentra en ninguna habitación");
            } else {
                // verificamos si la habitación actual cuenta con salida al exterior
                boolean tieneSalida = habActual.tieneSalida();

                // verificamos si el puntaje total acumulado es mayor o igual el requerido para
                // ganar
                boolean puntajeSuficiente = eq.getPuntajeAcumulado() >= puntajeParaGanar;

                if (!tieneSalida) {
                    System.out.println("No puede salir. La habitación actual (" + habActual.getCodigo()
                            + ") no tiene salida al exterior.");
                    log.registrar("Consulta puedeSalir, La habitación " + habActual.getCodigo() +
                            " no tiene salida para el equipo " + nombreEquipo);
                } else if (!puntajeSuficiente) {
                    System.out.println("No puede salir. Puntaje total insuficiente ("
                            + eq.getPuntajeAcumulado() + " / " + puntajeParaGanar + " requeridos).");
                    log.registrar("Consulta puedeSalir: Puntaje total insuficiente (" + eq.getPuntajeAcumulado() +
                            "/" + puntajeParaGanar + ") para el equipo " + nombreEquipo);
                } else {
                    System.out.println(
                            "¡exito! El equipo " + nombreEquipo + " cumple los requisitos y puede salir del juego.");
                    log.registrar(
                            "¡exito! El equipo " + nombreEquipo + " cumplió las condiciones y puede salir del juego");

                    puedeGanar = true;
                }
            }
        }

        return puedeGanar;
    }

}