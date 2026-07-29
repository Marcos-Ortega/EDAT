package Sistema;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class log {
    private static final String ARCHIVO = "log.txt";

    public static void registrar(String accion) {
        //gestino errores
        try ( BufferedWriter escritorLog = new BufferedWriter(new FileWriter(ARCHIVO, true));) {//creo el archivo log.txt y el true sirve para que escriba al final del archivo, que no sobreescriba
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));//guardo la fecha y hora actual del sistema y la formateo usando un formato personalizado como (fecha hora)

            escritorLog.write(fecha + " - " + accion);
            escritorLog.newLine();

        } catch (IOException e) {
            System.out.println("Error al escribir el log: " + e.getMessage());
        }
    }
}

