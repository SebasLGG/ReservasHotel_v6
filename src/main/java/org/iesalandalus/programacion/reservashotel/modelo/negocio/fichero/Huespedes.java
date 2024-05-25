package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;

import java.util.Iterator;


import javax.naming.OperationNotSupportedException;


// Clase Huespedes
public class Huespedes implements IHuespedes {

	// Atributos
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RUTA_FICHERO = "datos/huespedes.xml";
    private static final String RAIZ = "Huespedes";
    private static final String HUESPED = "Huesped";
    private static final String NOMBRE = "Nombre";
    private static final String DNI = "Dni";
    private static final String CORREO = "Correo";
    private static final String TELEFONO = "Telefono";
    private static final String FECHA_NACIMIENTO = "FechaNacimiento";
    
    
    // Atributo instancia para el patrón Singleton
    private static Huespedes instancia;
    // Agrega una instancia de UtilidadesXML como atributo de la clase
    private UtilidadesXML utilidadesXML;

    // Atributos para la clase.
    private ArrayList<Huesped> coleccionHuespedes;

    // Constructor que inicializa la lista de huespedes.
    public Huespedes() {
        coleccionHuespedes = new ArrayList<>();
        utilidadesXML = new UtilidadesXML();
    }

    // Método que devuelve una copia profunda de la lista.
    public List<Huesped> get() {
        return copiaProfundaHuespedes();
    }

    // Método privado que realiza una copia de la lista.
    private List<Huesped> copiaProfundaHuespedes() {
        List<Huesped> copia = new ArrayList<>();
        Iterator<Huesped> iterador = coleccionHuespedes.iterator();
        while (iterador.hasNext()) {
            Huesped huesped = iterador.next();
            copia.add(new Huesped(huesped));
        }
        return copia;
    }


    // Método que devuelve el tamaño actual de la lista.
    public int getTamano() {
        return coleccionHuespedes.size();
    }

    // Método para insertar un huesped en la lista y en el archivo XML
    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }

        // Verificar si el huésped ya existe en la colección
        if (buscar(huesped) != null) {
            throw new OperationNotSupportedException("ERROR: El huésped ya existe.");
        }

        // Agregar el nuevo huésped a la colección
        coleccionHuespedes.add(huesped);

        // Agregar el nuevo huésped al archivo XML
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(new File(RUTA_FICHERO));
            documento.getDocumentElement().normalize();

            Element raiz = documento.getDocumentElement();
            Element elementoHuesped = huespedToElement(documento, huesped);
            raiz.appendChild(elementoHuesped);

            // Guardar los cambios en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File(RUTA_FICHERO));
            transformer.transform(source, result);
        } catch (Exception e) {
            throw new OperationNotSupportedException("ERROR: No se pudo insertar el huésped en el archivo XML: " + e.getMessage());
        }
    }



    // Método que busca un huesped en la lista.
    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }

        Iterator<Huesped> iterador = coleccionHuespedes.iterator();
        while (iterador.hasNext()) {
            Huesped h = iterador.next();
            if (h.equals(huesped)) {
                return h;
            }
        }
        return null;
    }


    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }

        if (!coleccionHuespedes.remove(huesped)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }

        // Borrar el huésped del archivo XML
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(new File(RUTA_FICHERO));
            documento.getDocumentElement().normalize();

            Element raiz = documento.getDocumentElement();
            NodeList listaHuespedes = raiz.getElementsByTagName(HUESPED);

            for (int i = 0; i < listaHuespedes.getLength(); i++) {
                Node nodoHuesped = listaHuespedes.item(i);
                if (nodoHuesped.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoHuesped = (Element) nodoHuesped;
                    if (elementoHuesped.getAttribute(DNI).equals(huesped.getDni())) {
                        raiz.removeChild(elementoHuesped);
                        break;
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File(RUTA_FICHERO));
            transformer.transform(source, result);
        } catch (Exception e) {
            throw new OperationNotSupportedException("ERROR: No se pudo borrar el huésped del archivo XML: " + e.getMessage());
        }
    }
    
    // Método comenzar: realiza la lectura de los huéspedes almacenados en el fichero XML
    @Override
    public void comenzar() {
        coleccionHuespedes.clear(); // Inicializa la lista de huéspedes vacía
        try {
            leerXML();
        } catch (IOException e) {
            System.out.println("Error al leer los huéspedes desde el archivo XML: " + e.getMessage());
        }
    }

    // Método terminar: realiza la escritura de los huéspedes en el fichero XML
    @Override
    public void terminar() {
        try {
            escribirXML();
        } catch (IOException e) {
            System.out.println("Error al escribir los huéspedes en el archivo XML: " + e.getMessage());
        }
    }
    
    
    public static Huespedes getInstancia() {
        if (instancia == null) {
            instancia = new Huespedes();
        }
        return instancia;
    }
    
    // Método para leer los huéspedes desde el archivo XML
    private void leerXML() throws IOException {
        File archivoXML = new File(RUTA_FICHERO);
        if (!archivoXML.exists()) {
            System.out.println("El archivo XML de huéspedes no existe.");
            return;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(archivoXML);
            documento.getDocumentElement().normalize();

            Element raiz = documento.getDocumentElement();
            NodeList listaHuespedes = raiz.getElementsByTagName(HUESPED);

            for (int i = 0; i < listaHuespedes.getLength(); i++) {
                Node nodoHuesped = listaHuespedes.item(i);
                if (nodoHuesped.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoHuesped = (Element) nodoHuesped;
                    Huesped huesped = elementToHuesped(elementoHuesped);
                    // Verificar si el huésped ya existe antes de insertarlo
                    if (buscar(huesped) == null) {
                        coleccionHuespedes.add(huesped);
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException("Error al leer los huéspedes desde el archivo XML: " + e.getMessage());
        }
    }
    
    // Método para convertir un elemento XML en un objeto Huesped
    private Huesped elementToHuesped(Element elementoHuesped) {
        String dni = elementoHuesped.getAttribute("Dni");
        String nombre = elementoHuesped.getElementsByTagName("Nombre").item(0).getTextContent();
        String correo = elementoHuesped.getElementsByTagName("Correo").item(0).getTextContent();
        String telefono = elementoHuesped.getElementsByTagName("Telefono").item(0).getTextContent();
        LocalDate fechaNacimiento = LocalDate.parse(elementoHuesped.getElementsByTagName("FechaNacimiento").item(0).getTextContent(), FORMATO_FECHA);

        return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
    }
    
    
     
    // Método para convertir un objeto Huesped en un elemento XML
    private Element huespedToElement(Document documento, Huesped huesped) {
        Element elementoHuesped = documento.createElement(HUESPED);
        elementoHuesped.setAttribute(DNI, huesped.getDni());

        Element elementoNombre = documento.createElement(NOMBRE);
        elementoNombre.appendChild(documento.createTextNode(huesped.getNombre()));
        elementoHuesped.appendChild(elementoNombre);

        Element elementoCorreo = documento.createElement(CORREO);
        elementoCorreo.appendChild(documento.createTextNode(huesped.getCorreo()));
        elementoHuesped.appendChild(elementoCorreo);

        Element elementoTelefono = documento.createElement(TELEFONO);
        elementoTelefono.appendChild(documento.createTextNode(huesped.getTelefono()));
        elementoHuesped.appendChild(elementoTelefono);

        Element elementoFechaNacimiento = documento.createElement(FECHA_NACIMIENTO);
        elementoFechaNacimiento.appendChild(documento.createTextNode(huesped.getFechaNacimiento().format(FORMATO_FECHA)));
        elementoHuesped.appendChild(elementoFechaNacimiento);

        return elementoHuesped;
    }
    
    // Método para escribir los huéspedes en el archivo XML utilizando UtilidadesXML
    private void escribirXML() throws IOException {
        try {
            Document documento = utilidadesXML.crearDomVacio(RAIZ);
            Element elementoRaiz = documento.getDocumentElement();
            List<Huesped> listaHuespedes = get();
            for (Huesped huesped : listaHuespedes) {
                Element elementoHuesped = huespedToElement(documento, huesped);
                elementoRaiz.appendChild(elementoHuesped);
            }
            utilidadesXML.domToXml(documento, new File(RUTA_FICHERO));
        } catch (ParserConfigurationException | TransformerException e) {
            throw new IOException("Error al escribir los huéspedes en el archivo XML: " + e.getMessage());
        }
    }
    
    
    
}