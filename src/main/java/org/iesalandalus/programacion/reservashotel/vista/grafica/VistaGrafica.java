package org.iesalandalus.programacion.reservashotel.vista.grafica;

import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class VistaGrafica extends Vista {

	//Atributo para almacenar la instancia de vista gráfica
    private static VistaGrafica instancia;

    //Constructor
    private VistaGrafica(){

    }

    //Método para obtener la instancia de vista gráfica
    public static VistaGrafica getInstancia() {
        if (instancia==null)
            instancia=new VistaGrafica();

        return instancia;
    }

    //Método comenzar para iniciar la vista gráfica
    @Override
    public void comenzar() {
        LanzadorVentanaPrincipal.comenzar();
    }

    //Método para terminar la vista gráfica
    @Override
    public void terminar() {
        getControlador().terminar();
    }
}
