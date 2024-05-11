package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Doble;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Simple;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Suite;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Triple;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CVentanaDatos {

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
    


    private ObservableList<Huesped> obsHuesped = FXCollections.observableArrayList();
    private List<Huesped> coleccionHuesped=new ArrayList<>();

    private void cargarDatosHuespedes()
    {

        coleccionHuesped = VistaGrafica.getInstancia().getControlador().getHuespedes();
        obsHuesped.setAll(coleccionHuesped);

    }
    
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
    
    private void cargarDatosHabitaciones()
    {

        coleccionHabitaciones = VistaGrafica.getInstancia().getControlador().getHabitaciones();
        obsHabitacion.setAll(coleccionHabitaciones);

    }
    
    @FXML
    private TableView<Reserva> TablaListadoReservas;
    
    @FXML
    private TableColumn<Reserva, String> Dni1;
    
    @FXML
    private TableColumn<Reserva, String> Nombre1;
    
    @FXML
    private TableColumn<Reserva, String> Identificador1;
    
    @FXML
    private TableColumn<Reserva, String> TipoHabitacion1;
    
    @FXML
    private TableColumn<Reserva, String> FechaInicio1;
    
    @FXML
    private TableColumn<Reserva, String> FechaFin1;
    
    @FXML
    private TableColumn<Reserva, String> Precio1;
    
    @FXML
    private TableColumn<Reserva, String> Regimen1;
    
    @FXML
    private TableColumn<Reserva, String> CheckIn1;

    @FXML
    private TableColumn<Reserva, String> CheckOut1;
    
    @FXML
    private TableColumn<Reserva, String> Personas1;
    

    private List<Reserva> coleccionReservas=new ArrayList<>();
    private ObservableList<Reserva> obsReserva= FXCollections.observableArrayList();

    private void cargarDatosReservas()
    {
        coleccionReservas = VistaGrafica.getInstancia().getControlador().getReservas();
        obsReserva.setAll(coleccionReservas);
    }
    
    
    
    
    // Inicializar que cargue los datos de huéspedes, habitaciones y reservas
	@FXML
    private void initialize(){

    	cargarDatosHuespedes();

        TablaListadoHuespedes.setItems(obsHuesped);

        Nombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));
        Dni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        Correo.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getCorreo()));
        Telefono.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getTelefono()));
        FechaNacimiento.setCellValueFactory(huesped->new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(FORMATO_FECHA).toString()));
        
        
        
        
        
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
        
        cargarDatosReservas();

        Identificador1.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));;
        Dni1.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));;
        FechaInicio1.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));;
        FechaFin1.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));;
        Nombre1.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre()));;
        Precio1.setCellValueFactory(reserva-> new SimpleStringProperty(Double.toString(reserva.getValue().getHabitacion().getPrecio())));;
        Regimen1.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getRegimen().toString()));;
        TipoHabitacion1.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getClass().getSimpleName()));;
        CheckIn1.setCellValueFactory(reserva -> {
            String fechaHoraCheckIn = (reserva.getValue().getCheckIn() != null) ? reserva.getValue().getCheckIn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "No registrado";
            return new SimpleStringProperty(fechaHoraCheckIn);
        });
        CheckOut1.setCellValueFactory(reserva -> {
            String fechaHoraCheckOut = (reserva.getValue().getCheckOut() != null) ? reserva.getValue().getCheckOut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "No registrado";
            return new SimpleStringProperty(fechaHoraCheckOut);
        });
        Personas1.setCellValueFactory(reserva-> new SimpleStringProperty(Double.toString(reserva.getValue().getHabitacion().getNumeroMaximoPersonas())));;
        TablaListadoReservas.setItems(obsReserva);

    }
        
        
        
        
    }
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

