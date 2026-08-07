package Sistema;

import java.util.Scanner;

import clases.*;
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
        log.registrar("--- ESTADO DEL SISTEMA AL FINALIZAR LA CARGA INICIAL ---\n"
                + avlHabitaciones + "\n" + grafo + "\n" + tablaEquipos + "\n" + desResueltos);
        do {
            System.out.println("----- MENU -----\n");
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
                    menuConsultasEquipos(sc, tablaEquipos, desResueltos, grafo, avlHabitaciones);
                    break;

                case 7:
                    System.out.println("---Arbol De Habitaciones--- \n");
                    System.out.println(avlHabitaciones.toStringBonito());
                    System.out.println("Informacion De Habitaciones \n");
                    System.out.println(avlHabitaciones);
                    System.out.println(grafo);
                    System.out.println(tablaEquipos);
                    System.out.println(desResueltos);
                    log.registrar("Se mostro el sistema completo.");
                    break;

                case 0:
                    System.out.println("Saliendo..");
                    log.registrar("--- ESTADO DEL SISTEMA AL FINALIZAR LA EJECUCION ---\n"
                            + avlHabitaciones + "\n" + grafo + "\n" + tablaEquipos + "\n" + desResueltos);
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
            System.out.println("4. Agregar una puerta a otra Habitacion");
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
                case 4:
                    agregarPuertaHabitacion(sc, avl, grafo);
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
            } else if (habActual.tieneSalida()) {// preguntar con el grupo que pasa ocn una habitacion de entrada
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

    public static void agregarPuertaHabitacion(Scanner sc, ArbolAVL avl, Grafo grafo) {
        int codigoBuscar1, codigoBuscar2, puntaje;

        System.out.println("Ingrese el codigo de la primera habitacion donde esta la puerta: ");
        codigoBuscar1 = sc.nextInt();
        System.out.println("Ingrese el codigo de la segunda habitacion donde esta la puerta: ");
        codigoBuscar2 = sc.nextInt();

        Habitacion habOrigen = (Habitacion) avl.recuperar(new Habitacion(codigoBuscar1));
        Habitacion habDestino = (Habitacion) avl.recuperar(new Habitacion(codigoBuscar2));
        if (habOrigen == null || habDestino == null) {
            System.out.println("Error, las habitaciones no existen.");
            log.registrar("Error al agregar una puerta de una habitacion, habitacion no existe");
        } else if (codigoBuscar1 == codigoBuscar2) {
            System.out.println("Error, las habitaciones son las mismas.");
            log.registrar("Error al agregar una puerta de una habitacion, habitacion no existe");
        }else{
            System.out.println("Ingrese el puntaje requerido para pasar de habitacion: ");
            puntaje=sc.nextInt();
            if(grafo.insertarArco(codigoBuscar1, codigoBuscar2, puntaje)){
                System.out.println("La puerta se agrego exitosamente.");
                log.registrar("Se agrego una puerta para la habitacion "+codigoBuscar1+" y "+codigoBuscar2 +"con un puntaje requerido de "+puntaje);
            }else{
                System.out.println("Error al agregar la puerta, ya existe una puerta que conecta esas habitaciones.");
                log.registrar("Error al agregar una puerta para la habitacion "+codigoBuscar1+" y "+codigoBuscar2 +"con un puntaje requerido de "+puntaje+". Ya existe una puerta para esas habitaciones");
            }
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

    // punto 1
    public static void mostrarHabitacion(Scanner sc, ArbolAVL avl) {
        int codigo;
        System.out.println("ingrese el codigo de la habitacion: ");
        codigo = sc.nextInt();
        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigo));
        // si al recuperar la habitacion esta existe, la mostramos si no, informamos que
        // no existe
        if (habActual != null) {
            System.out.println(habActual);
            log.registrar("habitacion con el codigo: " + codigo);
        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("No se pudo mostrar, la habitacion " + codigo + " no existe");
        }
    }

    // punto 2
    public static void habitacionesContiguas(Scanner sc, ArbolAVL avl, Grafo grafo) {

        int codigo;

        System.out.println("Ingrese el codigo de la habitacion: ");
        codigo = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigo));
        // misma verificacion si existe habitacion
        if (habActual != null) {
            NodoAdy auxAdy = grafo.getAdyacentes(codigo);
            // verificamos que la habitacion tenga adyacentes
            if (auxAdy != null) {
                // si tiene adyacentes los recorremos y mostramos
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

    // punto 3
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
        // verificamos que ambas habitaciones existan
        if (hab1 != null && hab2 != null) {
            // llamamos al metodo de grafo que verifica si hay camino entre esas dos
            // habitaciones y si se puede hacer con ese puntaje
            boolean existe = grafo.sePuedeLlegar(cod1, cod2, k);
            // si el metodo de grafo devuelve verdaderole decimos que si puede llegar, de lo
            // contrario le decimos que no es posible
            if (existe) {
                System.out.println(
                        "es posible llegar de la habitacion de origen a la habitacion de destino con la cantidad de puntos ingresados.");
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

    // punto 4
    public static void minimoPuntaje(Scanner sc, Grafo grafo, ArbolAVL avl) {
        int cod1, cod2;

        System.out.println("Ingrese el codigo de la habitacion de origen: ");
        cod1 = sc.nextInt();

        System.out.println("Ingrese el codigo de la habitacion de destino: ");
        cod2 = sc.nextInt();

        Habitacion hab1 = (Habitacion) avl.recuperar(new Habitacion(cod1));
        Habitacion hab2 = (Habitacion) avl.recuperar(new Habitacion(cod2));
        // verificamos que las habitaciones existan
        if (hab1 != null && hab2 != null) {
            // creamos arreglo y lista para ir guardando lo que le vamos a mostrar al
            // usuario
            int[] mejorPuntaje = new int[1];
            Lista[] mejorCamino = new Lista[1];
            // llamamos al metodo de grafo que verifica si se puede llegar de una habitacion
            // a la otra y cual es el mejor camino y mininmo puntaje
            boolean exito = grafo.minimoPuntaje(cod1, cod2, mejorPuntaje, mejorCamino);
            // en el caso de poder ir de una habitacion a la otra mostramos el mejor camino
            // y minimo puntaje, de lo contrario indicamos que no hay un camino posible
            if (exito) {
                System.out.println("El puntaje minimo es: " + mejorPuntaje[0]);
                System.out.println("El camino es: " + mejorCamino[0]);
                log.registrar("Se mostro el puntaje minimo y el camino entre las habitaciones " + cod1 + " y " + cod2);
            } else {
                System.out.println("No existe camino entre esas dos habitaciones.");
                log.registrar("No existe camino entre las habitaciones" + cod1 + " y " + cod2);
            }

        } else {
            System.out.println("Una de las dos habitaciones no existe.");
            log.registrar("No se pudo evaluar minimoPuntaje, alguna habitacion no existe");
        }
    }

    // punto 5
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
        // verifica que existan las 3 habitaciones
        if (hab1 != null && hab2 != null && hab3 != null && cod2 != cod3) {
            // llama al metodo de grafos que verifica el camino y lo devuelve en una lista
            Lista caminos = grafo.sinPasarPor(cod1, cod2, cod3, p);
            // si la lista no esta vacia quiere decir que si existe un camino o varios
            // caminos entonces lo mostramos, de lo contario, decimos que no existe ningun
            // camino
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
        // verifica que la habitacion ingresada exista
        if (habActual != null) {
            System.out.println("Ingrese el puntaje del desafio a mostrar: ");
            puntajeBuscar = sc.nextInt();
            Desafio desafioActual = (Desafio) habActual.getDesafios().recuperar(new Desafio(puntajeBuscar));
            // verifica que en esa habitacion exista el desafio con puntaje ingresado
            if (desafioActual != null) {
                // si existe lo muestra
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
        // nos traemos los desafios que tiene resueltos ese equipo de
        // TablaDesafioResuelto donde tenemos hechas las relaciones de los desafios que
        // resolveio cada equipo
        Lista resueltos = desResueltos.obtenerResueltos(nombreEquipo);
        // si la lista no esta vacia muestra los desafio resueltos del equipo
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
        // verificamos que la habitacion exista
        if (hab == null) {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitaccion ingresada por el ususario no existe");
        } else {

            Desafio desafio = (Desafio) hab.getDesafios().recuperar(new Desafio(puntaje));
            // verificamos que el desafio exista en esa habitacion
            if (desafio == null) {
                System.out.println("Ese desafio no existe en esa habitacion.");
                log.registrar("El desafio ingresado por el ususario no existe en la habitacion" + hab);
            } else {
                // llamamos al metodo de TablaDesafioResuelto para ver si el desafio esta entre
                // los resueltos por ese equipo
                boolean resuelto = desResueltos.yaResuelto(nombreEquipo, desafio);
                // indica si el desafio se resolvio o no
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
        // verifica que la habitacion exista
        if (habActual != null) {

            System.out.println("Ingrese el puntaje minimo (a): ");
            a = sc.nextInt();

            System.out.println("Ingrese el puntaje maximo (b): ");
            b = sc.nextInt();
            sc.nextLine();

            System.out.println("Ingrese el tipo de desafio a buscar: ");
            tipo = sc.nextLine();
            // de la lista de desafios que tiene esa habitacion utilizamos el metodo de
            // lista que hace una sublista que se encuentre el el rango indicado
            Lista enRango = habActual.getDesafios().listarRango(new Desafio(a), new Desafio(b));
            boolean encontroAlguno = false;
            // recorre toda la lista de en rango
            for (int i = 1; i <= enRango.longitud(); i++) {
                Desafio d = (Desafio) enRango.recuperar(i);
                // se fija si el desafio en el que estamos es del tipo indicado
                if (d.getTipo().equalsIgnoreCase(tipo)) {
                    // si el desafio es del tipo ingresado lo muestra
                    System.out.println(d);
                    encontroAlguno = true;
                    log.registrar("Se muestran los desafios del tipo ingresado que estan dentro del rango ingresado");
                }
            }
            // no se encuentro ningun desafio de ese tipo en el rango
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
    // MENU CONSULTAS EQUIPOS

    public static void menuConsultasEquipos(Scanner sc, TablaHashEquipos tablaEquipos,
            TablaDesafioResueltos desResueltos, Grafo grafoMapa,
            ArbolAVL avlHabitaciones) {

        int op;

        do {

            System.out.println("\n----- CONSULTAS EQUIPOS -----");
            System.out.println("1. Mostrar Info Equipo");
            System.out.println("2. Ver Posibles Desafios");
            System.out.println("3. Jugar Desafio");
            System.out.println("4. Cambiar de Habitacion");
            System.out.println("5. Verificar si Puede Salir");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {

                case 1:
                    System.out.println("Ingrese el nombre del equipo: ");
                    String nombreEq = sc.nextLine();
                    mostrarInfoEquipo(tablaEquipos, nombreEq);
                    break;

                case 2:
                    System.out.println("Ingrese el nombre del equipo: ");
                    String nombreEquip = sc.nextLine();
                    System.out.println("Ingrese el codigo de la habitacion destino: ");
                    int codDestinoPosibles = sc.nextInt();
                    posiblesDesafios(tablaEquipos, desResueltos, grafoMapa, nombreEquip, codDestinoPosibles);
                    break;

                case 3:
                    System.out.print("Ingrese el nombre del equipo: ");
                    String nombreEquipo = sc.nextLine();

                    // buscamos el equipo antes de pedir la habitacion para poder mostrarle
                    // al usuario dónde está ubicado actualmente y que no se equivoque
                    Equipo equipoConsultado = tablaEquipos.buscar(nombreEquipo);

                    if (equipoConsultado == null) {
                        System.out.println("No existe un equipo con ese nombre.");
                    } else if (equipoConsultado.getHabitacionActual() == null) {
                        System.out.println("El equipo no tiene una habitación asignada.");
                    } else {
                        System.out.println("El equipo se encuentra actualmente en la habitación: "
                                + equipoConsultado.getHabitacionActual().getCodigo()
                                + " - " + equipoConsultado.getHabitacionActual().getNombre());

                        System.out.print("Ingrese el código de la habitación donde va a resolver el desafío: ");
                        int codHabitacion = Integer.parseInt(sc.nextLine());

                        System.out.print("Ingrese el puntaje del desafío que quiere resolver: ");
                        int puntajeDesafio = Integer.parseInt(sc.nextLine());

                        boolean resultado = jugarDesafio(tablaEquipos, desResueltos,
                                nombreEquipo, codHabitacion, puntajeDesafio);
                        if (resultado) {
                            System.out.println("Desafío resuelto exitosamente por " + nombreEquipo);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Ingrese el nombre del equipo: ");
                    String eqCambiar = sc.nextLine();
                    System.out.println("Ingrese el codigo de la habitacion destino: ");
                    int codHabDestino = sc.nextInt();

                    // recuperamos el objeto habitacion desde el avl para pasarlo al metodo
                    Habitacion habDestino = (Habitacion) avlHabitaciones.recuperar(new Habitacion(codHabDestino));
                    if (habDestino != null) {
                        cambiarDeHabitacion(tablaEquipos, grafoMapa, eqCambiar, habDestino);
                    } else {
                        System.out.println("No existe la habitacion ingresada");
                        log.registrar("Cambio el puntaje exigido del equipo " + codHabDestino);
                    }
                    break;

                case 5:
                    System.out.println("Ingrese el nombre del equipo: ");
                    String eqSalir = sc.nextLine();
                    System.out.println("Ingrese el puntaje total necesario para ganar: ");
                    int puntajeGanar = sc.nextInt();
                    puedeSalir(tablaEquipos, eqSalir, puntajeGanar);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
                    log.registrar("Opcion incorrecta en menu consultas equipos");
            }

        } while (op != 0);

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

    private static int obtenerPuntajeArco(Grafo grafoMapa, Object origen, Object destino) {
        int puntaje = -1;
        boolean encontrado = false;
        NodoAdy auxAdy = grafoMapa.getAdyacentes(origen);
        while (auxAdy != null && !encontrado) {
            if (auxAdy.getVertice().getElem().equals(destino)) {
                puntaje = auxAdy.getEtiqueta();
                encontrado = true;
            } else {
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return puntaje;
    }

    public static void posiblesDesafios(TablaHashEquipos tablaEquipos, TablaDesafioResueltos tablaResueltos,
            Grafo grafoMapa, String nombreEquipo, int codHabitacionDestino) {

        // buscamos el equipo en la tabla hash por su nombre
        Equipo eq = tablaEquipos.buscar(nombreEquipo);

        if (eq == null) {
            System.out.println("Error: El equipo '" + nombreEquipo + "' no existe");
            log.registrar("Error en posiblesDesafios: El equipo '" + nombreEquipo + "' no existe");
        } else {
            // obtenemos la habitación donde está ubicado actualmente el equipo
            Habitacion habActual = eq.getHabitacionActual();

            if (habActual == null) {
                System.out.println("Error: El equipo no tiene una habitación asignada actualmente");
                log.registrar(
                        "Error en posiblesDesafios: El equipo '" + nombreEquipo + "' no tiene habitación asignada");
            } else {
                int codHabActual = habActual.getCodigo();

                // verificamos que la habitación destino sea adyacente a la actual
                if (!grafoMapa.existeArco(codHabActual, codHabitacionDestino)) {
                    System.out.println("La habitación " + codHabitacionDestino +
                            " no es adyacente a la ubicación actual del equipo (" + codHabActual + ").");
                    log.registrar("Consulta posiblesDesafios: La habitación " + codHabitacionDestino +
                            " no es adyacente a la habitación " + codHabActual + " para el equipo " + nombreEquipo);
                } else {
                    // obtenemos el puntaje minimo exigido en el arco entre la habitación actual y
                    // la destino
                    int puntajeMinimoArco = obtenerPuntajeArco(grafoMapa, codHabActual, codHabitacionDestino);

                    // calculamos cuanto puntaje le falta al equipo dentro de esta habitación para
                    // poder pasar
                    int puntajeQueFalta = puntajeMinimoArco - eq.getPuntajeActualHab();

                    // obtenemos el AVL de desafíos de la habitación actual
                    ArbolAVL avlDesafios = habActual.getDesafios();

                    if (avlDesafios == null || avlDesafios.esVacio()) {
                        System.out.println("La habitación " + codHabActual + " no tiene desafíos disponibles.");
                        log.registrar("Consulta posiblesDesafios: La habitación " + codHabActual
                                + " no tiene desafíos disponibles");
                    } else {
                        // listamos los desafios de la habitacion (ordenados por puntaje gracias al AVL)
                        Lista listaDesafios = avlDesafios.listar();
                        boolean hayAlguno = false;

                        System.out.println("\nDESAFÍOS QUE PERMITEN PASAR DE LA HABITACIÓN " + codHabActual +
                                " A LA HABITACIÓN " + codHabitacionDestino);
                        System.out.println("Puntaje mínimo exigido en el arco: " + puntajeMinimoArco);
                        System.out.println(
                                "Puntaje ya acumulado por el equipo en esta habitación: " + eq.getPuntajeActualHab());

                        // recorremos los desafíos con un while, filtrando los que sirven:
                        // que el equipo no haya resuelto todavía ese desafío y que resolviéndolo solo
                        // (sumado a lo que ya tiene acumulado en la habitación)
                        // sea igual o mayor que el puntaje minimo exigido en el arco
                        int i = 1;
                        while (i <= listaDesafios.longitud()) {
                            Desafio des = (Desafio) listaDesafios.recuperar(i);

                            if (!tablaResueltos.yaResuelto(nombreEquipo, des) && des.getPuntaje() >= puntajeQueFalta) {
                                System.out.println(" - [Puntaje: " + des.getPuntaje() + "] Nombre: " + des.getNombre()
                                        + " | Tipo: " + des.getTipo());
                                hayAlguno = true;
                            }

                            i++;
                        }

                        // si no encontramos ningún desafío que sirva
                        if (!hayAlguno) {
                            System.out.println(
                                    "No hay ningún desafío disponible en esta habitación que, resuelto solo, "
                                            + "le alcance al equipo para pasar a la habitación " + codHabitacionDestino
                                            + ".");
                        }

                        log.registrar("Se consultaron los posibles desafíos para el equipo " + nombreEquipo +
                                " en la habitación " + codHabActual + " hacia la habitación " + codHabitacionDestino);
                    }
                }
            }
        }
    }

    public static boolean jugarDesafio(TablaHashEquipos tablaEquipos, TablaDesafioResueltos tablaResueltos,
            String nombreEquipo, int codHabitacion, int puntajeDesafio) {

        boolean exito = false;

        // buscamos el equipo en la Tabla Hash por su nombre
        Equipo eq = tablaEquipos.buscar(nombreEquipo);

        if (eq == null) {
            System.out.println("ERROR: El equipo '" + nombreEquipo + "' no existe.");
            log.registrar("Error en jugarDesafio: El equipo '" + nombreEquipo + "' no existe");
        } else {
            // obtenemos la habitación donde está ubicado el equipo
            Habitacion habActual = eq.getHabitacionActual();

            if (habActual == null) {
                System.out.println("error, el equipo no se encuentra en ninguna habitacion");
                log.registrar("Error en jugarDesafio: El equipo '" + nombreEquipo
                        + "' no se encuentra en ninguna habitación");
            } else {
                // la habitación que pasan por parámetro tiene que ser la habitacion
                // donde el equipo está ubicado (no puede resolver desafíos de otra habitacion)
                if (habActual.getCodigo() != codHabitacion) {
                    System.out.println("error, el equipo no se encuentra en la habitación " + codHabitacion
                            + ". Está en la habitación " + habActual.getCodigo());
                    log.registrar("Error en jugarDesafio: El equipo '" + nombreEquipo
                            + "' intentó jugar un desafío en la habitación " + codHabitacion
                            + " pero está en la habitación " + habActual.getCodigo());
                } else {
                    // buscamos el desafío dentro del AVL de la habitación actual, por su puntaje
                    // (clave)
                    ArbolAVL avlDesafios = habActual.getDesafios();
                    Desafio desafioBuscado = new Desafio(puntajeDesafio);
                    Desafio desafioEncontrado = (Desafio) avlDesafios.recuperar(desafioBuscado);

                    if (desafioEncontrado == null) {
                        System.out.println(
                                "error, no existe un desafío con puntaje " + puntajeDesafio
                                        + " en la habitación actual.");
                        log.registrar("Error en jugarDesafio: No existe desafío con puntaje " + puntajeDesafio +
                                " en la habitación " + habActual.getCodigo());
                    } else {
                        // como cada equipo puede resolver un desafío una única vez por eso consultamos
                        // yaResuelto pasando el nombre de este equipo puntual.
                        if (tablaResueltos.yaResuelto(nombreEquipo, desafioEncontrado)) {
                            System.out.println("El equipo '" + nombreEquipo + "' ya había resuelto el desafío '"
                                    + desafioEncontrado.getNombre() + "' anteriormente. No otorga puntaje nuevamente.");
                            log.registrar("Aviso en jugarDesafio: el equipo '" + nombreEquipo +
                                    "' ya había resuelto el desafío de puntaje " + puntajeDesafio +
                                    " en la habitación " + habActual.getCodigo());
                        } else {
                            // sumamos los puntos al equipo: al acumulado total del juego y al acumulado
                            // dentro de la habitación actual
                            int puntos = desafioEncontrado.getPuntaje();
                            eq.setPuntajeAcumulado(eq.getPuntajeAcumulado() + puntos);
                            eq.setPuntajeActualHab(eq.getPuntajeActualHab() + puntos);

                            // registramos el desafío como resuelto por este equipo en la tabla
                            tablaResueltos.agregar(nombreEquipo, desafioEncontrado);
                            System.out
                                    .println("Se sumaron " + puntos + " puntos. Puntaje acumulado en esta habitación: "
                                            + eq.getPuntajeActualHab());

                            log.registrar(
                                    "El equipo " + nombreEquipo + " resolvió el desafío '"
                                            + desafioEncontrado.getNombre()
                                            + "' de " + puntos + " puntos en la habitación " + habActual.getCodigo());

                            exito = true;
                        }
                    }
                }
            }
        }
        return exito;
    }

    public static boolean cambiarDeHabitacion(TablaHashEquipos tablaEquipos, Grafo grafoMapa, String nombreEquipo,
            Habitacion habDestino) {
        boolean exito = false;
        // buscamos el equipo en la tabla hash por su nombre
        Equipo eq = tablaEquipos.buscar(nombreEquipo);

        if (eq == null) {
            System.out.println("error, el equipo '" + nombreEquipo + "' no existe.");
            log.registrar("Error en cambiarDeHabitacion: El equipo '" + nombreEquipo + "' no existe");
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
                if(!grafoMapa.existeArco(codOrigen, codDestino)){
                    System.out.println("Rechazado. La habitación " + codDestino +
                            " no es contigua a la ubicación actual (" + codOrigen + ").");
                    log.registrar("Rechazado cambiarDeHabitacion: Habitación " + codDestino +
                            " no es contigua a " + codOrigen + " para el equipo " + nombreEquipo);
                }else{
                    //obtenemos el puntaje que se necesita para pasar de habitacion
                    int puntajeReq = grafoMapa.getEtiqueta(codOrigen, codDestino);
                    // verificamos si el puntaje acumulado en la habitación actual alcanza
                    boolean puntajeSuficiente = (eq.getPuntajeActualHab() >= puntajeReq);
                    if (!puntajeSuficiente) {
                        System.out.println("Rechazado. Puntaje insuficiente en la habitación actual ("
                            + eq.getPuntajeActualHab() + " / " + puntajeReq + " requeridos).");
                        log.registrar("Rechazado cambiarDeHabitacion: Puntaje insuficiente (" + eq.getPuntajeActualHab() +
                            "/" + puntajeReq + ") para el equipo " + nombreEquipo);
                    }
                    else {
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