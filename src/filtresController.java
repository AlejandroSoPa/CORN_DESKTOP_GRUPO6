import javafx.fxml.FXML;

public class filtresController {
    
    @FXML
    private void marca() {
        UtilsViews.setViewAnimating("marques");
        ControllerMarques c0 = (ControllerMarques) UtilsViews.getController("marques");
        c0.loadMarques();
    }

    @FXML
    private void color() {
        UtilsViews.setViewAnimating("colors");
        colorsController c0 = (colorsController) UtilsViews.getController("colors");
        c0.loadColors();
    }

    @FXML
    private void procesador() {
        UtilsViews.setViewAnimating("procesadors");
        ControllerProcesadors c0 = (ControllerProcesadors) UtilsViews.getController("procesadors");
        c0.loadProcesadors();
    }

    @FXML
    private void tots() {
        UtilsViews.setViewAnimating("consoles");
        ControllerConsoles c0 = (ControllerConsoles) UtilsViews.getController("consoles");
        c0.loadConsoles("consoles","");
    }

    @FXML
    private void back() {
        UtilsViews.setViewAnimating("login");
        Main.port=0;
        Main.host="";
    }
}
