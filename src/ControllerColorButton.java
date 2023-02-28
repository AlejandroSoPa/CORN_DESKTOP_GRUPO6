import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerColorButton {
    @FXML
    private Label title;

    @FXML
    private void handleMenuAction() {
        ControllerConsoles c0 = (ControllerConsoles) UtilsViews.getController("consoles");
        c0.loadConsoles("color",title.getText());
        UtilsViews.setViewAnimating("consoles");
        colorsController c1 = (colorsController) UtilsViews.getController("colors");
        c1.clean();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
