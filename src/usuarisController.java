import java.io.IOException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

public class usuarisController {
    @FXML
    private VBox yPane = new VBox();

    @FXML
    public VBox yPane2 = new VBox();

    @FXML
    public ProgressIndicator loading;

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label wallet;

    private void loadUsersCallback (String response) {

        JSONObject objResponse = new JSONObject(response);

        if (objResponse.getString("status").equals("OK")) {

            JSONArray JSONlist = objResponse.getJSONArray("result");
            URL resource = this.getClass().getResource("./assets/usuari.fxml");

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
                    usuariController itemController = loader.getController();
                
                    // Fill template with console information
                    itemController.setTitle(console.getString("name"));
                    itemController.setSubtitle(console.getString("surname"));
                    itemController.setPhone(String.valueOf(console.getInt("phone")));
                    
                    // Add template to the list
                    yPane.getChildren().add(itemTemplate);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            System.out.println("ERROR#usuarisController");
        }
    }
    public void loadUsers(){
        JSONObject obj = new JSONObject("{}");
        loading.setVisible(true);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/API/get_profiles",obj.toString(), (response) -> {
            System.out.println(response);
            loadUsersCallback(response);
            loading.setVisible(false);
        });
    }

    public void setName(String name) {
        this.name.setText(name);
    }
    public void setSurname(String surname) {
        this.surname.setText(surname);
    }
    public void setPhone(String phone) {
        this.phone.setText(phone);
    }
    public void setEmail(String email) {
        this.email.setText(email); 
    }
    public void setWallet(String wallet) {
        this.wallet.setText(wallet); 
    }

    public void clear(){
        this.name.setText("");
        this.surname.setText("");
        this.phone.setText("");
        this.email.setText(""); 
        this.wallet.setText("");
        this.yPane2.getChildren().clear();
    }
}
