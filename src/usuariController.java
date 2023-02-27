import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class usuariController {
    
    @FXML
    private Label title, subtitle,phone;

    @FXML
    private void handleMenuAction() {
        

        usuarisController c0=(usuarisController) UtilsViews.getController("usuaris");
        c0.clear();
        JSONObject obj = new JSONObject("{}");
        obj.put("phone",this.phone.getText());
        c0.loading.setVisible(true);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/API/get_profile",obj.toString(), (response) -> {
            JSONObject objResponse = new JSONObject(response);
            
            if (objResponse.getString("status").equals("OK")) {
                JSONArray JSONlist = objResponse.getJSONArray("result");
                for (int i = 0; i < JSONlist.length(); i++) {
                    System.out.println(response);
                    // Get console information
                    JSONObject console = JSONlist.getJSONObject(i);
    
                    c0.setName(console.getString("name"));
                    c0.setPhone(String.valueOf(console.getInt("phone")));
                    c0.setSurname(console.getString("surname"));
                    c0.setEmail(console.getString("email"));
    
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

}