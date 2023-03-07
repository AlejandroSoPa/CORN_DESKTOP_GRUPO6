import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerProcesadorsButton {
    @FXML
    private Label title;

    @FXML
    private void handleMenuAction() {
        ControllerConsoles c0 = (ControllerConsoles) UtilsViews.getController("consoles");
        c0.loadConsoles("procesador",title.getText());
        UtilsViews.setViewAnimating("consoles");
        ControllerProcesadors c1 = (ControllerProcesadors) UtilsViews.getController("procesadors");
        c1.clean();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
