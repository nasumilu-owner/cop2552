module cop2552.dice {
	
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	
    opens com.nasumilu.cop2552.dice to javafx.fxml;
    exports com.nasumilu.cop2552.dice;
}