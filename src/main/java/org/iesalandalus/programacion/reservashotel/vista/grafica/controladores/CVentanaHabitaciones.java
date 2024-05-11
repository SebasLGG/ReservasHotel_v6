package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleBooleanProperty;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Doble;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Simple;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Suite;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Triple;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CVentanaHabitaciones {

	//Atributos
    @FXML
    private TableView<Habitacion> TablaListadoHabitaciones;
    
    @FXML
    private TableColumn<Habitacion, String> Identificador;
    
    @FXML
    private TableColumn<Habitacion, String> Planta;
    
    @FXML
    private TableColumn<Habitacion, String> Puerta;
    
    @FXML
    private TableColumn<Habitacion, String> Precio;
    
    @FXML
    private TableColumn<Habitacion, String> TipoHabitacion;
    private List<Habitacion> coleccionHabitaciones=new ArrayList<>();
    private ObservableList<Habitacion> obsHabitacion= FXCollections.observableArrayList();
    
    @FXML
    private TableColumn<Habitacion, Integer> Camas;
    
    @FXML
    private TableColumn<Habitacion, Integer> Bano;
    
    @FXML
    private TableColumn<Habitacion, Boolean> Jacuzzi;
    
    @FXML
    private MenuItem MenuInsertarHabitacion;
    
    @FXML
    private MenuItem MenuEliminarHabitacion;
    
    @FXML
    private Button botonInsertarHabitacion;

    @FXML
    private Button botonBorrarHabitacion;

    //Inicializar cargar los datos de habitaciones
    @FXML
    private void initialize(){

    	cargarDatosHabitaciones();

        Identificador.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getIdentificador()));;
        Planta.setCellValueFactory(habitacion-> new SimpleStringProperty(Integer.toString(habitacion.getValue().getPlanta())));;
        Puerta.setCellValueFactory(habitacion-> new SimpleStringProperty(Integer.toString(habitacion.getValue().getPuerta())));;
        Precio.setCellValueFactory(habitacion-> new SimpleStringProperty(Double.toString(habitacion.getValue().getPrecio())));;
        TipoHabitacion.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getClass().getSimpleName()));;
        TablaListadoHabitaciones.setItems(obsHabitacion);
        Camas.setCellValueFactory(habitacion -> {
            if (habitacion.getValue() instanceof Suite) {
            	return new SimpleIntegerProperty(((Suite) habitacion.getValue()).getNumBanos()).asObject();

            } else if (habitacion.getValue() instanceof Triple) {
                return new SimpleIntegerProperty(((Triple) habitacion.getValue()).getNumCamasIndividuales()).asObject();
            } else if (habitacion.getValue() instanceof Doble) {
                return new SimpleIntegerProperty(((Doble) habitacion.getValue()).getNumCamasIndividuales()).asObject();
            } else if (habitacion.getValue() instanceof Simple) {
                return new SimpleIntegerProperty(1).asObject();
            } else {
                return null;
            }
        });

        Jacuzzi.setCellValueFactory(habitacion -> {
            if (habitacion.getValue() instanceof Suite) {
                return new SimpleBooleanProperty(((Suite) habitacion.getValue()).isTieneJacuzzi());
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
    }
    
    //Método para obtener los datos de habitaciones
    private void cargarDatosHabitaciones()
    {

        coleccionHabitaciones = VistaGrafica.getInstancia().getControlador().getHabitaciones();
        obsHabitacion.setAll(coleccionHabitaciones);

    }

    //Método para abrir la ventana para insertar las habitaciones.
    @FXML
    void InsertarHabitaciones(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertarHabitacion.fxml"));
        try
        {
            Parent raiz=fxmlLoader.load();

            Scene escenaVentanaHabitacion=new Scene(raiz,600,400);
            Stage escenarioVentanaHabitacion=new Stage();
            escenarioVentanaHabitacion.setScene(escenaVentanaHabitacion);
            escenarioVentanaHabitacion.setTitle("Hotel I.E.S. Al-Ándalus - Insertar Habitación" );
            escenarioVentanaHabitacion.initModality(Modality.APPLICATION_MODAL);
            escenarioVentanaHabitacion.showAndWait();
            cargarDatosHabitaciones();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Método para eliminar una habitación de la tabla.
    @FXML
    void BorrarHabitaciones(ActionEvent event) {

        Habitacion habitacion=TablaListadoHabitaciones.getSelectionModel().getSelectedItem();

         if (habitacion!=null &&
                Dialogos.mostrarDialogoConfirmacion("Hotel I.E.S. Al-Ándalus - Eliminar Habitacion", "¿Desea borrar la habitación seleccionada?"))
        {

            try {
                VistaGrafica.getInstancia().getControlador().borrar(habitacion);
            }catch (NullPointerException | IllegalArgumentException e){
                Dialogos.mostrarDialogoError("Error al borrar la habitacion",e.getMessage());
            }
            cargarDatosHabitaciones();
            Dialogos.mostrarDialogoInformacion("Hotel I.E.S. Al-Ándalus - Eliminar Habitación", "Habitación eliminada correctamente.");
        }

        if (habitacion==null)
            Dialogos.mostrarDialogoAdvertencia("Hotel I.E.S. Al-Ándalus - Eliminar Habitación","Debes seleccionar una habitacion de la tabla para eliminarla.");

    }

    //Método de acción para ejecutar el insertar habitaciones
    @FXML
    void InsertarHabitacion(ActionEvent event) {

        InsertarHabitaciones(event);

    }

    //Método de acción para ejecutar el eliminar habitaciones
    @FXML
    void EliminarHabitacion(ActionEvent event) {

        BorrarHabitaciones(event);

    }


}

