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

public class FXMLFacilityCreationController implements Initializable {
    
    @FXML
    private ListView parksList;
    @FXML
    private TextField facilityNameInput;
    @FXML
    private TextField minimumAgeInput;
    @FXML
    private Button facilityCreateButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Amusementpark> parks = DBAccess.getAllParks();
        for (Amusementpark park : parks){
            parksList.getItems().add(park.getParkName());
        }
        facilityNameInput.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                statusLabel.setText("");
            }
        });
        minimumAgeInput.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                statusLabel.setText("");
            }
        });
    }
    
    @FXML
    public void createFacility(ActionEvent event) throws IOException {
        String name = facilityNameInput.getText();
        if ((name.isEmpty()) || (facilityNameInput.getText().isEmpty())){
            statusLabel.setTextFill(Color.web("#FF0000"));
            statusLabel.setText("Failure");
        }else{
            try{
                int minAge = Integer.parseInt(minimumAgeInput.getText());
                int selectedIndex = parksList.getSelectionModel().getSelectedIndex();
                if (selectedIndex == -1){
                    statusLabel.setTextFill(Color.web("#FF0000"));
                    statusLabel.setText("Failure");
                }else{
                    int parkId = selectedIndex + 1;
                    if (DBAccess.createAndSetFacility(name, minAge, parkId)){
                        statusLabel.setTextFill(Color.web("#00FF00"));
                        statusLabel.setText("Success");
                    }else{
                        statusLabel.setTextFill(Color.web("#FF0000"));
                        statusLabel.setText("Failure");
                    }
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
    public void goToPreviousPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLManagerPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Hire Manager");
        app_stage.show();
    }
    
    @FXML
    public void goToNextPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLParkVisitorsPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Buy Tickets");
        app_stage.show();
    }
}