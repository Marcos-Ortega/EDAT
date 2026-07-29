package menu;

import GrafoHabitaciones.Grafo;
import GrafoHabitaciones.estructurasAuxiliares.ArbolAVL;
import GrafoHabitaciones.estructurasAuxiliares.Lista;
import clases.Desafio;
import clases.Equipo;
import clases.Habitacion;
import clases.TablaHashEquipos;

public class menu {
    public static void mostrarInfoEquipo(TablaHashEquipos tablaEquipos, String nombreEquipo) {
    Equipo eq = tablaEquipos.buscar(nombreEquipo);
    
    if (eq != null) {
        System.out.println(eq.toString());
    } else {
        System.out.println("ERROR: El equipo '" + nombreEquipo + "' no se encuentra registrado.");
    }
}


public static void posiblesDesafios(TablaHashEquipos tablaEquipos, Grafo grafoMapa, String nombreEquipo, int codHabitacionDestino) {
    //buscamos el equipo en la tabla hash por su nombre
    Equipo eq = tablaEquipos.buscar(nombreEquipo);

    if (eq == null) {
        System.out.println("ERROR: El equipo '" + nombreEquipo + "' no existe.");
    } else {
        // Obtenemos directamente la Habitacion actual desde el objeto Equipo
        Habitacion habActual = eq.getHabitacionActual();

        if (habActual == null) {
            System.out.println("ERROR: El equipo no tiene una habitación asignada actualmente.");
        } else {
            // Obtenemos el código numérico de la habitación actual
            int codHabActual = habActual.getCodigo();

            //grafoMapa es la instancia de la clase Grafo que contiene toda la estructura de las
            //habitaciones interconectadas del Escape House (el mapa completo del juego).
            // verificamos la conexion en el Grafo pasando los códigos de las habitaciones
            if (!grafoMapa.existeCamino(codHabActual, codHabitacionDestino)) {
                System.out.println("ACLARACIÓN: La habitación " + codHabitacionDestino + 
                                   " NO es adyacente a la ubicación actual (" + codHabActual + ").");
            } else {
                //obtenemos el AVL de desafíos directamente desde la habitación actual
                ArbolAVL avlDesafios = habActual.getDesafios();

                if (avlDesafios == null || avlDesafios.esVacio()) {
                    System.out.println("La habitación " + codHabActual + " no tiene desafíos disponibles.");
                } else {
                    //listamos los desafíos ordenados por puntaje
                    Lista listaDesafios = avlDesafios.listar();

                    System.out.println("\n=== DESAFÍOS DISPONIBLES EN HABITACIÓN " + codHabActual + " ===");
                    System.out.println("Objetivo: acumular puntos para pasar a la habitación " + codHabitacionDestino);

                    for (int i = 1; i <= listaDesafios.longitud(); i++) {
                        Desafio des = (Desafio) listaDesafios.recuperar(i);
                        System.out.println(" - [Puntaje: " + des.getPuntaje() + "] Nombre: " + des.getNombre() + " | Tipo: " + des.getTipo());
                    }
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
    } else {
        // obtenemos la habitación actual desde el objeto Equipo
        Habitacion habActual = eq.getHabitacionActual();

        if (habActual == null) {
            System.out.println("ERROR: El equipo no se encuentra en ninguna habitación.");
        } else {
            // obtenemos el AVL de desafíos de la habitación
            ArbolAVL avlDesafios = habActual.getDesafios();

            // creamos un desafío 'dummy' con la clave (puntaje) para buscarlo en el AVL
            Desafio desafioBuscado = new Desafio(puntajeDesafio);
            Desafio desafioEncontrado = (Desafio) avlDesafios.recuperar(desafioBuscado);

            if (desafioEncontrado == null) {
                System.out.println("ERROR: No existe un desafío con puntaje " + puntajeDesafio + " en la habitación actual.");
            } else {
                // si el desafío existe sumamos los puntos al equipo
                int puntos = desafioEncontrado.getPuntaje();

                // Actualizamos tanto el acumulado global como el de la habitación actual
                eq.setPuntajeAcumulado(eq.getPuntajeAcumulado() + puntos);
                eq.setPuntajeActualHab(eq.getPuntajeActualHab() + puntos);

                System.out.println("¡Desafío resuelto exitosamente por " + nombreEquipo + "!");
                System.out.println("Se sumaron " + puntos + " puntos. Puntaje acumulado en esta habitación: " + eq.getPuntajeActualHab());

                exito = true;
            }
        }
    }

    return exito;
}
    
}
