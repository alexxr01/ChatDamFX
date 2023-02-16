package client;

import java.net.URL;
import java.util.ResourceBundle;

import adapters.ItemCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ListaUsuarios implements Initializable {

    @FXML
    private ListView<String> listaUsuariosDisponibles;
    @FXML
    private Text nombreUsuario;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nombreUsuario.setText("Eres: pc1");
		
		this.listaUsuariosDisponibles.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override public ListCell<String> call(ListView<String> list) {
                return new ItemCell();
            }
        });        
		
		this.listaUsuariosDisponibles.getItems().add("pc1");
		this.listaUsuariosDisponibles.getItems().add("pc2");
//		do {
//			listaUsuariosDisponibles.getItems().add(chatClient.getNickName());
//		} while (chatClient != null);
		
	}
	
}