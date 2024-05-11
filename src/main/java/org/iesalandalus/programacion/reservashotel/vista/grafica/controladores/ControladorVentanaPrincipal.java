package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;

public class ControladorVentanaPrincipal {

	//Declaración de atributos
    @FXML
    private Button botonPrincipalHabitaciones;

    @FXML
    private Button botonPrincipalHuespedes;

    @FXML
    private Button botonPrincipalReservas;

    
    //Método para salir de la aplicación con dialogo
    @FXML
    void SalirAplicacion(ActionEvent event) {

        if (Dialogos.mostrarDialogoConfirmacion("Hotel I.E.S. Al-Ándalus", "Pulse ``Aceptar´´ o presiona ENTER si desea cerrar la aplicación."))
        {
            System.exit(0);
        }
        else
            event.consume();
    }
    
    
    //Método para abrir la ventana huéspedes
    @FXML
    void abrirVentanaHuespedes(ActionEvent event) {

        FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaHuespedes.fxml"));

        try {
            Parent raiz=fxmlLoader.load();

            Scene escena=new Scene(raiz,700,500);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel I.E.S. Al-Ándalus - Gestión de huéspedes");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
    
    
  //Método para abrir la ventana habitaciones
    @FXML
    void abrirVentanaHabitaciones(ActionEvent event) {

        FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaHabitaciones.fxml"));
        try {
            Parent raiz=fxmlLoader.load();

            Scene escena=new Scene(raiz,700,500);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel I.E.S. Al-Ándalus - Gestión de habitaciones");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }

    
    //Método para abrir la ventana reservas
    @FXML
    void abrirVentanaReservas(ActionEvent event) {

    	FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaReservas.fxml"));
        try {
            Parent raiz=fxmlLoader.load();

            Scene escena=new Scene(raiz,700,500);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel I.E.S. Al-Ándalus - Gestión de reservas");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
    
    
    //Método para abrir la ventana datos
    @FXML
    void abrirVentanaDatos(ActionEvent event) {

    	FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaDatos.fxml"));
        try {
            Parent raiz=fxmlLoader.load();

            Scene escena=new Scene(raiz,900,800);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel I.E.S. Al-Ándalus - DATOS");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
    

}

