package com.lattisi.peg.app;

import com.lattisi.peg.dsl.Shell;
import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.entities.IContainer;
import com.lattisi.peg.engine.entities.IItem;
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

        Map<String, IItem> map = problem.getItems();

        // TableView
        ObservableList<IItem> observableList = FXCollections.observableArrayList(map.values());
        itemsView.setItems(observableList);

        // TreeView
        TreeItem<String> root = new TreeItem<String>("Problem");
        root.setExpanded(true);
        Collection<IItem> values = map.values();

        addChidrenToNode(root, values);

        treeView.setRoot(root);
    }

    private void addChidrenToNode(TreeItem<String> node, Collection<IItem> items) {
        ObservableList<TreeItem<String>> children = node.getChildren();
        for( IItem item: items){
            String label = item.getType().toString().concat(" ").concat(item.getName());
            if( item.getMeasure() != null ){
                label = label.concat(" (").concat(item.getMeasure()).concat(")");
            }
            TreeItem<String> childNode = new TreeItem<String>(label);
            children.add(childNode);
            if( item instanceof IContainer ){
                Collection<IItem> childItems = ((IContainer) item).getChildren();
                addChidrenToNode(childNode, childItems);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnName.setCellValueFactory(new PropertyValueFactory<IItem, String>("name"));
        columnTypeName.setCellValueFactory(new PropertyValueFactory<IItem, String>("typeName"));
        columnMeasure.setCellValueFactory(new PropertyValueFactory<IItem, String>("measure"));
    }
}
