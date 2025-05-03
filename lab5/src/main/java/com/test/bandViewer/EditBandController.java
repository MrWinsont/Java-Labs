package com.test.bandViewer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditBandController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField infoField;

    private Band band;
    private Stage editStage;
    private boolean saved = false;
    private String oldName;

    public void SetEditStage(Stage stage) {
        this.editStage = stage;
    }

    public void setBand(Band band) {
        this.oldName = band.getName();
        this.band = band;
        nameField.setText(band.getName());
        countryField.setText(band.getCountry());
        yearField.setText(String.valueOf(band.getYear()));
        infoField.setText(band.getInfo());
    }

    public boolean isSaved() {
        return saved;
    }

    public Band GetBand(){
        return band;
    }

    public String GetOldName(){
        return oldName;
    }

    @FXML
    private void onSave() {
        band.SetName(nameField.getText());
        band.SetCountry(countryField.getText());
        band.SetYear(Integer.parseInt(yearField.getText()));
        band.SetInfo(infoField.getText());
        saved = true;
        editStage.close();
    }

    @FXML
    private void onCancel() {
        editStage.close();
    }
}
