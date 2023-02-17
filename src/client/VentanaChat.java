package client;

import java.net.URL;
import java.util.ResourceBundle;

import adapters.ItemCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class VentanaChat implements Initializable {
	// Propiedades de la ventana JavaFX
	@FXML
	private Button botonEnviar;
	@FXML
	private TextField escribirMensaje;
	@FXML
	private ListView<String> listaMensajesChat;
	@FXML
	private Text txtCambiarUsuario;
	// Accion a realizar al pulsar el boton
	@FXML
	void enviarMensaje(ActionEvent event) {
		// Recogemos el texto de la casilla
		//    	mensaje = 
		// Lo añadimos a la lista, que para nosotros será nuestro organizador
		// de mensajes. Como WhatsApp
		this.listaMensajesChat.getItems().add(escribirMensaje.getText());
		// Borramos el contenido para escribir el siguiente mensaje
		escribirMensaje.clear();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.listaMensajesChat.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override public ListCell<String> call(ListView<String> list) {
				return new ItemCell();
			}
		});
		// Indicamos el usuario que eres (orientativo)
		txtCambiarUsuario.setText("pcX");
	}
}