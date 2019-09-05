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

public class FXMLActionsController implements Initializable {
    
    @FXML
    private Label totalIncomeResultLabel;
    @FXML
    private Label averageAgeResultLabel;
    @FXML
    private ListView parksList;
    @FXML
    private ListView facilitiesList;
    @FXML
    private TextField percentInput;
    @FXML
    private TextField incomeGoalInput;
    @FXML
    private Button increaseManagerSalaryButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Button previousButton;
    @FXML
    private Button newParkButton;
    @FXML
    private Button closeButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Amusementpark> parks = DBAccess.getAllParks();
        double totalIncome = 0.0;
        double totalAge = 0.0;
        int numberOfChildren = 0;
        double averageAge = 0.0;
        for (Amusementpark park : parks){
            parksList.getItems().add(park.getParkName());
            totalIncome += park.getParkTotalIncome();
            List<Child> children = DBAccess.getParksVisitors();
            for (Child child : children){
                numberOfChildren++;
                totalAge += child.getChildAge();
            }
        }
        averageAge = totalAge / numberOfChildren;
        totalIncomeResultLabel.setText(String.valueOf(Math.round(totalIncome * 100.0) / 100.0));
        averageAgeResultLabel.setText(String.valueOf(Math.round(averageAge * 100.0) / 100.0));
        percentInput.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                statusLabel.setText("");
            }
        });
        incomeGoalInput.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                statusLabel.setText("");
            }
        });
    }
    
    @FXML
    public void showParkFacilities(MouseEvent event) throws IOException {
        int selectedIndexParks = parksList.getSelectionModel().getSelectedIndex();
        if (selectedIndexParks == -1){
            statusLabel.setText("");
        }else{
            int parkId = selectedIndexParks + 1;
            List<Facility> facilities = DBAccess.getParkFacilities(parkId);
            facilitiesList.getItems().clear();
            for (Facility facility : facilities){
                facilitiesList.getItems().add(facility.getFacilityName());
            }
        }
    }
    
    @FXML
    public void increaseManagerSalary(ActionEvent event) throws IOException {
        if ((percentInput.getText().isEmpty()) || (incomeGoalInput.getText().isEmpty())){
            statusLabel.setTextFill(Color.web("#FF0000"));
            statusLabel.setText("Failure");
        }else{
            try{
                double percent = Double.parseDouble(percentInput.getText());
                double goal = Double.parseDouble(incomeGoalInput.getText());
                int selectedIndex = parksList.getSelectionModel().getSelectedIndex();
                if (selectedIndex == -1){
                    statusLabel.setTextFill(Color.web("#FF0000"));
                    statusLabel.setText("Failure");
                }else{
                    int parkId = selectedIndex + 1;
                    if (DBAccess.increaseManagerSalaryIfParkGoalReached(percent, goal, parkId)){
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
        int selectedIndex = facilitiesList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            statusLabel.setText("");
        }
    }
    
    @FXML
    public void goToPreviousPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLShowParkFacilityVisitorsPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Show Park Facility Visitors");
        app_stage.show();
    }
    
    @FXML
    public void goToFirstPage(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLParkCreationPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.setTitle("Park Creation");
        app_stage.show();
    }
    
    @FXML
    public void closeForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}