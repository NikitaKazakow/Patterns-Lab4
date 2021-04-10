package mvc.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mvc.Model;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Model model;

    @FXML
    private TableView<Model.Point> table;

    @FXML
    private LineChart<Double, Double> chart;

    @FXML
    private Button addModalButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField editTextField;

    @FXML
    private void addPoint() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mvc/view/Modal.fxml"));

            Parent modalLayout = loader.load();
            ((ModalController)loader.getController()).setModel(model);

            modalLayout.getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/mvc/css/style.css")).toExternalForm());

            Scene scene = new Scene(modalLayout);
            Stage modalDialog = new Stage();
            modalDialog.setTitle("Добавление точки");
            modalDialog.setScene(scene);
            modalDialog.initOwner(table.getScene().getWindow());
            modalDialog.initModality(Modality.APPLICATION_MODAL);
            modalDialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removePoint() {
        if (table.getItems().size() > 0) {
            int index = model.removePoint(table.getSelectionModel().getSelectedIndex());
            if (table.getItems().size() == 0) {
                editTextField.setText("");
            }
            else {
                table.getSelectionModel().select(index);
            }
        }
    }

    @FXML
    private void editPoint() {
        double x = Double.parseDouble(editTextField.getText());
        table.getSelectionModel().select(
                model.editPoint(table.getSelectionModel().getSelectedIndex(), x)
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        model = new Model();

        chart.setData(model.getChartSeries());

        table.setItems(model.getTablePoints());

        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("x"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("y"));

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, point, t1) -> {
            if (t1 != null) {
                editTextField.setText(t1.getX().toString());
                editButton.setDisable(false);
                removeButton.setDisable(false);
            }
        });

        editTextField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.equals("")) {
                try {
                    Double.parseDouble(t1);
                    editTextField.setText(t1);
                    removeButton.setDisable(false);
                    editButton.setDisable(false);
                }
                catch (NumberFormatException ex) {
                    addModalButton.setDisable(true);
                }
            }
            else {
                editButton.setDisable(true);
            }
        });
    }
}
