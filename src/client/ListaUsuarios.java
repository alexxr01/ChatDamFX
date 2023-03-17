package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import adapters.ItemCell;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaUsuarios implements Initializable {
	@FXML
	private ListView<String> listaUsuariosDisponibles;
	@FXML
	private Text nombreUsuario;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ChatClient chatClient = new ChatClient();
		nombreUsuario.setText("Eres: " + chatClient.getNickName().toString());

		this.listaUsuariosDisponibles.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override public ListCell<String> call(ListView<String> list) {
				return new ItemCell();
			}
		});

		this.listaUsuariosDisponibles.getItems().add("pc1");
		this.listaUsuariosDisponibles.getItems().add("pc2");

		// Detectamos el doble click y abrimos otra sub pestaña
		// esta será el chat para comenzar a hablar.
		listaUsuariosDisponibles.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Queremos que el click se a el izquierdo, y que se pulse 2 veces
				if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					// Llamamos al método que se encargará de ello.
					comenzarChat();
				}    
			}
		});
	}
	

	public void comenzarChat() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/interfaces/chat.fxml"));
			Parent ventana = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Chat con usuario");
			stage.setScene(new Scene(ventana));  
			stage.show();
		} catch (Exception e) {
			System.out.println("Error al cargar la ventana para chatear!");
			e.printStackTrace();
		}
	}

}