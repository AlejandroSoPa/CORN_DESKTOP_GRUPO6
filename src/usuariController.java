import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class usuariController {
    
    @FXML
    private Label title, subtitle,phone;

    private Integer id;

    @FXML
    private void handleMenuAction() {
        

        usuarisController c0=(usuarisController) UtilsViews.getController("usuaris");
        c0.loading.setVisible(true);
        c0.clear();
        JSONObject obj = new JSONObject("{}");
        obj.put("id",this.id);
        c0.loading.setVisible(true);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host +"/API/get_profile",obj.toString(), (response) -> {
            JSONObject objResponse = new JSONObject(response);
            System.out.println("-------------------------------------------------");
            
            if (objResponse.getString("status").equals("OK")) {
                JSONArray JSONlist = objResponse.getJSONArray("result");
                for (int i = 0; i < JSONlist.length(); i++) {
                    
                    // Get console information
                    JSONObject console = JSONlist.getJSONObject(i);
    
                    c0.setName(console.getString("name"));
                    c0.setPhone(String.valueOf(console.getInt("phone")));
                    c0.setSurname(console.getString("surname"));
                    c0.setEmail(console.getString("email"));
                    c0.setWallet(String.valueOf(console.getInt("wallet")));

                    usuarisController.id=console.getInt("id");
                    try {
                        if(!console.get("front").equals(null) && !console.get("back").equals(null) && console.getInt("status")!=3 && console.getInt("status")!=1){
                            c0.very.setDisable(false);
                            c0.refus.setDisable(false);
                            c0.very.setVisible(true);
                            c0.refus.setVisible(true); 
                            // TODO PUT IMAGES HERE
                            String front = console.getString("front");
                            String back = console.getString("back");
                            
                            // Transformar la cadena de text amb dades binàries en un byte[]
                            byte[] decodedfront = Base64.getDecoder().decode(front);
                            byte[] decodedback = Base64.getDecoder().decode(back);
                            
                            // Crear un objecte ‘Image’ amb les dades rebudes i posar-les en una imatge
                            Image img = new Image(new java.io.ByteArrayInputStream(decodedfront));
                            c0.setImg1(img);
                            Image img22 = new Image(new java.io.ByteArrayInputStream(decodedback));
                            c0.setImg2(img22);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    
                }
            }

        });
        
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/API/transaccions",obj.toString(), (response) -> {
            JSONObject objResponse = new JSONObject(response);
            
            if (objResponse.getString("status").equals("OK")) {
                // System.out.println(objResponse);
                JSONArray JSONlist = objResponse.getJSONArray("result");
                URL resource = this.getClass().getResource("./assets/transacio.fxml");
                for (int i = 0; i < JSONlist.length(); i++) {
                    // Get console information
                    JSONObject console = JSONlist.getJSONObject(i);
                    
                    try {
                        // Load template and set controller
                        FXMLLoader loader = new FXMLLoader(resource);
                        Parent itemTemplate = loader.load();
                        transacioController itemController = loader.getController();
                    
                        // Fill template with console information
                        itemController.setTitle(console.getString("text"));
                        
                        // Add template to the list
                        c0.yPane2.getChildren().add(itemTemplate);
    
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    
                }
            }

            c0.loading.setVisible(false);
        });
        
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

    public void setId(Integer id) {
        this.id = id;
    }

    
}
