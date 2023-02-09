module ChatDamFX {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens chat to javafx.graphics, javafx.fxml;
	opens client to javafx.graphics, javafx.fxml;
	opens server to javafx.graphics, javafx.fxml;
	opens util to javafx.graphics, javafx.fxml;
}
