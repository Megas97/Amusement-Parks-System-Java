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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXMLFacilityVisitorsController implements Initializable {
    
    @FXML
    private ListView parksList;
    
    @FXML
    private ListView visitorsList;
    
    @FXML
    private ListView facilitiesList;
    
    @FXML
    private Button previousButton;
    
    @FXML
    private Button addVisitorToFacilityButton;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Button nextButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Amusementpark> parks = DBAccess.getAllParks();
        for (Amusementpark park : parks){
            parksList.getItems().add(park.getParkName());
        }
    }
    
    @FXML
    public void showParkVisitorsAndFacilities(MouseEvent event) throws IOException {
        int selectedIndex = parksList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            statusLabel.setText("");
        }else{
            int parkId = selectedIndex + 1;
            List<Child> visitors = DBAccess.getParkVisitors(parkId);
            visitorsList.getItems().clear();
            for (Child visitor : visitors){
                visitorsList.getItems().add(visitor.getChildName());
            }
            List<Facility> facilities = DBAccess.getParkFacilities(parkId);
            facilitiesList.getItems().clear();
            for (Facility facility : facilities){
                facilitiesList.getItems().add(facility.getFacilityName());
            }
        }
    }
    
    @FXML
    public void handleVisitorsListStatusLabel(MouseEvent event) throws IOException {
        int selectedIndex = visitorsList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            statusLabel.setText("");
        }
    }
    
    @FXML
    public void handleFacilitiesListStatusLabel(MouseEvent event) throws IOException {
        int selectedIndex = facilitiesList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            statusLabel.setText("");
        }
    }
    
    @FXML
    public void addVisitorToFacility(ActionEvent event) throws IOException {
        int selectedIndexParks = parksList.getSelectionModel().getSelectedIndex();
        int selectedIndexVisitors = visitorsList.getSelectionModel().getSelectedIndex();
        int selectedIndexFacilities = facilitiesList.getSelectionModel().getSelectedIndex();
        if ((selectedIndexParks == -1) || (selectedIndexVisitors == -1) || (selectedIndexFacilities == -1)){
            statusLabel.setTextFill(Color.web("#FF0000"));
            statusLabel.setText("Failure");
        }else{
            int parkId = selectedIndexParks + 1;
            int visitorId = selectedIndexVisitors + 1;
            int facilityId = selectedIndexFacilities + 1;
            int minAllowedAge = DBAccess.getFacilityMinAllowedAge(facilityId, parkId);
            int visitorAge = DBAccess.getVisitorAge(visitorId, parkId);
            if (visitorAge < minAllowedAge){
                statusLabel.setTextFill(Color.web("#FF0000"));
                statusLabel.setText("Failure");
            }else{
                if (DBAccess.addVisitorToFacility(parkId, visitorId, facilityId)){
                    statusLabel.setTextFill(Color.web("#00FF00"));
                    statusLabel.setText("Success");
                }else{
                    statusLabel.setTextFill(Color.web("#FF0000"));
                    statusLabel.setText("Failure");
                }
            }
        }
    }
    
    @FXML
    public void goToPreviousPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLParkVisitorsPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Buy Tickets");
        app_stage.show();
    }
    
    @FXML
    public void goToNextPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLShowParkFacilityVisitorsPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Show Park Facilities");
        app_stage.show();
    }
}