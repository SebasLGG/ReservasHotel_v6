package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

//Enumerado vista
public enum FactoriaVista {

	//Opción texto
    TEXTO{
        public Vista crear(){
            return new VistaTexto();
        }
    },
    
    //Opción gráfica
    GRAFICA{
        @Override
        public Vista crear() {
            return VistaGrafica.getInstancia();
        }
    };

    public abstract Vista crear();
}
