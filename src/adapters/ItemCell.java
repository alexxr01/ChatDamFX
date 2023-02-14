package adapters;

import javafx.scene.AccessibleAction;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class ItemCell extends ListCell<String> {

	@Override
	protected void updateItem(String name, boolean empty) {
		super.updateItem(name,empty);
		if(empty || name==null) {
			  this.setText(null);
              this.setGraphic(null);
		}else {
			//String resource = Thread.currentThread().getContextClassLoader().getResource("resources/hombre.png").toString();
			Image image = new Image("/img/hombre.png");
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
