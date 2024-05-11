package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;

public class LanzadorVentanaPrincipal extends Application {
	
	//Método comenzar
    public static void comenzar(){
        launch();
    }

    //Método start para inicial la ventana principal
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaPrincipal.fxml"));
        Parent raiz=fxmlLoader.load();
        Scene scene = new Scene(raiz, 900, 600);
        scene.getStylesheets().add(LocalizadorRecursos.class.getResource("estilos/estilos.css").toExternalForm());
        escenarioPrincipal.setTitle("Hotel I.E.S. Al-Ándalus");
        escenarioPrincipal.setScene(scene);
        escenarioPrincipal.setOnCloseRequest(e->confirmarSalida(escenarioPrincipal,e));
        escenarioPrincipal.show();
    }

    //Método para confirmar la salida de la aplicación
    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e)
    {
        if (Dialogos.mostrarDialogoConfirmacion("Hotel I.E.S. Al-Ándalus", "Pulse ``Aceptar´´ o presiona ENTER si desea cerrar la aplicación."))
        {
            escenarioPrincipal.close();
        }
        else
            e.consume();

    }

}
