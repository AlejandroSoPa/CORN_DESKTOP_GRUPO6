import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class transacioController {
    
    @FXML
    private Label title;

    public void setTitle(String title) {
        this.title.setText(title);
    }


}
