
public class Habitacion {
    private int codigo;
    private String nombre;
    private int planta;
    private int metros;
    private boolean tieneSalida;
    private ArbolAVL desafios; //cada habitacion conoce sus propios desafios, CONSULTAR SI ES POSIBLE 

    public Habitacion(int codigo) {
        this.codigo=codigo;
    }

    public Habitacion(int codigo, String nombre, int planta, int metros, boolean tieneSalida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metros = metros;
        this.tieneSalida = tieneSalida;
        this.desafios=new ArbolAVL();
    }

    // Métodos observadores (Getters)
    public int getCodigo() {return codigo;}
    public String getNombre() {return nombre;}
    public int getPlanta() {return planta;}
    public int getMetros() { return metros;}
    public boolean tieneSalida() {return tieneSalida;}
    public ArbolAVL getDesafios() { return desafios;
    }

    // Métodos modificadores (Setters)
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setPlanta(int planta) {this.planta = planta;}
    public void setMetros(int metros) {this.metros = metros;}
    public void setTieneSalida(boolean tieneSalida) {this.tieneSalida = tieneSalida;}
    public void setDesafios(ArbolAVL desafios) { this.desafios = desafios; }

    
    /* 
    @Override
    public int compareTo(Habitacion otra) { //consultar como hago el compareTo
        return Integer.compare(this.codigo, otra.codigo);
    }
    */

    
    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (obj != null && obj instanceof Habitacion) { //instanceof verifica si el objeto pertenece a la clase habitacion
            Habitacion otro = (Habitacion) obj; 
            if (this.codigo==otro.codigo) {
                iguales = true;
            }
        }
        return iguales;
    }

    // Genera un valor entero unico para la clave
    @Override
    public int hashCode() {
        return Integer.hashCode(this.codigo);
    }

    @Override
    public String toString() {
        return "Habitacion [Codigo: " + codigo + ", Nombre: " + nombre + ", Planta: " + planta +
                ", Metros: " + metros + ", Salida Exterior: " + (tieneSalida ? "Sí" : "No") + "]";
    }

}