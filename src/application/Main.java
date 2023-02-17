package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/interfaces/principal.fxml"));
	        Scene scene = new Scene(fxmlLoader.load());
	        stage.setTitle("ChatDamFX");
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();  	       
	        Thread.currentThread().setName("Hilo de la interfaz");
		} catch (Exception e) {
			System.out.println("Error al iniciar la interfaz principal.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread.currentThread().setName("Hilo de la aplicación");
		launch(args);
	}
}
