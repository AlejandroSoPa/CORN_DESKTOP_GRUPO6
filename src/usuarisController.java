import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Label info;

    @FXML
    private Label wallet;

    @FXML
    private Spinner max1;

    @FXML
    private Spinner min1;

    @FXML
    private Spinner max2;

    @FXML
    private Spinner min2;

    @FXML
    public ImageView img1;

    @FXML
    public ImageView img2;

    @FXML
    private ComboBox drop;

    @FXML
    public Button very;

    @FXML
    public Button refus=new Button();

    public static Integer id;

    private Integer status;

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
                    itemController.setId(console.getInt("id"));
                    
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
        refus.setVisible(false);
        very.setVisible(false);
        very.setDisable(true);
        refus.setDisable(true);
        
        JSONObject obj = new JSONObject("{}");
        loading.setVisible(true);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/API/get_profiles",obj.toString(), (response) -> {
            System.out.println(response);
            loadUsersCallback(response);
            loading.setVisible(false);
            initSpinner();
            if(this.drop.getItems().size()==0){
                this.drop.getItems().addAll("ACCEPTAT","A VERIFICAR","REFUSAT","NO VERFICAT");

            }
        });
    }

    public void loadFilteredUsers(){
        JSONObject obj = new JSONObject("{}");
        loading.setVisible(true);
        if((Integer)max1.getValue()>0 && (Integer)max1.getValue()>(Integer)min1.getValue()){obj.put("max1", max1.getValue());}
        if((Integer)max2.getValue()>0 && (Integer)max2.getValue()>(Integer)min2.getValue()){obj.put("max2", max2.getValue());}
        obj.put("min1", min1.getValue());
        obj.put("min2", min2.getValue());
        if(drop.getValue()!=null){

            switch ((String)drop.getValue()) {
                case "REFUSAT":
                obj.put("status", 4);
                    break;
            
                case "ACCEPTAT":
                obj.put("status", 3);
                break;
                case "A VERIFICAR":
                obj.put("status", 2);
                break;
                case "NO VERFICAT":
                obj.put("status", 1);
                break;
                default:
                    break;
            }
        }

        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/API/get_filtered_profiles",obj.toString(), (response) -> {
            System.out.println(response);
            loadUsersCallback(response);
            loading.setVisible(false);
            // initSpinner();
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
        this.img1.setImage(new Image("./assets/No_Image_Available.jpg"));
        this.img2.setImage(new Image("./assets/No_Image_Available.jpg"));
        this.very.setVisible(false);
        this.refus.setVisible(false);
        this.info.setVisible(false);
        this.yPane2.getChildren().clear();
    }

    public void initSpinner() {
        max1.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99)
        );
        max2.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99)
        );
        min1.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99)
        );
        min2.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99)
        );
    }

    @FXML
    private void filter(){
        this.clear();
        this.yPane.getChildren().clear();
        this.loadFilteredUsers();

    }

    @FXML
    private void filterClean(){
        this.clear();
        this.yPane.getChildren().clear();
        this.initSpinner();
        this.loadUsers();
    }

    @FXML
    private void verify(){
        this.changeStatus(3);
    }

    @FXML
    private void refuse(){
        this.changeStatus(4);
    }

    private void changeStatus(Integer type){
        very.setDisable(true);
        refus.setDisable(true);
        JSONObject obj = new JSONObject("{}");
        loading.setVisible(true);
        obj.put("id",this.id );
        System.out.println(this.id);
        obj.put("status", type);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/API/validate",obj.toString(), (response) -> {
            
            loading.setVisible(false);
            JSONObject objResponse = new JSONObject(response);

            if (objResponse.getString("status").equals("OK")) {
                info.setVisible(true);
            }else{
                info.setText("Error al actualitzar");
                info.setVisible(true);
                very.setDisable(false);
                refus.setDisable(false);
            }
            
        });
    }
    public void setImg1(Image img1) {
        this.img1.setImage(img1);
    }
    public void setImg2(Image img2) {
        this.img2.setImage(img2);
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    
}
