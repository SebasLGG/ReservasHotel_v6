package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.FactoriaVista;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class MainApp {

    // Método Main
    public static void main(String[] args) {
        // Procesar los argumentos de la fuente de datos.
        FactoriaFuenteDatos factoriaFuenteDatos = procesarArgumentosFuenteDatos(args);
        // Crear el modelo usando la factoría de fuente de datos.
        IModelo modelo = new Modelo(factoriaFuenteDatos);

        // Procesar los argumentos de la vista.
        FactoriaVista factoriaVista = procesarArgumentosVista(args);
        // Crear la vista correspondiente usando la factoría de vista.
        Vista vista = factoriaVista.crear();

        // Crear el controlador con el modelo y la vista.
        Controlador controlador = new Controlador(modelo, vista);

        // Comenzar la aplicación.
        controlador.comenzar();
    }

    // Método para procesar los argumentos de la fuente de datos.
    private static FactoriaFuenteDatos procesarArgumentosFuenteDatos(String[] args) {
        if (args.length == 0) {
            System.out.println("Argumento de fuente de datos no proporcionado. Usando MEMORIA por defecto.");
            return FactoriaFuenteDatos.MEMORIA;
        }

        // Verificar el argumento proporcionado
        String argumento = args[0];
        switch (argumento) {
            case "-fdmemoria":
                System.out.println("Conectando a fuente de datos MEMORIA.");
                return FactoriaFuenteDatos.MEMORIA;
            case "-fdmongodb":
                System.out.println("Conectando a fuente de datos MongoDB.");
                return FactoriaFuenteDatos.MONGODB;
            default:
                System.out.println("Argumento de fuente de datos no válido. Usando MEMORIA por defecto.");
                return FactoriaFuenteDatos.MEMORIA;
        }
    }

    // Método para procesar los argumentos de la vista.
    private static FactoriaVista procesarArgumentosVista(String[] args) {
        if (args.length < 2) {
            System.out.println("Argumento de vista no proporcionado. Usando VISTA TEXTO por defecto.");
            return FactoriaVista.TEXTO;
        }

        // Verificar el argumento proporcionado
        String argumento = args[1];
        switch (argumento) {
            case "-vTexto":
                System.out.println("Creando vista textual.");
                return FactoriaVista.TEXTO;
            case "-vGrafica":
                System.out.println("Creando vista gráfica.");
                return FactoriaVista.GRAFICA;
            default:
                System.out.println("Argumento de vista no válido. Usando VISTA TEXTO por defecto.");
                return FactoriaVista.TEXTO;
        }
    }
}
