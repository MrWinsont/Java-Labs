package com.test.bandViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BandViewController {
    DBAdapter adapter = new DBAdapter();

    @FXML
    private TableView<Band> tableBand;
    @FXML
    private TableColumn<Band, String> nameCol;
    @FXML
    private TableColumn<Band, String> countryCol;
    @FXML
    private TableColumn<Band, Integer> yearCol;
    @FXML
    private TableColumn<Band, String> infoCol;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_country;
    @FXML
    private TextField txt_year;
    @FXML
    private TextField txt_info;

    static Band currentBand;

    private void UpdateTable() throws IOException, SQLException {
        ArrayList<Band> data = adapter.SelectData();
        ObservableList<Band> data_new = FXCollections.observableArrayList(data);
        tableBand.setItems(data_new);
    }

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        infoCol.setCellValueFactory(new PropertyValueFactory<>("info"));
        DBAdapter.CreateOrConnection();
        try {
            UpdateTable();
            System.out.println("successfully updated");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupContextMenu();
    }

    @FXML
    public void onAddButtonClick(){
        String name = txt_name.getText();
        String country = txt_country.getText();
        Integer year = Integer.parseInt(txt_year.getText());
        String info = txt_info.getText();
        try {
            adapter.InsertBandInfo(name, country, year, info);
            UpdateTable();
            System.out.println("successfully updated");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onDeleteButtonClick() throws IOException, SQLException {
        Band selectedBand = tableBand.getSelectionModel().getSelectedItem();
        if(selectedBand != null){
            try {
                adapter.DeleteBandData(selectedBand.getName());
                UpdateTable();
                System.out.println("successfully updated");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void onUpdateButtonClick() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBand.fxml"));
        Parent root = loader.load();
        Band selectedBand = tableBand.getSelectionModel().getSelectedItem();

        Stage dialog = new Stage();
        dialog.setTitle("Редактировать группу");
        dialog.setScene(new Scene(root, 1100, 300));

        EditBandController editController = loader.getController();
        editController.SetEditStage(dialog);
        editController.setBand(selectedBand);

        dialog.showAndWait();

        if (editController.isSaved()) {
            Band newBand = editController.GetBand();
            String oldName = editController.GetOldName();
            adapter.UpdateBandData(newBand,oldName);  
            UpdateTable();
        }
    }

    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem showMembersItem = new MenuItem("Показать состав");

        showMembersItem.setOnAction(e -> {
            Band selectedBand = tableBand.getSelectionModel().getSelectedItem();
            if (selectedBand != null) {
                currentBand = selectedBand;
                ShowBandMembers();
            }
        });

        contextMenu.getItems().add(showMembersItem);

        tableBand.setRowFactory(tv -> {
            TableRow<Band> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    tableBand.getSelectionModel().select(row.getItem());
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }
    private void ShowBandMembers(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bandMembersView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Состав группы: " + currentBand.getName());
            stage.setScene(new Scene(root));
            stage.show();
         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}