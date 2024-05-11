package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;


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

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;


import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CVentanaHuespedes {
    

    //Atributos
    @FXML
    private TableColumn<Huesped, String> Nombre;
    
    @FXML
    private TableColumn<Huesped, String> Dni;
    
    @FXML
    private TableColumn<Huesped, String> Correo;
    
    @FXML
    private TableColumn<Huesped, String> Telefono;
    
    @FXML
    private TableColumn<Huesped, String> FechaNacimiento;
    
    private static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
   
    @FXML
    private TableView<Huesped> TablaListadoHuespedes;
    
    @FXML
    private Button botonInsertarHuesped;

    @FXML
    private Button botonBorrarHuesped;

    @FXML
    private MenuItem MenuInsertarHuesped;

    private ObservableList<Huesped> obsHuesped = FXCollections.observableArrayList();
    private List<Huesped> coleccionHuesped=new ArrayList<>();

    private void cargarDatosHuespedes()
    {

        coleccionHuesped = VistaGrafica.getInstancia().getControlador().getHuespedes();
        obsHuesped.setAll(coleccionHuesped);

    }

    @FXML
    private MenuItem MenuEliminarHuesped;

    //Inicializar cargar los datos de huéspedes
    @FXML
    private void initialize(){

    	cargarDatosHuespedes();

        TablaListadoHuespedes.setItems(obsHuesped);

        Nombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));
        Dni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        Correo.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getCorreo()));
        Telefono.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getTelefono()));
        FechaNacimiento.setCellValueFactory(huesped->new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(FORMATO_FECHA).toString()));

    }

    //Método para abrir la ventana de insertar huéspedes
    @FXML
    void InsertarHuespedes(ActionEvent event){

        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertarHuesped.fxml"));
        try
        {
            Parent raiz=fxmlLoader.load();


            Scene escenaVentanaHuesped=new Scene(raiz,600,400);
            Stage escenarioVentanaHuesped=new Stage();
            escenarioVentanaHuesped.setScene(escenaVentanaHuesped);
            escenarioVentanaHuesped.setTitle("Hotel I.E.S. Al-Ándalus - Insertar Huésped" );
            escenarioVentanaHuesped.initModality(Modality.APPLICATION_MODAL);
            escenarioVentanaHuesped.showAndWait();
            cargarDatosHuespedes();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Método para eliminar un huésped de la tabla seleccionado
    @FXML
    void BorrarHuespedes(ActionEvent event){

        Huesped huesped=TablaListadoHuespedes.getSelectionModel().getSelectedItem();

        if (huesped!=null &&
                Dialogos.mostrarDialogoConfirmacion("Hotel I.E.S. Al-Ándalus - Eliminar Huésped", "¿Desea borrar el huesped seleccionado?"))
        {
            try {
                VistaGrafica.getInstancia().getControlador().borrar(huesped);
            }catch (NullPointerException | IllegalArgumentException e){
                Dialogos.mostrarDialogoError("Ha ocurrido un error al eliminar el huésped",e.getMessage());
            }

            cargarDatosHuespedes();
            Dialogos.mostrarDialogoInformacion("Hotel I.E.S. Al-Ándalus - Eliminar Huésped", "Huésped eliminado correctamente.");
        }

        if (huesped==null)
            Dialogos.mostrarDialogoAdvertencia("Hotel I.E.S. Al-Ándalus - Eliminar Huésped","Debes seleccionar un huésped de la tabla para eliminarlo.");

    }

    //Método de acción para ejecutar insertar huespedes
    @FXML
    void InsertaHuesped (ActionEvent event){
        InsertarHuespedes(event);
    }

    //Método de acción para ejecutar el eliminar huésped
    @FXML
    void EliminaHuesped (ActionEvent event){
    BorrarHuespedes(event);
    }

    }

