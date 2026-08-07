package Sistema;
import clases.Desafio;
import clases.Equipo;
import clases.Habitacion;
import estructurasAuxiliares.Conjuntista.ArbolAVL;
import estructurasAuxiliares.Conjuntista.TablaDesafioResueltos;
import estructurasAuxiliares.Conjuntista.TablaHashEquipos;
import estructurasAuxiliares.Grafo.Grafo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


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
                    String primerCaracter = linea.substring(0,1);
                    StringTokenizer tokenizer = new StringTokenizer(linea, ";"); // variable para leer cada cadena de linea y separarlo con el ;
                    if (primerCaracter.equals("H")) {
                        if (tokenizer.countTokens() == 6) { // valido si la cadena del bloque tiene todos los datos
                            tokenizer.nextToken(); ////saco la "H"
                            int codigo = Integer.parseInt(tokenizer.nextToken());// uso Integer.parseInt para cambiar el tipo de dato
                            String nombre = tokenizer.nextToken();
                            int planta = Integer.parseInt(tokenizer.nextToken()); // uso Integer.parseInt para cambiar el tipo de dato
                            int metros = Integer.parseInt(tokenizer.nextToken()); // uso Integer.parseInt para cambiar el tipo de dato
                            boolean tieneSalida = Boolean.parseBoolean(tokenizer.nextToken()); // uso Boolean.parseBoolean para cambiar
                                                                                   // el tipo de dato
    
                            // Almaceno la habitacion
                            avlHabitaciones.insertar(new Habitacion(codigo, nombre, planta, metros, tieneSalida));
                            grafo.insertarVertice(codigo);
                        }
                    } else if (primerCaracter.equals("D")) {
                        if (tokenizer.countTokens() == 5) {
                            tokenizer.nextToken();//saco la "D"
                            int puntaje = Integer.parseInt(tokenizer.nextToken());
                            int codigoHab = Integer.parseInt(tokenizer.nextToken());
                            String nombre = tokenizer.nextToken();
                            String tipo = tokenizer.nextToken();
                            //busco la habitacion
                            Habitacion hab = (Habitacion) avlHabitaciones.recuperar(new Habitacion(codigoHab));//creo nueva habitacion para buscarla en el arbol con el compareTo. Como buscar devuelve un comparable, yo lo casteo a un Habitacion
                            if (hab != null) {
                                hab.getDesafios().insertar(new Desafio(puntaje, nombre, tipo));
                            }
                        }
                    } else if (primerCaracter.equals("P")) {
                        String siguiente = linea.substring(2); // saco P:
                        StringTokenizer tokenizer2 = new StringTokenizer(siguiente,";");
                        int codigoHab1 = Integer.parseInt(tokenizer2.nextToken());
                        int codigoHab2 = Integer.parseInt(tokenizer2.nextToken());
                        int puntajeExigido = Integer.parseInt(tokenizer2.nextToken());
    
                        grafo.insertarArco(codigoHab1, codigoHab2, puntajeExigido);
                    } else if (primerCaracter.equals("E")) {
                        if (tokenizer.countTokens() >= 6) {
                            tokenizer.nextToken();//saco la "E"
                            String nombreEquipo = tokenizer.nextToken();
                            int puntajeExigido = Integer.parseInt(tokenizer.nextToken());
                            int puntajeAcumulado = Integer.parseInt(tokenizer.nextToken());
                            int codigoHabActual = Integer.parseInt(tokenizer.nextToken());
                            int puntajeActualHab = Integer.parseInt(tokenizer.nextToken());
                            //busco la habitacion
                            Habitacion habActual = (Habitacion) avlHabitaciones.recuperar(new Habitacion(codigoHabActual));//creo nueva habitacion para buscarla en el arbol con el compareTo. Como buscar devuelve un comparable, yo lo casteo a un Habitacion
    
                            Equipo nuevoEquipo = new Equipo(nombreEquipo, puntajeExigido, puntajeAcumulado, habActual, puntajeActualHab);
                            tablaEquipos.insertar(nuevoEquipo);
    
                            if (tokenizer.countTokens() == 1) {
                                String listaDesafios = tokenizer.nextToken();
                                int pos = 0; //pos marca desde donde sigo buscando el proximo "(" en cada vuelta del while
                                while (listaDesafios.indexOf("(", pos) != -1) {
    
                                    int inicioParentesis = listaDesafios.indexOf("(", pos);
                                    int finParentesis = listaDesafios.indexOf(")", inicioParentesis);
    
                                    String grupo = listaDesafios.substring(inicioParentesis + 1, finParentesis);//guardo el formato de 1:20,30 sin los paretenssus
    
                                    StringTokenizer partesGrupo = new StringTokenizer(grupo, ":");//primero esta el codigo de la habtacion y despues se separa con : para los puntajes de los desafios, por eso corto con :
                                    int codigoHabDesafio = Integer.parseInt(partesGrupo.nextToken()); //guardo el codigo de la habitacion
                                    String puntajeString=partesGrupo.nextToken();//aca guardo el puntaje sacando el codigo, 20,30,etc.

                                    StringTokenizer puntajes = new StringTokenizer(puntajeString, ",");//los puntajes se separan por ,
                                    //busco la habitacion
                                    Habitacion habDesafio = (Habitacion) avlHabitaciones.recuperar(new Habitacion(codigoHabDesafio));////creo nueva habitacion para buscarla en el arbol con el compareTo. Como buscar devuelve un comparable, yo lo casteo a un Habitacion
    
                                    if (habDesafio != null) {
                                        // recorro cada puntaje de la lista
                                        while (puntajes.hasMoreTokens()) {//recorro mientras hayan tokens por leer
                                            int puntaje = Integer.parseInt(puntajes.nextToken());
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