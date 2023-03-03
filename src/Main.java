import org.json.JSONObject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {

    public static UtilsWS socketClient;

    // Local test
    public static int port = 3000;
    public static String protocol = "http";
    public static String host = "localhost";
    public static String protocolWS = "ws";

    // Exemple de configuració per Railway
    // public static int port = 8015;
    // public static String protocol = "https";
    // public static String host = "cornapigrupo6-production.up.railway.app";
    // public static String protocolWS = "wss";

    // Camps JavaFX a modificar
    public static Label consoleName = new Label();
    public static Label consoleDate = new Label();
    public static Label consoleBrand = new Label();
    public static ImageView imageView = new ImageView(); 

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {

        final int windowWidth = 700;
        final int windowHeight = 400;

        UtilsViews.parentContainer.setStyle("-fx-font: 14 arial;");

        UtilsViews.addView(getClass(), "usuaris", "./assets/usuaris.fxml");

        //test 
        usuarisController c0=(usuarisController) UtilsViews.getController("usuaris");
        c0.loadUsers();

        // UtilsViews.addView(getClass(), "filtres", "./assets/filtres.fxml");
        // UtilsViews.addView(getClass(), "colors", "./assets/colors.fxml");
        // UtilsViews.addView(getClass(), "marques", "./assets/marques.fxml");
        // UtilsViews.addView(getClass(), "procesadors", "./assets/procesadors.fxml");
        // UtilsViews.addView(getClass(), "consoles", "./assets/consoles.fxml");        
        // UtilsViews.addView(getClass(), "dades", "./assets/dades.fxml");
        
        Scene scene = new Scene(UtilsViews.parentContainer);
        
        stage.setScene(scene);
        stage.onCloseRequestProperty(); // Call close method when closing window
        stage.setTitle("CORN DESKTOP");
        stage.setMinWidth(windowWidth);
        stage.setMinHeight(windowHeight);
        stage.show();

        // Image icon = new Image("file:./assets/icon.png");
        // stage.getIcons().add(icon);
    }

    @Override
    public void stop() { 
        // socketClient.close();
        
        System.exit(1); // Kill all executor services
    }
}
