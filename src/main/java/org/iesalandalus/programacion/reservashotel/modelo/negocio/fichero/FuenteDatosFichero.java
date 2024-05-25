package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

public class FuenteDatosFichero implements IFuenteDatos {


 
	// Crear la instancia de la interfaz huéspedes
    @Override
    public IHuespedes crearHuespedes() {
        return Huespedes.getInstancia();
    }
    
    // Crear la instancia de la interfaz habitaciones
    @Override
    public IHabitaciones crearHabitaciones() {
        return Habitaciones.getInstancia();
    }

    // Crear la instancia de la interfaz reservas
    @Override
    public IReservas crearReservas() {
    	return Reservas.getInstancia();
    }
    
}
