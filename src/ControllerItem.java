import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class ControllerItem {

    private String type,param;

    @FXML
    private ImageView imgConsole;
    
    @FXML
    private Label title, subtitle;

    @FXML
    private Circle coloredShape;

    @FXML
    private void handleMenuAction() {
        ControllerDades c0 = (ControllerDades) UtilsViews.getController("dades");
        c0.loadConsoleInfo("consola",title.getText());
        c0.setParams(type, param);
        UtilsViews.setViewAnimating("dades");
        ControllerConsoles c1 = (ControllerConsoles) UtilsViews.getController("consoles");
        c1.clear();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }

    public void setColor(String color) {
        coloredShape.setStyle("-fx-fill: " + color);
    }

    public void setParams(String type,String param){
        this.type=type;
        this.param=param;
    }

    public void setImgConsole(String path) {
        Image image = new Image(Main.protocol + "://" + Main.host + ":" + Main.port + "/" + path);
        this.imgConsole.setImage(image);
        this.imgConsole.setFitWidth(90);
        this.imgConsole.setPreserveRatio(true);
    }
}
