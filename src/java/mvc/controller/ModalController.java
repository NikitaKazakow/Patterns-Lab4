package mvc.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import mvc.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ModalController implements Initializable {

    @Getter
    @Setter
    private Model model;

    @FXML
    private TextField addTextField;

    @FXML
    private Button addButton;

    @FXML
    private void addButtonClick() {
        double x = Double.parseDouble(addTextField.getText());
        model.addPoint(x);
        addButton.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTextField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.equals("")) {
                try {
                    Double.parseDouble(t1);
                    addTextField.setText(t1);
                    addButton.setDisable(false);
                }
                catch (NumberFormatException ex) {
                    addButton.setDisable(true);
                }
            }
            else {
                addButton.setDisable(true);
            }
        });
    }
}
