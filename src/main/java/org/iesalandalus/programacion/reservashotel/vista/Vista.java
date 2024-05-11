package org.iesalandalus.programacion.reservashotel.vista;

//Paquetes necesarios.
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;

//Clase abstracta vista.
public abstract class Vista {

	//Atributo protegido para almacenar el controlador
    protected Controlador controlador;

    //Método set y get del controlador
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public Controlador getControlador() {
        return controlador;
    }

    //Métodos abstractos de comenzar y terminar.
    public abstract void comenzar();
    public abstract void terminar();

}
