package adapters;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ItemCell extends ListCell<String> {

	@Override
	protected void updateItem(String name, boolean empty) {
		super.updateItem(name, empty);
		if(empty || name == null) {
			  this.setText(null);
              this.setGraphic(null);
		} else {
			// Generamos un array de avatares
			String[] avatares = new String[7];
			// Damos lugar a los avatares
			{
				avatares[0] = "avatar1.png";
				avatares[1] = "avatar2.png";
				avatares[2] = "avatar3.png";
				avatares[3] = "avatar4.png";
				avatares[4] = "avatar5.png";
				avatares[5] = "avatar6.png";
				avatares[6] = "avatar7.png";
			}
			// Generamos un numero aleatorio entre el total de los avatares
			int seleccionarAvatar = (int) (Math.random()*7);
			
			//String resource = Thread.currentThread().getContextClassLoader().getResource("resources/hombre.png").toString();
			Image image = new Image(avatares[seleccionarAvatar]);
			ImageView imageView = new ImageView();			
			imageView.setPreserveRatio(true);
			imageView.setImage(image);
			imageView.setFitHeight(10);
			imageView.setFitWidth(10);
			this.setGraphic(imageView);
			this.setHeight(32);
			this.setMaxHeight(USE_PREF_SIZE);
			this.setText(name);	
		}
	}

}
