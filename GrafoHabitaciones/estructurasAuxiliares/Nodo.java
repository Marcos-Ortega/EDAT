package GrafoHabitaciones.estructurasAuxiliares;

public class Nodo {
    private Object elem;
    private Nodo enlace;

    //contructor 
    public Nodo (Object elem, Nodo enlace){
        this.elem=elem;
        this.enlace=enlace;
    }

    // geters
    public Object getElem(){
        return elem;
    }
    public Nodo getEnlace(){
        return enlace;
    }

    // seters
    public void setElem(Object elem){
        this.elem= elem;
    }
    public void setEnlace(Nodo enlace){
        this.enlace= enlace;
    }
}
