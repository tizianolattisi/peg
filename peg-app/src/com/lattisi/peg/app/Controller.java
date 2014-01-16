package com.lattisi.peg.app;

import com.lattisi.peg.dsl.Shell;
import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.entities.Container;
import com.lattisi.peg.engine.entities.Item;
import com.lattisi.peg.engine.entities.ItemType;
import com.lattisi.peg.engine.entities.Measurable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea dsl;

    @FXML
    private TableView itemsView;

    @FXML
    private TreeView treeView;

    @FXML
    private TableColumn columnName;

    @FXML
    private TableColumn columnTypeName;

    @FXML
    private TableColumn columnMeasure;


    @FXML
    private Button test;

    @FXML
    protected void testDslAction(ActionEvent event) {
        String text = dsl.getText();
        Shell shell = Shell.build();
        shell.evaluate(text);
        Problem problem = shell.getLanguage().getProblem();
        problem.refresh();

        Map<String, Item> map = problem.getItems();

        // TableView
        ObservableList<Item> observableList = FXCollections.observableArrayList(map.values());
        itemsView.setItems(observableList);

        // TreeView
        TreeItem<String> root = new TreeItem<String>("Problem");
        root.setExpanded(true);
        Collection<Item> values = map.values();

        addChidrenToNode(root, values);

        treeView.setRoot(root);
    }

    private void addChidrenToNode(TreeItem<String> node, Collection<Item> items) {
        ObservableList<TreeItem<String>> children = node.getChildren();
        for( Item item: items){
            String label = item.getType().toString().concat(" ").concat(item.getName());
            if( item instanceof Measurable && ((Measurable) item).getMeasure() != null ){
                label = label.concat(" (").concat(((Measurable) item).getMeasure()).concat(")");
            }
            TreeItem<String> childNode = new TreeItem<String>(label);
            if( node.getValue().equals("Problem") &&
                    (item.getType().equals(ItemType.point) || item.getType().equals(ItemType.angle)) ) {
                // skip
            } else {
                children.add(childNode);
            }
            if( item instanceof Container){
                Collection<Item> childItems = ((Container) item).getChildren();
                addChidrenToNode(childNode, childItems);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        columnTypeName.setCellValueFactory(new PropertyValueFactory<Item, String>("typeName"));
        columnMeasure.setCellValueFactory(new PropertyValueFactory<Item, String>("measure"));
    }
}
