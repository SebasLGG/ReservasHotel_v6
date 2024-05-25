package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Reservas implements IReservas {

	// Atributos
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String RUTA_FICHERO = "datos/reservas.xml";
    private static final String RAIZ = "Reservas";
    private static final String RESERVA = "Reserva";
    private static final String NOMBRE_HUESPED = "Nombre";
    private static final String CORREO_HUESPED = "Correo";
    private static final String TELEFONO_HUESPED = "Telefono";
    private static final String DNI_HUESPED = "Dni";
    private static final String FECHA_NACIMIENTO = "FechaNacimiento";
    
    private static final String PLANTA_HABITACION = "Planta";
    private static final String PUERTA_HABITACION = "Puerta";
    private static final String FECHA_INICIO_RESERVA = "FechaInicioReserva";
    private static final String FECHA_FIN_RESERVA = "FechaFinReserva";
    private static final String REGIMEN = "Regimen";
    private static final String NUMERO_PERSONAS = "Personas";
    private static final String CHECKIN = "FechaCheckin";
    private static final String CHECKOUT = "FechaCheckout";
    private static final String PRECIO = "Precio";

    private static Reservas instancia;
    private ArrayList<Reserva> coleccionReservas;

    private Reservas() {
        coleccionReservas = new ArrayList<>();
    }

    public static Reservas getInstancia() {
        if (instancia == null) {
            instancia = new Reservas();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        leerXML();
    }

    @Override
    public void terminar() {
        escribirXML();
    }

    public List<Reserva> get() {
        return new ArrayList<>(coleccionReservas);
    }

    public int getTamano() {
        return coleccionReservas.size();
    }

    // Método para insertar una reserva en el XML
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }

        Reserva reservaExistente = buscar(reserva);
        if (reservaExistente == null) {
            coleccionReservas.add(reserva);
            escribirXML();
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
    }

    // Método para buscar una reserva en la colección
    public Reserva buscar(Reserva reservaBuscada) {
        if (reservaBuscada == null) {
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        for (Reserva reserva : coleccionReservas) {
            if (reserva.equals(reservaBuscada)) {
                return reserva;
            }
        }
        return null;
    }

    // Método para borrar una reserva de la colección
    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if (!coleccionReservas.remove(reserva)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva igual.");
        }
    }

    // Método para leer las reservas desde un archivo XML
     private void leerXML() {
 //      try {
 //          File archivo = new File(RUTA_FICHERO);
 //          if (!archivo.exists()) {
 //              return;
 // 
 //             DocumentBuilder db = dbf.newDocumentBuilder();
 //        Document doc = db.parse(archivo);
 //        doc.getDocumentElement().normalize();
 // 
 //        NodeList reservas = doc.getElementsByTagName(RESERVA);
 //         for (int i = 0; i < reservas.getLength(); i++) {
 //            Node nodo = reservas.item(i);
 //            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
 //                  Element elemento = (Element) nodo;
 //                 coleccionReservas.add(reserva);
 //             }
 //        }
 //    } catch (ParserConfigurationException | SAXException | IOException | OperationNotSupportedException e) {
 //          e.printStackTrace();
 //       }
   }

     // Método para escribir las reservas en un archivo XML
    private void escribirXML() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element raiz = doc.createElement(RAIZ);
            doc.appendChild(raiz);

            for (Reserva reserva : coleccionReservas) {
                Element elementoReserva = reservaToElement(doc, reserva);
                raiz.appendChild(elementoReserva);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(RUTA_FICHERO));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    // Método para convertir una reserva en un elemento XML
    private Element reservaToElement(Document doc, Reserva reserva) {
        Element elementoReserva = doc.createElement(RESERVA);

        // Añadir atributos al elemento Reserva
        Huesped huesped = reserva.getHuesped();
        elementoReserva.setAttribute(NOMBRE_HUESPED, huesped.getNombre());
        elementoReserva.setAttribute(CORREO_HUESPED, huesped.getCorreo());
        elementoReserva.setAttribute(TELEFONO_HUESPED, huesped.getTelefono());
        elementoReserva.setAttribute(DNI_HUESPED, huesped.getDni());
        elementoReserva.setAttribute(FECHA_NACIMIENTO, huesped.getFechaNacimiento().toString());
        elementoReserva.setAttribute(PLANTA_HABITACION, String.valueOf(reserva.getHabitacion().getPlanta()));
        elementoReserva.setAttribute(PUERTA_HABITACION, String.valueOf(reserva.getHabitacion().getPuerta()));

        // Añadir nodos hijos
        elementoReserva.appendChild(crearElemento(doc, FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva().format(FORMATO_FECHA)));
        elementoReserva.appendChild(crearElemento(doc, FECHA_FIN_RESERVA, reserva.getFechaFinReserva().format(FORMATO_FECHA)));
        elementoReserva.appendChild(crearElemento(doc, REGIMEN, reserva.getRegimen().name()));
        elementoReserva.appendChild(crearElemento(doc, NUMERO_PERSONAS, String.valueOf(reserva.getNumeroPersonas())));
        if (reserva.getCheckIn() != null) {
            elementoReserva.appendChild(crearElemento(doc, CHECKIN, reserva.getCheckIn().format(FORMATO_FECHA_HORA)));
        }
        if (reserva.getCheckOut() != null) {
            elementoReserva.appendChild(crearElemento(doc, CHECKOUT, reserva.getCheckOut().format(FORMATO_FECHA_HORA)));
        }
        elementoReserva.appendChild(crearElemento(doc, PRECIO, String.valueOf(reserva.getPrecio())));

        return elementoReserva;
    }

    // Método para convertir un elemento XML en una reserva
    private Reserva elementToReserva(Element elemento) throws OperationNotSupportedException {
        String nombreHuesped = elemento.getAttribute(NOMBRE_HUESPED);
        String correoHuesped = elemento.getAttribute(CORREO_HUESPED);
        String telefonoHuesped = elemento.getAttribute(TELEFONO_HUESPED);
        String dniHuesped = elemento.getAttribute(DNI_HUESPED);
        LocalDate fechaNacimientoHuesped = LocalDate.parse(elemento.getAttribute(FECHA_NACIMIENTO));
        int plantaHabitacion = Integer.parseInt(elemento.getAttribute(PLANTA_HABITACION));
        int puertaHabitacion = Integer.parseInt(elemento.getAttribute(PUERTA_HABITACION));
        LocalDate fechaInicioReserva = LocalDate.parse(elemento.getElementsByTagName(FECHA_INICIO_RESERVA).item(0).getTextContent(), FORMATO_FECHA);
        LocalDate fechaFinReserva = LocalDate.parse(elemento.getElementsByTagName(FECHA_FIN_RESERVA).item(0).getTextContent(), FORMATO_FECHA);
        Regimen regimen = Regimen.valueOf(elemento.getElementsByTagName(REGIMEN).item(0).getTextContent());
        int numeroPersonas = Integer.parseInt(elemento.getElementsByTagName(NUMERO_PERSONAS).item(0).getTextContent());
        LocalDateTime checkIn = null;
        if (elemento.getElementsByTagName(CHECKIN).getLength() > 0) {
            checkIn = LocalDateTime.parse(elemento.getElementsByTagName(CHECKIN).item(0).getTextContent(), FORMATO_FECHA_HORA);
        }
        LocalDateTime checkOut = null;
        if (elemento.getElementsByTagName(CHECKOUT).getLength() > 0) {
            checkOut = LocalDateTime.parse(elemento.getElementsByTagName(CHECKOUT).item(0).getTextContent(), FORMATO_FECHA_HORA);
        }
        double precio = Double.parseDouble(elemento.getElementsByTagName(PRECIO).item(0).getTextContent());

        // Crear el objeto Huesped
        Huesped huesped = new Huesped(nombreHuesped, dniHuesped, correoHuesped, telefonoHuesped, fechaNacimientoHuesped);

        // Buscar el huésped en la colección de huéspedes
        Huesped huespedExistente = Huespedes.getInstancia().buscar(huesped);
        if (huespedExistente == null) {
            throw new OperationNotSupportedException("ERROR: No se ha encontrado el huésped con DNI: " + dniHuesped);
        }

        Habitacion habitacion = Habitaciones.getInstancia().buscar(new Simple(plantaHabitacion, puertaHabitacion, precio));
        
        Reserva reserva = new Reserva(huespedExistente, habitacion, regimen, fechaInicioReserva, fechaFinReserva, numeroPersonas);
        if (checkIn != null) {
            reserva.setCheckIn(checkIn);
        }
        if (checkOut != null) {
            reserva.setCheckOut(checkOut);
        }
        return reserva;
    }

    private Element crearElemento(Document doc, String nombre, String valor) {
        Element nodo = doc.createElement(nombre);
        nodo.setTextContent(valor);
        return nodo;
    }

    // Método para obtener todas las reservas de un determinado tipo de habitación
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }


        List<Reserva> reservasTipo = new ArrayList<>();
        for (Reserva reserva : coleccionReservas) {
            Habitacion habitacion = reserva.getHabitacion();
            if (habitacion != null && habitacion instanceof Simple && tipoHabitacion == TipoHabitacion.SIMPLE) {
                reservasTipo.add(reserva);
            } else if (habitacion != null && habitacion instanceof Doble && tipoHabitacion == TipoHabitacion.DOBLE) {
                reservasTipo.add(reserva);
            } else if (habitacion != null && habitacion instanceof Triple && tipoHabitacion == TipoHabitacion.TRIPLE) {
                reservasTipo.add(reserva);
            } else if (habitacion != null && habitacion instanceof Suite && tipoHabitacion == TipoHabitacion.SUITE) {
                reservasTipo.add(reserva);
            }
        }
        return reservasTipo;
    }

    // Método para obtener todas las reservas de un determinado huésped
    public List<Reserva> getReservas(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }

        List<Reserva> reservasHuesped = new ArrayList<>();
        Iterator<Reserva> iterador = coleccionReservas.iterator();
        while (iterador.hasNext()) {
            Reserva reserva = iterador.next();
            if (reserva.getHuesped().equals(huesped)) {
                reservasHuesped.add(reserva);
            }
        }
        return reservasHuesped;
    }
    
    // Método para obtener todas las reservas de una determinada habitación
    public List<Reserva> getReservas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        List<Reserva> reservasHabitacion = new ArrayList<>();
        for (Reserva reserva : coleccionReservas) {
            if (reserva.getHabitacion().equals(habitacion)) {
                reservasHabitacion.add(reserva);
            }
        }
        return reservasHabitacion;
    }

    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        List<Reserva> reservasFuturas = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        for (Reserva reserva : coleccionReservas) {
            if (reserva.getHabitacion().equals(habitacion) && reserva.getFechaInicioReserva().isAfter(fechaActual)) {
                reservasFuturas.add(reserva);
            }
        }
        return reservasFuturas;
    }

    // Método para realizar el check-in de una reserva
    public void realizarCheckin(Reserva reserva, LocalDateTime fechaCheckin) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        Reserva reservaEncontrada = buscar(reserva);
        if (reservaEncontrada == null) {
            throw new OperationNotSupportedException("ERROR: No existe la reserva indicada.");
        }
        if (fechaCheckin.isBefore(LocalDateTime.now())) {
            reservaEncontrada.setCheckIn(fechaCheckin);
        }
    }

    // Método para realizar el check-out de una reserva
    public void realizarCheckout(Reserva reserva, LocalDateTime fechaCheckout) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        Reserva reservaEncontrada = buscar(reserva);
        if (reservaEncontrada == null) {
            throw new OperationNotSupportedException("ERROR: No existe la reserva indicada.");
        }
        if (fechaCheckout.isAfter(LocalDateTime.now())) {
            reservaEncontrada.setCheckOut(fechaCheckout);
        }
    }
}
