package com.test.bandViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BandMembersViewController extends BandViewController{   
    @FXML
    private TableView<Musician> tableMember;
    @FXML
    private TableColumn<Musician, String> MusCol;
    @FXML
    private TableColumn<Musician, String> instrumentCol;
    @FXML
    private TextField txt_name_mus;
    @FXML
    private TextField txt_inst;

    @FXML
    public void initialize() {
        MusCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        instrumentCol.setCellValueFactory(new PropertyValueFactory<>("instrument"));
        try {
            UpdateTable();
            System.out.println("successfully updated");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void UpdateTable() throws IOException, SQLException {
        ArrayList<Musician> data = adapter.SelectMusicianData(BandViewController.currentBand.getName());
        ObservableList<Musician> data_new = FXCollections.observableArrayList(data);
        tableMember.setItems(data_new);
    }

    @FXML
    public void onAddMusButtonClick(){
        String name = txt_name_mus.getText();
        String instrument = txt_inst.getText();
        try {
            adapter.InsertMusician(name, instrument, BandViewController.currentBand);
            UpdateTable();
            System.out.println("successfully updated");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onDeleteMusButtonClick() throws IOException, SQLException {
        Musician selectedMusician = tableMember.getSelectionModel().getSelectedItem();
        if(selectedMusician != null){
            try {
                adapter.DeleteMusicianData(selectedMusician.getName());
                UpdateTable();
                System.out.println("successfully updated");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
