package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.dao.ConcreteDAO;
import sample.dao.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private TreeView treeView;
    @FXML
    private Button addLine;
    @FXML
    private Button addStation;
    @FXML
    private Button updateLine;
    @FXML
    private Button updateStation;
    @FXML
    private Button deleteLine;
    @FXML
    private Button deleteStation;
    @FXML
    private Button save;
    @FXML
    private TextField idLine;
    @FXML
    private TextField colorLine;
    @FXML
    private TextField idStation;
    @FXML
    private TextField name;
    @FXML
    private Label information;

    DAO dao;
    @FXML
    public void initialize() {
        try {
            dao = new ConcreteDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printToTreeView();
    }
    public void printToTreeView(){
        treeView.setRoot(null);
        ArrayList<Line> lines = dao.getAll().getLines();

        TreeItem<String> root = new TreeItem<>("Metropolitan");

        for(int i = 0; i < lines.size(); i++){
            String info;
            info = "id: " + lines.get(i).line_id + " Color: " + lines.get(i).color;
            ArrayList<Station> stations = lines.get(i).getStations();
            TreeItem<String> line = new TreeItem<>(info);
            for(int j = 0; j < stations.size(); j++){
                String stationInfo = stations.get(j).station_id + " " + stations.get(j).name;
                TreeItem<String> station = new TreeItem<>(stationInfo);
                line.getChildren().add(station);
            }

            root.getChildren().add(line);
        }
        treeView.setRoot(root);
    }
    @FXML
    private void addLine(ActionEvent event) {
        String code = idLine.getText();
        String color = colorLine.getText();
        dao.addLine(Integer.valueOf(code), color);
        printToTreeView();
    }
    @FXML
    private void addStation(ActionEvent event) {
        String id = this.idStation.getText();
        String name = this.name.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected line");
            return;
        }
        String idLine = selectedItem.getValue().split(" ")[1];

        dao.addStation( Integer.valueOf(idLine), Integer.valueOf(id), name);
        printToTreeView();

    }
    @FXML
    private void updateLine(ActionEvent event) {
        String newCode = idLine.getText();
        String newColor = colorLine.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected line");
            return;
        }

        String[] s = selectedItem.getValue().split(" ");
        String lineId = selectedItem.getValue().split(" ")[1];
        String lineColor = selectedItem.getValue().split(" ")[3];

        if(newCode.equals("")){
            newCode = lineId;
        }
        if(newColor.equals("")){
            newColor = lineColor;
        }
        dao.updateLine(Integer.parseInt(lineId), Integer.parseInt(newCode), newColor);
        printToTreeView();
    }


    @FXML
    public void deleteLine(ActionEvent event){
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected line");
            return;
        }
        String lineId = selectedItem.getValue().split(" ")[1];
        dao.deleteLine(Integer.valueOf(lineId));
        printToTreeView();
    }
    @FXML
    public void deleteStation(ActionEvent event){
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected line");
            return;
        }
        String lineId = selectedItem.getParent().getValue().split(" ")[1];
        String stationId = selectedItem.getValue().split(" ")[0];
        dao.deleteStation(Integer.parseInt(lineId), Integer.parseInt(stationId));
        printToTreeView();
    }

}

