import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class ControllerMarquesButton {

    @FXML
    private Label title;

    @FXML
    private void handleMenuAction() {
        ControllerConsoles c0 = (ControllerConsoles) UtilsViews.getController("consoles");
        c0.loadConsoles("marca",title.getText());
        UtilsViews.setViewAnimating("consoles");
        ControllerMarques c1 = (ControllerMarques) UtilsViews.getController("marques");
        c1.clean();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
