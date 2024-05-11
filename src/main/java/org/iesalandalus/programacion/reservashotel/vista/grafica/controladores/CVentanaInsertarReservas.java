package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;


public class CVentanaInsertarReservas {

	//Atributos huésped
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
    
    
    //Atributos habitaciones
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
    
    
    //Atributos para reserva
    @FXML
    private Button Insertar;

    @FXML
    private Button Cancelar;

    @FXML
    private ComboBox<Regimen> TipoRegimen;


    @FXML
    private DatePicker FechaFin;

    @FXML
    private DatePicker FechaInicio;

    @FXML
    private ToggleGroup grupoPersonas;

    @FXML
    private RadioButton Personas1;

    @FXML
    private RadioButton Personas2;

    @FXML
    private RadioButton Personas3;

    @FXML
    private RadioButton Personas4;


    //Inicializar el cargado de tipos de habitaciones y regimen.
    @FXML
    void initialize(){

    	TiposHabitacion.setItems(FXCollections.observableArrayList(TipoHabitacion.values()));
    	TipoRegimen.setItems(FXCollections.observableArrayList(Regimen.values()));

    }

    //Método para insertar la reserva
    @FXML
    void insertar(ActionEvent event) {
    	/*ESTE SERÍA LA FORMA CORRECTA PARA INSERTAR EL HUÉSPED PARA QUE CON SOLO INDICAR EL DNI ME RELLENE EL RESTO DE DATOS AUTOMATICAMENTE
         * PERO EL PROGRAMA EMPIEZA A IR LENTO Y ME DICE QUE NO RESPONDE AUNQUE FUNCIONE. */
        /*Huesped huesped= VistaGrafica.getInstancia().getControlador().buscar(new Huesped("aaaa", Dni.getText(), "aaaf@gmail.com", "666266664", LocalDate.of(1999,11,05)));
        */
        Huesped huesped= VistaGrafica.getInstancia().getControlador().buscar(new Huesped(Nombre.getText(), Dni.getText(), Correo.getText(), Telefono.getText(), FechaNacimiento.getValue()));

        Habitacion habitacion = null;
        int numCamasInd = 0;
        int numCamasDobles = 0;
        int numBanios = 0;
        boolean jacuzzi = false;

        if (UnaCamaIndividual.isSelected()) {
            numCamasInd = 1;
        } else if (DosCamasIndividuales.isSelected()) {
            numCamasInd = 2;
        } else if (TresCamasIndividuales.isSelected()) {
            numCamasInd = 3;
        }

        if (UnaCamaDoble.isSelected()) {
            numCamasDobles = 1;
        }

        if (UnBano.isSelected()) {
            numBanios = 1;
        } else if (DosBanos.isSelected()) {
            numBanios = 2;
        } else if (TresBanos.isSelected()) {
            numBanios = 3;
        }

        if (JacuzziSi.isSelected()) {
            jacuzzi = true;
        }

        try {
            TipoHabitacion tipoSeleccionado = TiposHabitacion.getSelectionModel().getSelectedItem();
            if (tipoSeleccionado != null) {
                switch (tipoSeleccionado) {
                    case SUITE:
                        habitacion = new Suite(Integer.parseInt(Planta.getText()), Integer.parseInt(Puerta.getText()), Double.parseDouble(Precio.getText()), numBanios, jacuzzi);
                        break;
                    case SIMPLE:
                        habitacion = new Simple(Integer.parseInt(Planta.getText()), Integer.parseInt(Puerta.getText()), Double.parseDouble(Precio.getText()));
                        break;
                    case DOBLE:
                        habitacion = new Doble(Integer.parseInt(Planta.getText()), Integer.parseInt(Puerta.getText()), Double.parseDouble(Precio.getText()), numCamasInd, numCamasDobles);
                        break;
                    case TRIPLE:
                        habitacion = new Triple(Integer.parseInt(Planta.getText()), Integer.parseInt(Puerta.getText()), Double.parseDouble(Precio.getText()), numBanios, numCamasInd, numCamasDobles);
                        break;
                }
                if (Dialogos.mostrarDialogoConfirmacion("Insertar Reserva", "¿Insertar reserva?")) {
                    Regimen regimen = null;
                    int numPersonas = 0;
                    if (TipoRegimen.getSelectionModel().isSelected(0)) {
                        regimen = Regimen.SOLO_ALOJAMIENTO;
                    } else if (TipoRegimen.getSelectionModel().isSelected(1)) {
                        regimen = Regimen.ALOJAMIENTO_DESAYUNO;
                    } else if (TipoRegimen.getSelectionModel().isSelected(2)) {
                        regimen = Regimen.MEDIA_PENSION;
                    } else if (TipoRegimen.getSelectionModel().isSelected(3)) {
                        regimen = Regimen.PENSION_COMPLETA;
                    }
                    RadioButton Seleccionado = (RadioButton) grupoPersonas.getSelectedToggle();
                    if (Seleccionado == Personas1) {
                        numPersonas = 1;
                    } else if (Seleccionado == Personas2) {
                        numPersonas = 2;
                    } else if (Seleccionado == Personas3) {
                        numPersonas = 3;
                    } else if (Seleccionado == Personas4) {
                        numPersonas = 4;
                    }
                    VistaGrafica.getInstancia().getControlador().insertar(new Reserva(huesped, habitacion, regimen, FechaInicio.getValue(), FechaFin.getValue(), numPersonas));
                    Dialogos.mostrarDialogoInformacion("Reserva insertada", "Reserva insertada correctamente.");
                    ((Stage) Insertar.getScene().getWindow()).close();
                }
            } else {
                Dialogos.mostrarDialogoError("Error", "Seleccione un tipo de habitación.");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            Dialogos.mostrarDialogoError("Error al insertar la reserva", e.getMessage());
        }
    }

    //Método para cancelar y cerrar la ventana.
    @FXML
    void cancelar(ActionEvent event) {

        ((Stage)Cancelar.getScene().getWindow()).close();

    }

}
