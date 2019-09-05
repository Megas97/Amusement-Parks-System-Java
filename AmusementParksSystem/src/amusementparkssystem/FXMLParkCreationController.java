package amusementparkssystem;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXMLParkCreationController implements Initializable {
    
    @FXML
    private ListView parksList;
    @FXML
    private TextField parkNameInput;
    @FXML
    private TextField parkTicketPriceInput;
    @FXML
    private Button parkCreateButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Button closeButton;
    @FXML
    private Button nextButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Amusementpark> parks = DBAccess.getAllParks();
        for (Amusementpark park : parks){
            parksList.getItems().add(park.getParkName());
        }
        parkNameInput.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                statusLabel.setText("");
            }
        });
        parkTicketPriceInput.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                statusLabel.setText("");
            }
        });
    }
    
    @FXML
    public void createPark(ActionEvent event) throws IOException {
        String name = parkNameInput.getText();
        if ((name.isEmpty()) || (parkTicketPriceInput.getText().isEmpty())){
            statusLabel.setTextFill(Color.web("#FF0000"));
            statusLabel.setText("Failure");
        }else{
            try{
                double price = Double.parseDouble(parkTicketPriceInput.getText());
                if (DBAccess.addPark(name, price)){
                    parksList.getItems().add(name);
                    statusLabel.setTextFill(Color.web("#00FF00"));
                    statusLabel.setText("Success");
                }else{
                    statusLabel.setTextFill(Color.web("#FF0000"));
                    statusLabel.setText("Failure");
                }
            }catch(Exception ex){
                statusLabel.setTextFill(Color.web("#FF0000"));
                statusLabel.setText("Failure");
            }
        }
    }
    
    @FXML
    public void handleParksListStatusLabel(MouseEvent event) throws IOException {
        int selectedIndex = parksList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            statusLabel.setText("");
        }
    }
    
    @FXML
    public void closeForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void goToNextPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLManagerPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Hire Manager");
        app_stage.show();
    }
}