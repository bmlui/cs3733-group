package edu.wpi.teamc.controllers.pages;

import edu.wpi.teamc.dao.*;
import edu.wpi.teamc.dao.ImportCSV;
import edu.wpi.teamc.dao.displays.signage.SignEntry;
import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.login.Login;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportExportController {

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  private Desktop desktop = Desktop.getDesktop();
  private String filePath;

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  // Map variables
  private String[] selectedMapFilePaths = new String[4];
  final String nodeHeader = "nodeID,xCoord,yCoord,floor,building,status";
  final String edgeHeader = "startNode,endNode";
  final String moveHeader = "nodeID,longName,date";
  final String locationHeader = "longName,shortName,nodeType";

  // Service variables
  private String[] selectedServiceFilePaths = new String[6];
  final String conferenceRoomHeader =
      "requestid,requester,roomname,status,additionalnotes,starttime,endtime,assignedto";
  final String flowerHeader =
      "requestID,requester,roomname,status,additionalnotes,eta,flower,assignedto";
  final String furnitureHeader =
      "requestid,requester,roomname,status,additionalnotes,furnituretype,eta,assignedto";
  final String giftBasketHeader =
      "requestid,requester,status,additionalnotes,giftbasket,eta,roomname,assignedto";
  final String mealHeader =
      "requestID,requester,status,additionalnotes,meal,eta,roomname,assignedto";
  final String officeSupplyHeader =
      "requestID,requester,status,additionalnotes,officesupplytype,eta,roomname,assignedto";

  // User imports
  private String[] selectedUsersFilePaths = new String[3];
  final String employeeHeader = "id,username,name,department,position";
  final String loginHeader = "username,password,permissions,salt,otp";
  final String patientHeader = "id,name,checkin,checkout,phone,room,activetext";
  // Displays imports
  private String[] selectedDisplaysFilePaths = new String[2];
  final String alertsHeader = "id,title,description,type,startdate,enddate";
  final String signageHeader = "macadd,devicename,date,locationname,direction";

  @FXML
  void getImport(String Header) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String importedHeader = br.readLine();
        if (importedHeader.equals(Header)) {
          desktop.open(file);
          filePath = file.getAbsolutePath();
          switch (Header) {
            case nodeHeader:
              selectedMapFilePaths[0] = filePath;
              break;
            case edgeHeader:
              selectedMapFilePaths[1] = filePath;
              break;
            case moveHeader:
              selectedMapFilePaths[2] = filePath;
              break;
            case locationHeader:
              selectedMapFilePaths[3] = filePath;
              break;
            case conferenceRoomHeader:
              selectedServiceFilePaths[0] = filePath;
              break;
            case flowerHeader:
              selectedServiceFilePaths[1] = filePath;
              break;
            case furnitureHeader:
              selectedServiceFilePaths[2] = filePath;
              break;
            case giftBasketHeader:
              selectedServiceFilePaths[3] = filePath;
              break;
            case mealHeader:
              selectedServiceFilePaths[4] = filePath;
              break;
            case officeSupplyHeader:
              selectedServiceFilePaths[5] = filePath;
              break;
            case employeeHeader:
              selectedUsersFilePaths[0] = filePath;
              break;
            case loginHeader:
              selectedUsersFilePaths[1] = filePath;
              break;
            case patientHeader:
              selectedUsersFilePaths[2] = filePath;
              break;
            case alertsHeader:
              selectedDisplaysFilePaths[0] = filePath;
              break;
            case signageHeader:
              selectedDisplaysFilePaths[1] = filePath;
              break;
          }
        } else {
          // if it doesn't, display error message
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("File header does not match header format");
          alert.setContentText("Please select a valid file");
          alert.showAndWait();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  // Map imports
  @FXML
  void getImportNodes() {
    getImport(nodeHeader);
  }

  @FXML
  void getImportEdges() {
    getImport(edgeHeader);
  }

  @FXML
  void getImportMoves() {
    getImport(moveHeader);
  }

  @FXML
  void getImportLocations() {
    getImport(locationHeader);
  }

  @FXML
  void getImportMapSubmit(ActionEvent event) {
    String nodesFilePath = selectedMapFilePaths[0];
    String edgesFilePath = selectedMapFilePaths[1];
    String moveFilePath = selectedMapFilePaths[2];
    String locationNamesFilePath = selectedMapFilePaths[3];
    ImportCSV.importMapCSV(nodesFilePath, edgesFilePath, moveFilePath, locationNamesFilePath);
    selectedMapFilePaths[0] = null;
    selectedMapFilePaths[1] = null;
    selectedMapFilePaths[2] = null;
    selectedMapFilePaths[3] = null;
  }

  @FXML
  void getImportMapCancel(ActionEvent event) {
    selectedMapFilePaths[0] = null;
    selectedMapFilePaths[1] = null;
    selectedMapFilePaths[2] = null;
    selectedMapFilePaths[3] = null;
  }

  // Service imports
  @FXML
  void getImportConferenceRoom() {
    getImport(conferenceRoomHeader);
  }

  @FXML
  void getImportFlower() {
    getImport(flowerHeader);
  }

  @FXML
  void getImportFurniture() {
    getImport(furnitureHeader);
  }

  @FXML
  void getImportGiftBasket() {
    getImport(giftBasketHeader);
  }

  @FXML
  void getImportMeal() {
    getImport(mealHeader);
  }

  @FXML
  void getImportOfficeSupply() {
    getImport(officeSupplyHeader);
  }

  @FXML
  void getImportServiceSubmit(ActionEvent event) throws SQLException, FileNotFoundException {
    String conferenceRoomFilePath = selectedServiceFilePaths[0];
    String flowerFilePath = selectedServiceFilePaths[1];
    String furnitureFilePath = selectedServiceFilePaths[2];
    String giftBasketFilePath = selectedServiceFilePaths[3];
    String mealFilePath = selectedServiceFilePaths[4];
    String officeSupplyFilePath = selectedServiceFilePaths[5];
    ImportCSV.importConferenceRequestCSV(conferenceRoomFilePath);
    ImportCSV.importFlowerRequestCSV(flowerFilePath);
    ImportCSV.importFurnitureRequestCSV(furnitureFilePath);
    ImportCSV.importGiftBasketRequestCSV(giftBasketFilePath);
    ImportCSV.importMealRequestCSV(mealFilePath);
    ImportCSV.importOfficeSupplyRequestCSV(officeSupplyFilePath);
    selectedServiceFilePaths[0] = null;
    selectedServiceFilePaths[1] = null;
    selectedServiceFilePaths[2] = null;
    selectedServiceFilePaths[3] = null;
    selectedServiceFilePaths[4] = null;
    selectedServiceFilePaths[5] = null;
  }

  @FXML
  void getImportServiceCancel(ActionEvent event) {
    selectedServiceFilePaths[0] = null;
    selectedServiceFilePaths[1] = null;
    selectedServiceFilePaths[2] = null;
    selectedServiceFilePaths[3] = null;
    selectedServiceFilePaths[4] = null;
    selectedServiceFilePaths[5] = null;
  }
  // User imports
  @FXML
  void getImportEmployee() {
    getImport(employeeHeader);
  }

  @FXML
  void getImportLogin() {
    getImport(loginHeader);
  }

  @FXML
  void getImportPatient() {
    getImport(patientHeader);
  }

  @FXML
  void getImportUserSubmit(ActionEvent event) throws SQLException, FileNotFoundException {
    String employeeFilePath = selectedUsersFilePaths[0];
    String loginFilePath = selectedUsersFilePaths[1];
    String patientFilePath = selectedUsersFilePaths[2];
    ImportCSV.importEmployeeCSV(employeeFilePath, loginFilePath);
    ImportCSV.importPatientCSV(patientFilePath);
    selectedUsersFilePaths[0] = null;
    selectedUsersFilePaths[1] = null;
    selectedUsersFilePaths[2] = null;
  }

  @FXML
  void getImportUserCancel(ActionEvent event) {
    selectedUsersFilePaths[0] = null;
    selectedUsersFilePaths[1] = null;
    selectedUsersFilePaths[2] = null;
  }

  // Display imports
  @FXML
  void getImportAlerts() {
    getImport(alertsHeader);
  }

  @FXML
  void getImportSignage() {
    getImport(signageHeader);
  }

  @FXML
  void getImportDisplaySubmit(ActionEvent event) throws SQLException, FileNotFoundException {
    String alertsFilePath = selectedDisplaysFilePaths[0];
    String signageFilePath = selectedDisplaysFilePaths[1];
    ImportCSV.importAlertCSV(alertsFilePath);
    ImportCSV.importSignageCSV(signageFilePath);
    selectedDisplaysFilePaths[0] = null;
    selectedDisplaysFilePaths[1] = null;
  }

  @FXML
  void getImportDisplayCancel(ActionEvent event) {
    selectedDisplaysFilePaths[0] = null;
    selectedDisplaysFilePaths[1] = null;
  }

  // Export

  NodeDao nodeDao = new NodeDao();
  HospitalSystem hospitalSystem = new HospitalSystem();

  void getExport(String header) throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
      String filePath = file.getAbsolutePath();
      if (!filePath.endsWith(".csv")) { // check if file path doesn't already end with ".csv"
        filePath += ".csv"; // append ".csv" to the file path
      }
      switch (header) {
        case nodeHeader:
          hospitalSystem.exportCSV(filePath, new Node());
          break;
        case edgeHeader:
          hospitalSystem.exportCSV(filePath, new Edge());
          break;
        case moveHeader:
          hospitalSystem.exportCSV(filePath, new Move());
          break;
        case locationHeader:
          hospitalSystem.exportCSV(filePath, new LocationName());
          break;
        case conferenceRoomHeader:
          hospitalSystem.exportCSV(filePath, new ConferenceRoomRequest());
          break;
        case flowerHeader:
          hospitalSystem.exportCSV(filePath, new FlowerDeliveryRequest());
          break;
        case furnitureHeader:
          hospitalSystem.exportCSV(filePath, new FurnitureDeliveryRequest());
          break;
        case giftBasketHeader:
          hospitalSystem.exportCSV(filePath, new GiftBasketRequest());
          break;
        case mealHeader:
          hospitalSystem.exportCSV(filePath, new MealRequest());
          break;
        case officeSupplyHeader:
          hospitalSystem.exportCSV(filePath, new OfficeSuppliesRequest());
          break;
        case employeeHeader:
          hospitalSystem.exportCSV(filePath, new EmployeeUser());
          break;
        case loginHeader:
          hospitalSystem.exportCSV(filePath, new Login());
          break;
          //                case patientHeader:
          //                    hospitalSystem.exportCSV(filePath, new PatientUser());
          //                    break;
        case alertsHeader:
          hospitalSystem.exportCSV(filePath, new edu.wpi.teamc.dao.displays.Alert());
          break;
        case signageHeader:
          hospitalSystem.exportCSV(filePath, new SignEntry());
          break;
      }
    }
  }

  @FXML
  public void getNodesExport() throws IOException {
    getExport(nodeHeader);
  }

  @FXML
  public void getEdgesExport() throws IOException {
    getExport(edgeHeader);
  }

  @FXML
  public void getMovesExport() throws IOException {
    getExport(moveHeader);
  }

  @FXML
  public void getLocationsExport() throws IOException {
    getExport(locationHeader);
  }

  @FXML
  public void getConferenceRoomExport() throws IOException {
    getExport(conferenceRoomHeader);
  }

  @FXML
  public void getFlowerExport() throws IOException {
    getExport(flowerHeader);
  }

  @FXML
  public void getFurnitureExport() throws IOException {
    getExport(furnitureHeader);
  }

  @FXML
  public void getGiftBasketExport() throws IOException {
    getExport(giftBasketHeader);
  }

  @FXML
  public void getMealExport() throws IOException {
    getExport(mealHeader);
  }

  @FXML
  public void getOfficeSupplyExport() throws IOException {
    getExport(officeSupplyHeader);
  }

  @FXML
  public void getEmployeeExport() throws IOException {
    getExport(employeeHeader);
  }

  @FXML
  public void getLoginExport() throws IOException {
    getExport(loginHeader);
  }

  @FXML
  public void getPatientExport() throws IOException {
    getExport(patientHeader);
  }

  @FXML
  public void getAlertsExport() throws IOException {
    getExport(alertsHeader);
  }

  @FXML
  public void getSignageExport() throws IOException {
    getExport(signageHeader);
  }
}
