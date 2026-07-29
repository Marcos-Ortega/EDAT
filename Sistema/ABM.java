package Sistema;

import java.util.Scanner;

import GrafoHabitaciones.estructurasAuxiliares.*;
import GrafoHabitaciones.Grafo;
import clases.*;

public class ABM {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArbolAVL avlHabitaciones = new ArbolAVL();
        Grafo grafo = new Grafo();
        TablaHashEquipos tablaEquipos = new TablaHashEquipos();
        TablaDesafioResueltos desResueltos = new TablaDesafioResueltos(); //hacer cuando terminen desafios

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
                    menuHabitaciones(sc, avlHabitaciones, grafo);
                    break;

                case 2:
                    menuDesafios(sc, avlHabitaciones);
                    break;

                case 3:
                    menuEquipos(sc, tablaEquipos, avlHabitaciones);
                    break;

                case 4:
                    //Consulta habitaciones
                    break;

                case 5:
                    //Consulta desafios
                    break;

                case 6:
                    //Consulta equipos
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
    public static void menuHabitaciones(Scanner sc, ArbolAVL avl, Grafo grafo) {

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
                    bajaHabitacion(sc, avl, grafo);
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
            grafo.insertarHabitacion(codigo);
            System.out.println("Habitacion agregada exitosamente.");
            log.registrar("Se crea la habitacion "+ codigo);
        } else {
            System.out.println("Ya existe una habitacion con ese codigo.");
            log.registrar("Error al crear habitacion, ya existe "+codigo);
        }
    }

    public static void bajaHabitacion(Scanner sc, ArbolAVL avl, Grafo grafo) {

        int codigoBuscar;

        System.out.println("Ingrese el codigo de la habitacion: ");
        codigoBuscar = sc.nextInt();

        Habitacion habActual = (Habitacion) avl.recuperar(new Habitacion(codigoBuscar));

        if (habActual != null) {
            if (habActual.tieneSalida()) {// preguntar con el grupo que pasa ocn una habitacion de entrada
                System.out.println("No se puede eliminar esta habitacion.");
                log.registrar("Error al eliminar habitacion "+ codigoBuscar);
            } else {
                avl.eliminar(habActual);
                grafo.eliminarHabitacion(codigoBuscar);
                log.registrar("Se borro la habitacion "+codigoBuscar);
                System.out.println("Habitacion eliminada correctamente.");
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("No se encontro la habitacion "+codigoBuscar);
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
                        log.registrar("Se modifico el nombre de la habitacion "+codigoBuscar);
                        break;

                    case 2:
                        System.out.println("Ingrese la nueva planta: ");
                        int nuevaPlanta = sc.nextInt();
                        habActual.setPlanta(nuevaPlanta);
                        System.out.println("Planta modificada.");
                        log.registrar("Se modifico la planta de la habitacion "+codigoBuscar);
                        break;

                    case 3:
                        System.out.println("Ingrese los nuevos metros cuadrados: ");
                        int nuevosMetros = sc.nextInt();
                        habActual.setMetros(nuevosMetros);
                        System.out.println("Metros modificados.");
                        log.registrar("Se modificaron los metros de la habitacion "+codigoBuscar);
                        break;

                    case 4:
                        System.out.println("Ingrese si tiene salida (Si/No): ");
                        String tiene = sc.nextLine();

                        if (tiene.equalsIgnoreCase("si")) {
                            habActual.setTieneSalida(true);
                            System.out.println("Se cambio tiene salida.");
                            log.registrar("Se modifico la salida de la habitacion "+codigoBuscar);
                        } else if (tiene.equalsIgnoreCase("no")) {
                            habActual.setTieneSalida(false);
                            System.out.println("Se cambio tiene salida.");
                            log.registrar("Se modifico la salida de la habitacion "+codigoBuscar);
                        } else {
                            System.out.println("No se pudo determinar si tiene salida.");
                            log.registrar("No se pudo modificar la salida de la habitacion "+codigoBuscar);
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
            log.registrar("No se puede modificar habitacion "+codigoBuscar+", no existe la habitacion.");
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
                log.registrar("Se creo el Desafio "+ nombre + " en habitacion " + codigoHab);
            } else {
                System.out.println("Ya existe un desafio con ese puntaje en esta habitacion.");
                log.registrar("Ya existe un desafio con puntaje "+ puntaje + " en habitacion " + codigoHab);
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitacion "+ codigoHab+" no existe");
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
                log.registrar("Se elimino el desafio con puntaje de "+ puntajeBuscar +" de la habitacion "+codigoHab );
            } else {
                System.out.println("El desafio no existe en esta habitacion.");
                log.registrar("Desafio con puntaje "+puntajeBuscar+" no existe en la habitacion "+codigoHab);
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitacion "+ codigoHab+" no existe");
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
                            log.registrar("Se cambio el nombre del Desafio con puntaje "+ puntajeBuscar + " de la habitacion "+codigoHab);
                            break;

                        case 2:
                            System.out.println("Ingrese el nuevo tipo: ");
                            String nuevoTipo = sc.nextLine();
                            desafioActual.setTipo(nuevoTipo);
                            System.out.println("Tipo modificado.");
                            log.registrar("Se cambio el tipo del Desafio con puntaje "+ puntajeBuscar + " de la habitacion "+codigoHab);
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
                log.registrar("Desafio con puntaje "+puntajeBuscar+" no existe en la habitacion "+codigoHab);
            }

        } else {
            System.out.println("La habitacion no existe.");
            log.registrar("La habitacion "+ codigoHab+" no existe");
        }
    }

    //operaciones desafio Punto 4


    

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
                log.registrar("Se creo correctamente el equipo "+nombre);
            } else {
                System.out.println("Ya existe un equipo con ese nombre.");
                log.registrar("Error al crear equipo "+nombre+". Ya existe equipo con ese nombre");
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
            tabla.eliminar(equipoActual);//hacer este metodo
            System.out.println("Equipo eliminado correctamente.");
            log.registrar("Se elimino el equipo "+nombreBuscar);
        } else {
            System.out.println("El equipo no existe.");
            log.registrar("No existe el equipo "+nombreBuscar+" para eliminar");
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
                        log.registrar("Cambio el puntaje exigido del equipo "+nombreBuscar);
                        break;

                    case 2:
                        System.out.println("Ingrese el codigo de la nueva habitacion: ");
                        int nuevoCodigoHab = sc.nextInt();

                        Habitacion nuevaHab = (Habitacion) avl.recuperar(new Habitacion(nuevoCodigoHab));

                        if (nuevaHab != null) {
                            equipoActual.setHabitacionActual(nuevaHab);
                            System.out.println("Habitacion actual modificada.");
                            log.registrar("Se cambio la Habitacion actual del equipo "+nombreBuscar);
                        } else {
                            System.out.println("La habitacion no existe.");
                            log.registrar("Error al cambiar habitacion actual del equipo "+nombreBuscar+". Habitacion no existe");
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
            log.registrar("El equipo "+nombreBuscar+" a modificar no existe.");
        }
    }

}