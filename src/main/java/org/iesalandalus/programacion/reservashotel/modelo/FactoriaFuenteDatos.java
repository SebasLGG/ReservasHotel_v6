package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.FuenteDatosMongoDB;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.FuenteDatosFichero;

public enum FactoriaFuenteDatos {

	// Métodos para crear una instancia de FuenteDatosMemoria.
    MEMORIA{
        public IFuenteDatos crear(){
            return new FuenteDatosMemoria();
        }
    },
    
    // Métodos para crear una instancia de FuenteDatosMongoDB.
    MONGODB{
        public IFuenteDatos crear(){
            return new FuenteDatosMongoDB();
        }
    },
    
    // Métodos para crear una instancia de FuenteDatosFichero.
    FICHERO {
        public IFuenteDatos crear() {
            return new FuenteDatosFichero();
        }
    };

	// Método abstracto para crear una instancia de IFuenteDatos.
    public abstract IFuenteDatos crear();

}