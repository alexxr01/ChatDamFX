module ChatDamFX {
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
	opens chat to javafx.graphics, javafx.fxml;
	opens client to javafx.graphics, javafx.fxml;
	opens server to javafx.graphics, javafx.fxml;
	opens util to javafx.graphics, javafx.fxml;
}
