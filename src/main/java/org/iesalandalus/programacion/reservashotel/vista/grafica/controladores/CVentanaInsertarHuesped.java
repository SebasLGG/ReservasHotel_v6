package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;



public class CVentanaInsertarHuesped {

	//Atributos
    @FXML
    private TextField Nombre;
    
    @FXML
    private TextField Dni;
    
    @FXML
    private TextField Correo;
    
    @FXML
    private TextField Telefono;
    
    @FXML
    private DatePicker FechaNacimiento;
    
    @FXML
    private Button Insertar;

    @FXML
    private Button Cancelar;

    //Método insertar huésped
    @FXML
    void Insertar(ActionEvent event) {

        try{
            if(Dialogos.mostrarDialogoConfirmacion("ADVERTENCIA","¿Insertar huésped?")){
                VistaGrafica.getInstancia().getControlador().insertar(new Huesped(Nombre.getText(), Dni.getText(),Correo.getText(),Telefono.getText(),FechaNacimiento.getValue()));
                Dialogos.mostrarDialogoInformacion("CONFIRMACIÓN", "Huésped insertado correctamente.");
            }
        }catch (NullPointerException | IllegalArgumentException e){
            Dialogos.mostrarDialogoError("Error al insertar el huésped",e.getMessage());
        }
        ((Stage)Insertar.getScene().getWindow()).close();
    }

    //Método para cancelar y cerrar la ventana.
    @FXML
    void Cancelar(ActionEvent event) {

        ((Stage)Cancelar.getScene().getWindow()).close();

    }


}
