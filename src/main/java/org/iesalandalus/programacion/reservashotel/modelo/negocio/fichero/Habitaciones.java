package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.*;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Habitaciones implements IHabitaciones {

	// Atributos
    private static final String RUTA_FICHERO = "datos/habitaciones.xml";
    private static final String RAIZ = "Habitaciones";
    private static final String HABITACION = "Habitacion";
    private static final String IDENTIFICADOR = "Identificador";
    private static final String PLANTA = "Planta";
    private static final String PUERTA = "Puerta";
    private static final String PRECIO = "Precio";
    private static final String CAMAS_INDIVIDUALES = "CamasIndividuales";
    private static final String CAMAS_DOBLES = "CamasDobles";
    private static final String BANOS = "Banos";
    private static final String JACUZZI = "Jacuzzi";
    private static final String TIPO = "Tipo";
    private static final String SIMPLE = "Simple";
    private static final String DOBLE = "Doble";
    private static final String TRIPLE = "Triple";
    private static final String SUITE = "Suite";

    private static Habitaciones instancia;
    private ArrayList<Habitacion> coleccionHabitaciones;
    private UtilidadesXML utilidadesXML;

    public Habitaciones() {
        coleccionHabitaciones = new ArrayList<>();
        utilidadesXML = new UtilidadesXML();
    }

    
    // Método para obtener todas las habitaciones
    public List<Habitacion> get() {
        return new ArrayList<>(coleccionHabitaciones);
    }

    // Método para obtener todas las habitaciones de un tipo específico
    @Override
    public List<Habitacion> get(TipoHabitacion tipoHabitacion) {
        List<Habitacion> habitacionesFiltradas = new ArrayList<>();
        for (Habitacion habitacion : coleccionHabitaciones) {
            if (tipoHabitacion == TipoHabitacion.SIMPLE && habitacion instanceof Simple) {
                habitacionesFiltradas.add(habitacion);
            } else if (tipoHabitacion == TipoHabitacion.DOBLE && habitacion instanceof Doble) {
                habitacionesFiltradas.add(habitacion);
            } else if (tipoHabitacion == TipoHabitacion.TRIPLE && habitacion instanceof Triple) {
                habitacionesFiltradas.add(habitacion);
            } else if (tipoHabitacion == TipoHabitacion.SUITE && habitacion instanceof Suite) {
                habitacionesFiltradas.add(habitacion);
            }
        }
        return habitacionesFiltradas;
    }

    // Método para obtener el tamaño de la colección de habitaciones
    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    // Insertar la habitacion
    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if (coleccionHabitaciones.contains(habitacion)) {
            throw new IllegalArgumentException("ERROR: La habitación ya existe.");
        }
        coleccionHabitaciones.add(habitacion);
        try {
            escribirXML();
        } catch (IOException e) {
            System.out.println("Error al escribir las habitaciones en el archivo XML: " + e.getMessage());
        }
    }

    // borrar una habitación
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }
        if (!coleccionHabitaciones.remove(habitacion)) {
            throw new IllegalArgumentException("ERROR: La habitación a borrar no existe.");
        }
        try {
            escribirXML(); // Guardar los cambios en el archivo XML
        } catch (IOException e) {
            System.out.println("Error al escribir las habitaciones en el archivo XML: " + e.getMessage());
        }
    }

    // buscar una habitación en la colección
    public Habitacion buscar(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }
        for (Habitacion h : coleccionHabitaciones) {
            if (h.equals(habitacion)) {
                return h;
            }
        }
        return null;
    }
    
    
    // Método para cargar las habitaciones desde el archivo XML al inicio del programa
    @Override
    public void comenzar() {
        coleccionHabitaciones.clear();
        try {
            leerXML();
        } catch (IOException e) {
            System.out.println("Error al leer las habitaciones desde el archivo XML: " + e.getMessage());
        }
    }


    // Método para guardar las habitaciones en el archivo XML al terminar el programa
    @Override
    public void terminar() {
        try {
            escribirXML();
        } catch (IOException e) {
            System.out.println("Error al escribir las habitaciones en el archivo XML: " + e.getMessage());
        }
    }
    
    public static Habitaciones getInstancia() {
        if (instancia == null) {
            instancia = new Habitaciones();
        }
        return instancia;
    }
    
    // Método para leer las habitaciones desde el archivo XML
    private void leerXML() throws IOException {
        File archivoXML = new File(RUTA_FICHERO);
        if (!archivoXML.exists()) {
            System.out.println("El archivo XML de habitaciones no existe.");
            return;
        }
        try {
            Document documento = utilidadesXML.xmlToDom(archivoXML);
            Element raiz = documento.getDocumentElement();
            NodeList listaHabitaciones = raiz.getElementsByTagName(HABITACION);
            for (int i = 0; i < listaHabitaciones.getLength(); i++) {
                Node nodoHabitacion = listaHabitaciones.item(i);
                if (nodoHabitacion.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoHabitacion = (Element) nodoHabitacion;
                    Habitacion habitacion = elementToHabitacion(elementoHabitacion);
                    coleccionHabitaciones.add(habitacion);
                }
            }
        } catch (Exception e) {
            throw new IOException("Error al leer las habitaciones desde el archivo XML: " + e.getMessage());
        }
    }

    // Método para convertir un elemento XML en una habitación
    private Habitacion elementToHabitacion(Element elementoHabitacion) {
        String tipoHabitacion = elementoHabitacion.getAttribute(TIPO);
        int planta = Integer.parseInt(elementoHabitacion.getElementsByTagName(PLANTA).item(0).getTextContent());
        int puerta = Integer.parseInt(elementoHabitacion.getElementsByTagName(PUERTA).item(0).getTextContent());
        double precio = Double.parseDouble(elementoHabitacion.getElementsByTagName(PRECIO).item(0).getTextContent());

        switch (tipoHabitacion) {
            case SIMPLE:
                return new Simple(planta, puerta, precio);
            case DOBLE:
                NodeList camasIndividualesNodeList = elementoHabitacion.getElementsByTagName(CAMAS_INDIVIDUALES);
                NodeList camasDoblesNodeList = elementoHabitacion.getElementsByTagName(CAMAS_DOBLES);
                int camasIndividuales = camasIndividualesNodeList.getLength() > 0 ? Integer.parseInt(camasIndividualesNodeList.item(0).getTextContent()) : 0;
                int camasDobles = camasDoblesNodeList.getLength() > 0 ? Integer.parseInt(camasDoblesNodeList.item(0).getTextContent()) : 0;
                return new Doble(planta, puerta, precio, camasIndividuales, camasDobles);
            case TRIPLE:
                NodeList banosNodeList = elementoHabitacion.getElementsByTagName(BANOS);
                int banos = banosNodeList.getLength() > 0 ? Integer.parseInt(banosNodeList.item(0).getTextContent()) : 0;
                NodeList camasIndividualesTripleNodeList = elementoHabitacion.getElementsByTagName(CAMAS_INDIVIDUALES);
                NodeList camasDoblesTripleNodeList = elementoHabitacion.getElementsByTagName(CAMAS_DOBLES);
                int camasIndividualesTriple = camasIndividualesTripleNodeList.getLength() > 0 ? Integer.parseInt(camasIndividualesTripleNodeList.item(0).getTextContent()) : 0;
                int camasDoblesTriple = camasDoblesTripleNodeList.getLength() > 0 ? Integer.parseInt(camasDoblesTripleNodeList.item(0).getTextContent()) : 0;
                return new Triple(planta, puerta, precio, banos, camasIndividualesTriple, camasDoblesTriple);
            case SUITE:
                NodeList jacuzziNodeList = elementoHabitacion.getElementsByTagName(JACUZZI);
                NodeList banosSuiteNodeList = elementoHabitacion.getElementsByTagName(BANOS);
                boolean jacuzzi = jacuzziNodeList.getLength() > 0 ? Boolean.parseBoolean(jacuzziNodeList.item(0).getTextContent()) : false;
                int banosSuite = banosSuiteNodeList.getLength() > 0 ? Integer.parseInt(banosSuiteNodeList.item(0).getTextContent()) : 0;
                return new Suite(planta, puerta, precio, banosSuite, jacuzzi);
            default:
                throw new IllegalArgumentException("Tipo de habitación no válido.");
        }
    }


    // Método para crear un elemento XML a partir de una habitación
    private Element crearElemento(Document documento, String nombre, String valor) {
        Element elemento = documento.createElement(nombre);
        elemento.appendChild(documento.createTextNode(valor));
        return elemento;
    }
    
    // Método para convertir una habitación en un elemento XML
    private Element habitacionToElement(Document documento, Habitacion habitacion) {
        Element elementoHabitacion = documento.createElement(HABITACION);
        elementoHabitacion.setAttribute(IDENTIFICADOR, habitacion.getIdentificador());
        elementoHabitacion.setAttribute(TIPO, habitacion.getClass().getSimpleName());
        elementoHabitacion.appendChild(crearElemento(documento, PLANTA, String.valueOf(habitacion.getPlanta())));
        elementoHabitacion.appendChild(crearElemento(documento, PUERTA, String.valueOf(habitacion.getPuerta())));
        elementoHabitacion.appendChild(crearElemento(documento, PRECIO, String.valueOf(habitacion.getPrecio())));

        if (habitacion instanceof Doble) {
            Doble doble = (Doble) habitacion;
            elementoHabitacion.appendChild(crearElemento(documento, CAMAS_INDIVIDUALES, String.valueOf(doble.getNumCamasIndividuales())));
            elementoHabitacion.appendChild(crearElemento(documento, CAMAS_DOBLES, String.valueOf(doble.getNumCamasDobles())));
        } else if (habitacion instanceof Triple) {
            Triple triple = (Triple) habitacion;
            elementoHabitacion.appendChild(crearElemento(documento, BANOS, String.valueOf(triple.getNumBanos())));
            elementoHabitacion.appendChild(crearElemento(documento, CAMAS_INDIVIDUALES, String.valueOf(triple.getNumCamasIndividuales())));
            elementoHabitacion.appendChild(crearElemento(documento, CAMAS_DOBLES, String.valueOf(triple.getNumCamasDobles())));
        } else if (habitacion instanceof Suite) {
            Suite suite = (Suite) habitacion;
            elementoHabitacion.appendChild(crearElemento(documento, JACUZZI, String.valueOf(suite.isTieneJacuzzi())));
            elementoHabitacion.appendChild(crearElemento(documento, BANOS, String.valueOf(suite.getNumBanos())));
        }

        return elementoHabitacion;
    }
    
    // Método para escribir las habitaciones en un archivo XML
    private void escribirXML() throws IOException {
        try {
            Document documento = utilidadesXML.crearDomVacio(RAIZ);
            Element elementoRaiz = documento.getDocumentElement();
            while (elementoRaiz.hasChildNodes()) {
                elementoRaiz.removeChild(elementoRaiz.getFirstChild());
            }
            for (Habitacion habitacion : coleccionHabitaciones) {
                Element elementoHabitacion = habitacionToElement(documento, habitacion);
                elementoRaiz.appendChild(elementoHabitacion);
            }

            // Configurar la salida para formatear el XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Crear la fuente XML y realizar la transformación
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File(RUTA_FICHERO));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new IOException("Error al escribir las habitaciones en el archivo XML: " + e.getMessage());
        }
    }
    
}
