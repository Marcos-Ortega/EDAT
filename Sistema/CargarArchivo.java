package Sistema;
import GrafoHabitaciones.Grafo;
import GrafoHabitaciones.estructurasAuxiliares.ArbolAVL;
import clases.Desafio;
import clases.Equipo;
import clases.Habitacion;
import clases.TablaHashEquipos;
import clases.TablaDesafioResueltos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class CargarArchivo {


    public static void cargarArchivo(ArbolAVL avlHabitaciones, Grafo grafo, TablaHashEquipos tablaEquipos, TablaDesafioResueltos desResueltos) {

        String nombreArchivoEntrada = "Sistema.txt";// guardo el nombre del archivo en un string
        // gestion de errores
        try {
            FileReader lectorArchivo = new FileReader(nombreArchivoEntrada); // leo archivos
            BufferedReader lectura = new BufferedReader(lectorArchivo);
            String linea = ""; // creo variable linea vacia, para leer cada linea del txt
            while ((linea = lectura.readLine()) != null) { // se recorre mientras haya una linea
                if(!linea.isEmpty()){
                    String primerCaracter = linea.substring(0, 1);
                    String[] bloque = linea.split(";"); // variable para leer cada cadena de linea y separarlo con el ;
                    if (primerCaracter.equals("H")) {
                        if (bloque.length == 6) { // valido si la cadena del bloque tiene todos los datos
                            int codigo = Integer.parseInt(bloque[1]);// uso Integer.parseInt para cambiar el tipo de dato
                            String nombre = bloque[2];
                            int planta = Integer.parseInt(bloque[3]); // uso Integer.parseInt para cambiar el tipo de dato
                            int metros = Integer.parseInt(bloque[4]); // uso Integer.parseInt para cambiar el tipo de dato
                            boolean tieneSalida = Boolean.parseBoolean(bloque[5]); // uso Boolean.parseBoolean para cambiar
                                                                                   // el tipo de dato
    
                            // Almaceno la habitacion
                            avlHabitaciones.insertar(new Habitacion(codigo, nombre, planta, metros, tieneSalida));
                            grafo.insertarVertice(codigo);
                        }
                    } else if (primerCaracter.equals("D")) {
                        if (bloque.length == 5) {
                            int puntaje = Integer.parseInt(bloque[1]);
                            int codigoHab = Integer.parseInt(bloque[2]);
                            String nombre = bloque[3];
                            String tipo = bloque[4];
                            //busco la habitacion
                            Habitacion hab = (Habitacion) avlHabitaciones.recuperar(new Habitacion(codigoHab));//creo nueva habitacion para buscarla en el arbol con el compareTo. Como buscar devuelve un comparable, yo lo casteo a un Habitacion
                            if (hab != null) {
                                hab.getDesafios().insertar(new Desafio(puntaje, nombre, tipo));
                            }
                        }
                    } else if (primerCaracter.equals("P")) {
                        String siguiente = linea.substring(2); // saco P:
                        String[] datosArco = siguiente.split(";");
                        int codigoHab1 = Integer.parseInt(datosArco[0]);
                        int codigoHab2 = Integer.parseInt(datosArco[1]);
                        int puntajeExigido = Integer.parseInt(datosArco[2]);
    
                        grafo.insertarArco(codigoHab1, codigoHab2, puntajeExigido);
                    } else if (primerCaracter.equals("E")) {
                        if (bloque.length >= 6) {
                            String nombreEquipo = bloque[1];
                            int puntajeExigido = Integer.parseInt(bloque[2]);
                            int puntajeAcumulado = Integer.parseInt(bloque[3]);
                            int codigoHabActual = Integer.parseInt(bloque[4]);
                            int puntajeActualHab = Integer.parseInt(bloque[5]);
                            //busco la habitacion
                            Habitacion habActual = (Habitacion) avlHabitaciones.recuperar(new Habitacion(codigoHabActual));//creo nueva habitacion para buscarla en el arbol con el compareTo. Como buscar devuelve un comparable, yo lo casteo a un Habitacion
    
                            Equipo nuevoEquipo = new Equipo(nombreEquipo, puntajeExigido, puntajeAcumulado, habActual, puntajeActualHab);
                            tablaEquipos.insertar(nuevoEquipo);
    
                            if (bloque.length == 7) {
                                String listaDesafios = bloque[6];
                                int pos = 0; //pos marca desde donde sigo buscando el proximo "(" en cada vuelta del while
                                while (listaDesafios.indexOf("(", pos) != -1) {
    
                                    int inicioParentesis = listaDesafios.indexOf("(", pos);
                                    int finParentesis = listaDesafios.indexOf(")", inicioParentesis);
    
                                    String grupo = listaDesafios.substring(inicioParentesis + 1, finParentesis);//guardo el formato de 1:20,30 sin los paretenssus
    
                                    String[] partesGrupo = grupo.split(":");//primero esta el codigo de la habtacion y despues se separa con : para los puntajes de los desafios, por eso corto con :
                                    int codigoHabDesafio = Integer.parseInt(partesGrupo[0]); //guardo el codigo de la habitacion
    
                                    String[] puntajes = partesGrupo[1].split(",");//los puntajes se separan por ,
                                    //busco la habitacion
                                    Habitacion habDesafio = (Habitacion) avlHabitaciones.recuperar(new Habitacion(codigoHabDesafio));////creo nueva habitacion para buscarla en el arbol con el compareTo. Como buscar devuelve un comparable, yo lo casteo a un Habitacion
    
                                    if (habDesafio != null) {
                                        // recorro cada puntaje de la lista
                                        for (int i = 0; i < puntajes.length; i++) {
                                            int puntaje = Integer.parseInt(puntajes[i]);
                                            //busco el desafio resuelto dentro de la habitacion
                                            Desafio desafioResuelto = (Desafio) habDesafio.getDesafios().recuperar(new Desafio(puntaje)); //hacer cuando terminen desafios
    
                                            if (desafioResuelto != null) {
                                                desResueltos.agregar(nombreEquipo, desafioResuelto);
                                            }
                                        }
                                    }
    
                                    pos = finParentesis + 1;//avanzo el pos, una posicion mas del ultimo parentesis
                                }
                            }
                        }
                    }
                }
            }
            lectorArchivo.close();
            lectura.close();
            System.out.println("Carga Finalizada." );
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nEl archivo no existe.");
        } catch (IOException error) {
            System.out.println("Error al leer el archivo " + error.getMessage());
        }
    }
}