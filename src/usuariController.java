import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class usuariController {
    
    @FXML
    private Label title, subtitle,phone;

    @FXML
    private void handleMenuAction() {
        // ControllerDades c0 = (ControllerDades) UtilsViews.getController("dades");
        // c0.loadConsoleInfo("consola",title.getText());
        // c0.setParams(type, param);
        // UtilsViews.setViewAnimating("dades");
        // ControllerConsoles c1 = (ControllerConsoles) UtilsViews.getController("consoles");
        // c1.clear();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }

    public void setPhone(String phone){
        this.phone.setText(phone);
    }

}
