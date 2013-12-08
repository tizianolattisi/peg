package com.lattisi.peg.app;

import com.lattisi.peg.dsl.Shell;
import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.entities.IItem;
import com.lattisi.peg.engine.entities.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea dsl;

    @FXML
    private TableView itemsView;

    @FXML
    private TableColumn columnName;

    @FXML
    private TableColumn columnType;

    @FXML
    private TableColumn columnMetric;


    @FXML
    private Button test;

    @FXML
    protected void testDslAction(ActionEvent event) {
        String text = dsl.getText();
        Shell shell = Shell.build();
        shell.evaluate(text);
        Problem problem = shell.getLanguage().getProblem();
        Map<String, IItem> map = problem.getItems();

        ObservableList<IItem> items = FXCollections.observableArrayList(map.values());
        itemsView.setItems(items);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnName.setCellValueFactory(new PropertyValueFactory<IItem, String>("name"));
        columnType.setCellValueFactory(new PropertyValueFactory<IItem, String>("type"));
    }
}
