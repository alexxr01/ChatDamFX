package client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ListaUsuarios implements Initializable {

    @FXML
    private ListView<String> listaUsuariosDisponibles;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ChatClient chatClient = new ChatClient();
		
		do {
			listaUsuariosDisponibles.getItems().add(chatClient.getNickName());
		} while (chatClient != null);
		
	}
}