import java.io.IOException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class ControllerConsoles {
    
    private String type,param;

    @FXML
    private VBox yPane = new VBox();

    
    @FXML
    private ProgressIndicator loading;

    private void loadConsolesCallback (String response) {

        JSONObject objResponse = new JSONObject(response);

        if (objResponse.getString("status").equals("OK")) {

            JSONArray JSONlist = objResponse.getJSONArray("result");
            URL resource = this.getClass().getResource("./assets/listItem.fxml");

            // Clear the list of consoles
            yPane.getChildren().clear();

            // Add received consoles from the JSON to the list
            for (int i = 0; i < JSONlist.length(); i++) {

                // Get console information
                JSONObject console = JSONlist.getJSONObject(i);

                try {
                    // Load template and set controller
                    FXMLLoader loader = new FXMLLoader(resource);
                    Parent itemTemplate = loader.load();
                    ControllerItem itemController = loader.getController();
                
                    // Fill template with console information
                    itemController.setTitle(console.getString("name"));
                    itemController.setSubtitle(console.getString("processor"));
                    itemController.setColor(console.getString("color"));
                    itemController.setParams(this.type, this.param);
                    
                    itemController.setImgConsole(console.getString("image"));
                    
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
    public void loadConsoles(String type,String param){
        JSONObject obj = new JSONObject("{}");
        obj.put("type", type);
        obj.put("name", param);

        this.type=type;
        this.param=param;

        loading.setVisible(true);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + ":" + Main.port + "/dades", obj.toString(), (response) -> {
            loadConsolesCallback(response);
            loading.setVisible(false);
        });
    }

    @FXML
    private void back() {
        switch (type) {
            case "color":
            yPane.getChildren().clear();
            colorsController c1 = (colorsController) UtilsViews.getController("colors");
            UtilsViews.setViewAnimating("colors");
            c1.loadColors();
                break;
        
            case "marca":
            yPane.getChildren().clear();
            ControllerMarques c2 = (ControllerMarques) UtilsViews.getController("marques");
            UtilsViews.setViewAnimating("marques");
            c2.loadMarques();
                break;
            case "procesador":
                yPane.getChildren().clear();
                ControllerProcesadors c3 = (ControllerProcesadors) UtilsViews.getController("procesadors");
                UtilsViews.setViewAnimating("procesadors");
                c3.loadProcesadors();
                break;
            default:
            yPane.getChildren().clear();
            UtilsViews.setViewAnimating("filtres");
                break;
        }
    }
    public void clear() {
        this.yPane.getChildren().clear();
    }
    
}
