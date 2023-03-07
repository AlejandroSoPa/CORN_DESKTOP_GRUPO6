import java.io.IOException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

public class ControllerProcesadors {
    @FXML
    private VBox yPane = new VBox();

    
    @FXML
    private ProgressIndicator loading;

    private void loadProcesadorsCallback (String response) {

        JSONObject objResponse = new JSONObject(response);

        if (objResponse.getString("status").equals("OK")) {

            JSONArray JSONlist = objResponse.getJSONArray("result");
            URL resource = this.getClass().getResource("./assets/procesadorsbutton.fxml");

            // Clear the list of consoles
            yPane.getChildren().clear();

            // Add received consoles from the JSON to the list
            for (int i = 0; i < JSONlist.length(); i++) {

                try {
                    // Load template and set controller
                    FXMLLoader loader = new FXMLLoader(resource);
                    Parent itemTemplate = loader.load();
                    ControllerProcesadorsButton itemControlle = loader.getController();
                
                    // Fill template with console information
                    itemControlle.setTitle(JSONlist.getString(i));
                    
                    // Add template to the list
                    yPane.getChildren().add(itemTemplate);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            System.out.println("ERROR#ConsolaController");
        }
    }
    public void loadProcesadors(){
        JSONObject obj = new JSONObject("{}");
        obj.put("type", "procesadors");

        loading.setVisible(true);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + ":" + Main.port + "/dades", obj.toString(), (response) -> {
           
            this.loadProcesadorsCallback(response);
            loading.setVisible(false);
        });
    }

    @FXML
    private void back() {
        yPane.getChildren().clear();
        UtilsViews.setViewAnimating("filtres");
    }
    public void clean() {
        this.yPane.getChildren().clear();
    }
}
