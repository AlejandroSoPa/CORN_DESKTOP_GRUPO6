import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// TODO THIS CONTROLLER WORKS AND ITS FINISHED!!!
public class ControllerDades  {
    
    private String type,param;

    @FXML
    private Label txtName, txtDate, txtBrand;

    @FXML
    private ImageView imgConsole;

    @FXML
    private ProgressIndicator loading;

    public void loadConsoleInfo (String type,String param)  {

        JSONObject obj = new JSONObject("{}");
        obj.put("type", type);
        obj.put("name", param);

        loading.setVisible(true);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + ":" + Main.port + "/dades", obj.toString(), (response) -> {
            loadConsoleInfoCallback(response);
            loading.setVisible(false);
        });
    }

    private void loadConsoleInfoCallback (String response) {

        JSONObject objResponse = new JSONObject(response);
        
        if (objResponse.getString("status").equals("OK")) {

            JSONObject console = objResponse.getJSONObject("result");

            // Fill console info with the received data
            txtName.setText(console.getString("name"));
            txtDate.setText(console.getString("date"));
            txtBrand.setText(console.getString("brand"));
    
            try{
                // Load console image
                Image image = new Image(Main.protocol + "://" + Main.host + ":" + Main.port + "/" + console.getString("image")); 
                imgConsole.setImage(image); 
                imgConsole.setFitWidth(200);
                imgConsole.setPreserveRatio(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //showError();
            System.out.println("ERROR#DadesController");
        }
    }

   

    @FXML
    private void back() {
        txtName.setText("");
        txtDate.setText("");
        txtBrand.setText("");
        imgConsole.setImage(null);

        ControllerConsoles c0 = (ControllerConsoles) UtilsViews.getController("consoles");
        c0.loadConsoles(type, param);
        UtilsViews.setViewAnimating("consoles");
    }

    public void setParams(String type,String param){
        this.type=type;
        this.param=param;
    }
}
