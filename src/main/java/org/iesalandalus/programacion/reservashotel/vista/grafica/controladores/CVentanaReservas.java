package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CVentanaReservas {

	//Atributos
    @FXML
    private TableView<Reserva> TablaListadoReservas;
    
    @FXML
    private TableColumn<Reserva, String> Dni;
    
    @FXML
    private TableColumn<Reserva, String> Nombre;
    
    @FXML
    private TableColumn<Reserva, String> Identificador;
    
    @FXML
    private TableColumn<Reserva, String> TipoHabitacion;
    
    @FXML
    private TableColumn<Reserva, String> FechaInicio;
    
    @FXML
    private TableColumn<Reserva, String> FechaFin;
    
    @FXML
    private TableColumn<Reserva, String> Precio;
    
    @FXML
    private TableColumn<Reserva, String> Regimen;
    
    @FXML
    private TableColumn<Reserva, String> CheckIn;

    @FXML
    private TableColumn<Reserva, String> CheckOut;
    
    @FXML
    private TableColumn<Reserva, String> Personas;
    
    @FXML
    private MenuItem EliminarReserva;

    @FXML
    private MenuItem InsertarReserva;
    
    @FXML
    private Button InsertarReservas;

    @FXML
    private Button EliminarReservas;

    @FXML
    private Button RealizarCheckIn;

    @FXML
    private Button RealizarCheckOut;

    private List<Reserva> coleccionReservas=new ArrayList<>();
    private ObservableList<Reserva> obsReserva= FXCollections.observableArrayList();

    //Obtener los datos de reservas
    private void cargarDatosReservas()
    {
        coleccionReservas = VistaGrafica.getInstancia().getControlador().getReservas();
        obsReserva.setAll(coleccionReservas);
    }

    //Inicializar el cargar datos de la reserva
    @FXML
    private void initialize(){

    	cargarDatosReservas();

        Identificador.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));;
        Dni.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));;
        FechaInicio.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));;
        FechaFin.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));;
        Nombre.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre()));;
        Precio.setCellValueFactory(reserva-> new SimpleStringProperty(Double.toString(reserva.getValue().getHabitacion().getPrecio())));;
        Regimen.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getRegimen().toString()));;
        TipoHabitacion.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getClass().getSimpleName()));;
        CheckIn.setCellValueFactory(reserva -> {
            String fechaHoraCheckIn = (reserva.getValue().getCheckIn() != null) ? reserva.getValue().getCheckIn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "No registrado";
            return new SimpleStringProperty(fechaHoraCheckIn);
        });
        CheckOut.setCellValueFactory(reserva -> {
            String fechaHoraCheckOut = (reserva.getValue().getCheckOut() != null) ? reserva.getValue().getCheckOut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "No registrado";
            return new SimpleStringProperty(fechaHoraCheckOut);
        });
        Personas.setCellValueFactory(reserva-> new SimpleStringProperty(Double.toString(reserva.getValue().getHabitacion().getNumeroMaximoPersonas())));;
        TablaListadoReservas.setItems(obsReserva);

    }

    //Abrir la ventana insertar reservas.
    @FXML
    void insertarReservas(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertarReservas.fxml"));
        try
        {
            Parent raiz=fxmlLoader.load();

            Scene escenaVentanaReserva=new Scene(raiz,600,400);
            Stage escenarioVentanaReserva=new Stage();
            escenarioVentanaReserva.setScene(escenaVentanaReserva);
            escenarioVentanaReserva.setTitle("Hotel I.E.S. Al-Ándalus - Insertar Reserva" );
            escenarioVentanaReserva.initModality(Modality.APPLICATION_MODAL);
            escenarioVentanaReserva.showAndWait();
            cargarDatosReservas();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Eliminar una reserva seleccionada de la tabla
    @FXML
    void eliminarReservas(ActionEvent event) {

        Reserva reserva=TablaListadoReservas.getSelectionModel().getSelectedItem();

        if (reserva!=null &&
                Dialogos.mostrarDialogoConfirmacion("Hotel I.E.S. Al-Ándalus - Eliminar Reserva", "¿Desea borrar la reserva seleccionada?"))
        {

            try{

                VistaGrafica.getInstancia().getControlador().borrar(reserva);

            }catch (NullPointerException | IllegalArgumentException e){
                Dialogos.mostrarDialogoError("Ha ocurrido un error al eliminar la reserva",e.getMessage());
            }

            cargarDatosReservas();
            Dialogos.mostrarDialogoInformacion("Hotel I.E.S. Al-Ándalus - Eliminar Reserva", "Reserva eliminada correctamente.");
        }

        if (reserva==null)
            Dialogos.mostrarDialogoAdvertencia("Hotel I.E.S. Al-Ándalus - Eliminar Reserva","Debes seleccionar una reserva de la tabla para eliminarla.");

    }

    //Realizar el checkin de una reserva seleccionada.
    @FXML
    void RealizarcheckInReservas(ActionEvent event) {
        Reserva reserva = TablaListadoReservas.getSelectionModel().getSelectedItem();
        if (reserva != null) {
            try {
                VistaGrafica.getInstancia().getControlador().realizarCheckin(reserva, LocalDateTime.now());
                cargarDatosReservas();
                Dialogos.mostrarDialogoInformacion("Hotel I.E.S. Al-Ándalus - Check-In Reserva", "Check-In realizado correctamente.");
            } catch (Exception e) {
                Dialogos.mostrarDialogoError("Error", "Ha ocurrido un error al realizar el Check-In: " + e.getMessage());
            }
        } else {
            Dialogos.mostrarDialogoAdvertencia("Hotel I.E.S. Al-Ándalus - Check-In Reserva", "Debes seleccionar una reserva de la tabla para realizar el Check-In.");
        }
    }

    //Realizar el checkout de la reserva seleccionada
    @FXML
    void RealizarcheckOutReservas(ActionEvent event) {
        Reserva reserva = TablaListadoReservas.getSelectionModel().getSelectedItem();
        if (reserva != null) {
            try {
                VistaGrafica.getInstancia().getControlador().realizarCheckout(reserva, LocalDateTime.now());
                cargarDatosReservas();
                Dialogos.mostrarDialogoInformacion("Hotel I.E.S. Al-Ándalus - Check-Out Reserva", "Check-Out realizado correctamente.");
            } catch (Exception e) {
                Dialogos.mostrarDialogoError("Error", "Ha ocurrido un error al realizar el Check-In: " + e.getMessage());
            }
        } else {
            Dialogos.mostrarDialogoAdvertencia("Hotel I.E.S. Al-Ándalus - Check-Out Reserva", "Debes seleccionar una reserva de la tabla para realizar el Check-Out.");
        }
    }

    //Método de acción para eliminar la reserva
    @FXML
    void eliminarReserva(ActionEvent event) {

        eliminarReservas(event);

    }

    //Método de acción para insertar una reserva
    @FXML
    void insertarReserva(ActionEvent event) {

        insertarReservas(event);

    }

}
