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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLShowParkFacilityVisitorsController implements Initializable {
    
    @FXML
    private ListView parksList;
    
    @FXML
    private ListView visitorsList;
    
    @FXML
    private ListView facilitiesList;
    
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
    }
    
    @FXML
    public void showParkFacilities(MouseEvent event) throws IOException {
        int selectedIndex = parksList.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1){
            int parkId = selectedIndex + 1;
            List<Facility> facilities = DBAccess.getParkFacilities(parkId);
            facilitiesList.getItems().clear();
            for (Facility facility : facilities){
                facilitiesList.getItems().add(facility.getFacilityName());
            }
        }
    }
    
    @FXML
    public void showFacilityVisitors(MouseEvent event) throws IOException {
        int selectedIndexParks = parksList.getSelectionModel().getSelectedIndex();
        int selectedIndexFacilities = facilitiesList.getSelectionModel().getSelectedIndex();
        if ((selectedIndexParks != -1) || (selectedIndexFacilities != -1)){
            int parkId = selectedIndexParks + 1;
            int facilityId = selectedIndexFacilities + 1;
            List<Child> visitors = DBAccess.getFacilityVisitors(facilityId, parkId);
            visitorsList.getItems().clear();
            for (Child visitor : visitors){
                visitorsList.getItems().add(visitor.getChildName());
            }
        }
    }
    
    @FXML
    public void goToPreviousPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLFacilityVisitorsPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Enter Facility");
        app_stage.show();
    }
    
    @FXML
    public void goToNextPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLActionsPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Information");
        app_stage.show();
    }
}