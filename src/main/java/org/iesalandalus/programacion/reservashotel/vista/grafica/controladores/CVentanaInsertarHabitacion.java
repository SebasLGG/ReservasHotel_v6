package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import javafx.scene.control.ToggleGroup;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;



public class CVentanaInsertarHabitacion {

	//Atributos
    @FXML
    private TextField Planta;
    
    @FXML
    private TextField Puerta;
    
    @FXML
    private TextField Precio;
	
    @FXML
    private ComboBox<TipoHabitacion> TiposHabitacion;
    
    @FXML
    private ToggleGroup CamasIndividuales;
    
    @FXML
    private RadioButton CeroCamaIndividual;
    
    @FXML
    private RadioButton UnaCamaIndividual;
    
    @FXML
    private RadioButton DosCamasIndividuales;
    
    @FXML
    private RadioButton TresCamasIndividuales;
    
    @FXML
    private ToggleGroup CamasDobles;
    
    @FXML
    private RadioButton CeroCamasDobles;
    
    @FXML
    private RadioButton UnaCamaDoble;
    
    @FXML
    private Button Insertar;

    @FXML
    private Button Cancelar;

    @FXML
    private ToggleGroup Banos;

    @FXML
    private RadioButton UnBano;

    @FXML
    private RadioButton DosBanos;

    @FXML
    private RadioButton TresBanos;
    
    @FXML
    private ToggleGroup Jacuzzi;

    @FXML
    private RadioButton JacuzziNo;

    @FXML
    private RadioButton JacuzziSi;


    //Inicializar los datos de tipos de habitaciones
    @FXML
    void initialize(){
    	TiposHabitacion.setItems(FXCollections.observableArrayList(TipoHabitacion.values()));
    }

    //Método para insertar las habitaciones
    @FXML
    void Insertar(ActionEvent event) {

        int numCamasInd=0, numCamasDobles=0, numBanios=1;
        boolean jacuzzi=false;

        RadioButton Seleccionado1=(RadioButton) CamasIndividuales.getSelectedToggle();
        if (Seleccionado1==CeroCamaIndividual)
        {
            numCamasInd=0;
        }
        else if (Seleccionado1==UnaCamaIndividual)
        {
            numCamasInd=1;
        }
        else if (Seleccionado1==DosCamasIndividuales)
        {
            numCamasInd=2;

        }else if(Seleccionado1==TresCamasIndividuales){

            numCamasInd=3;
        }

        RadioButton Seleccionado2=(RadioButton) CamasDobles.getSelectedToggle();
        if (Seleccionado2==CeroCamasDobles){
            numCamasDobles=0;
        } else if(Seleccionado2==UnaCamaDoble){
            numCamasDobles=1;
        }

        RadioButton Seleccionado3=(RadioButton) Banos.getSelectedToggle();

        if(Seleccionado3==UnBano){
            numBanios=1;
        }else if(Seleccionado3==DosBanos){
            numBanios=2;
        }else if(Seleccionado3==TresBanos){
            numBanios=3;
        }

        RadioButton Seleccionado4=(RadioButton) Jacuzzi.getSelectedToggle();

        if(Seleccionado4==JacuzziSi){
            jacuzzi=true;
        }else if(Seleccionado4==JacuzziNo){
            jacuzzi=false;
        }

        //Depende de la opción marcada creará una habitación u otra
        try{
        if(TiposHabitacion.getSelectionModel().isSelected(3)){
            if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitación", "¿Insertar esta habitación?")){
                VistaGrafica.getInstancia().getControlador().insertar(new Suite(Integer.parseInt(Planta.getText()), Integer.parseInt(Puerta.getText()),Double.parseDouble(Precio.getText()), numBanios, jacuzzi));
                Dialogos.mostrarDialogoInformacion("Habitacion insertada", "Habitación insertada correctamente.");
            }
        }
        else if (TiposHabitacion.getSelectionModel().isSelected(0)) {
            if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitación", "¿Insertar esta habitación?")){
                VistaGrafica.getInstancia().getControlador().insertar(new Simple(Integer.parseInt(Planta.getText()), Integer.parseInt(Puerta.getText()),Double.parseDouble(Precio.getText())));
                Dialogos.mostrarDialogoInformacion("Habitacion insertada", "Habitación insertada correctamente.");
            }
        }
        else if(TiposHabitacion.getSelectionModel().isSelected(1)){
            if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitación", "¿Insertar esta habitación?")){
                VistaGrafica.getInstancia().getControlador().insertar(new Doble(Integer.parseInt(Planta.getText()),Integer.parseInt(Puerta.getText()),Double.parseDouble(Precio.getText()),numCamasInd,numCamasDobles));
                Dialogos.mostrarDialogoInformacion("Habitacion Habitación", "Habitación insertada correctamente.");
            }
        }
        else if (TiposHabitacion.getSelectionModel().isSelected(2)){

            if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitación", "¿Insertar esta habitación?")){
                VistaGrafica.getInstancia().getControlador().insertar(new Triple(Integer.parseInt(Planta.getText()), Integer.parseInt(Puerta.getText()), Double.parseDouble(Precio.getText()), numBanios,numCamasInd,numCamasDobles));
                Dialogos.mostrarDialogoInformacion("Habitacion insertada", "Habitación insertada correctamente.");
            }
        }}catch (NullPointerException | IllegalArgumentException e){
            Dialogos.mostrarDialogoError("Error al insertar habitacion",e.getMessage());
        }
        ((Stage)Insertar.getScene().getWindow()).close();


    }

    //Método cancelar y cerrar la ventana
    @FXML
    void Cancelar(ActionEvent event) {

        ((Stage)Cancelar.getScene().getWindow()).close();

    }


}

