package com.lattisi.peg.app;

import com.lattisi.peg.dsl.Shell;
import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.Theorems;
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
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private TextArea dsl;

    @FXML
    private TreeView treeView;

    @FXML
    private TableView itemsView;

    @FXML
    private TableColumn columnName;

    @FXML
    private TableColumn columnTypeName;

    @FXML
    private TableColumn columnMeasure;

    @FXML
    private TableColumn columnTheoremId;

    @FXML
    private TableColumn columnTheoremDescription;

    @FXML
    private TableView theoremsView;

    @FXML
    private WebView help;


    @FXML
    private Button test;

    @FXML
    private Button enter;

    @FXML
    private TextField newLine;

    private Shell shell = Shell.build();

    @FXML
    protected void newLineAction(ActionEvent event) {
        String line = "\n" + newLine.textProperty().get();
        dsl.appendText(line);
        shell.evaluate(line);
        newLine.textProperty().set("");
        Problem problem = shell.getLanguage().getProblem();
        problem.refresh();
        refresh(problem);
    }

    @FXML
    protected void applyTheorem(MouseEvent event) {
        Map<String, String> map = (Map<String, String>) theoremsView.getSelectionModel().getSelectedItem();
        String out = "apply \"" + map.get("id") + "\" on ";
        newLine.clear();
        newLine.appendText(out);
    }

    @FXML
    protected void applyOn(MouseEvent event) {
        Item item = (Item) itemsView.getSelectionModel().getSelectedItem();
        String out = "\"" + item.getName() + "\", ";
        newLine.appendText(out);
    }

    @FXML
    protected void testDslAction(ActionEvent event) {
        String text = dsl.getText();
        Problem problem = shell.getLanguage().getProblem();
        for( String line: text.split("\\n") ){
            shell.evaluate(line);
            problem.refresh();
        }

        refresh(problem);
    }

    private void refresh(Problem problem) {
        Map<String, Item> map = problem.getItemsMap();

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

    private void addChidrenToNode(TreeItem<String> node, Collection<? extends Item> items) {
        Problem problem = shell.getLanguage().getProblem();
        ObservableList<TreeItem<String>> children = node.getChildren();
        for( Item item: items){
            String label = item.getType().toString().concat(" ").concat(item.getName());
            if( item instanceof Measurable && ((Measurable) item).getMeasure() != null ){
                label = label.concat(" (").concat(((Measurable) item).getMeasure()).concat(")");
            }
            TreeItem<String> childNode = new TreeItem<String>(label);
            if( node.getValue().equals("Problem") && problem.getParents(item).size()>0 ){
                // skip
            } else {
                children.add(childNode);
            }
            if( item instanceof Container){
                Collection<? extends Item> childItems = ((Container) item).getChildren();
                addChidrenToNode(childNode, childItems);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Items
        columnName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        columnTypeName.setCellValueFactory(new PropertyValueFactory<Item, String>("typeName"));
        columnMeasure.setCellValueFactory(new PropertyValueFactory<Item, String>("measure"));

        // Theorems
        List<Map> theorems = new ArrayList<Map>();
        for( String key: Theorems.THEOREMS_MAP.keySet() ){
            Map theorem = new HashMap<String, String>();
            theorem.put("id", key);
            theorem.put("description", Theorems.THEOREMS_MAP.get(key));
            theorems.add(theorem);
        }
        columnTheoremId.setCellValueFactory(new MapValueFactory<String>("id"));
        columnTheoremDescription.setCellValueFactory(new MapValueFactory<String>("description"));
        ObservableList<Map> observableList = FXCollections.observableArrayList(theorems);
        theoremsView.setItems(observableList);

        WebEngine engine = help.getEngine();
        URL indexUrl = getClass().getResource("../help/index.html");
        engine.load(indexUrl.toExternalForm());

        // Demo
        String problemCode = "create the triangle \"ABC\"    // or: create \"ABC\"\n" +
                "extend the segment \"AC\" to \"D\" with measure of \"BC\"\n" +
                "extend \"BC\" to \"E\" with measure of \"AC\"\n" +
                "create the segment \"ED\"\n" +
                "extend the segment \"ED\" to \"H\"\n" +
                "extend the segment \"AB\" to \"H\"\n" +
                "\n" +
                "declare \"dce\" equals \"bca\" due \"NAA\" // not adjacent angles\n" +
                "declare \"CED\" equals \"ABC\" due \"SAS\" // side angle side\n" +
                "declare \"cba\" equals \"edc\" due \"ETOA\" // equals triangle opposite angles\n" +
                "create the segment \"BD\"\n" +
                "declare \"cdb\" equals \"dbc\" due \"TICA\" // triangle isosceles corresponding angles\n" +
                "declare \"edb\" equals \"abd\" due \"SEA\" // sum of equals angles\n" +
                "declare \"hdb\" equals \"hbd\" due \"DEA\" // difference of equals angles\n";

        //String newLineCode = "apply \"10.8\" on \"ad\", \"bc\"";
        dsl.textProperty().set(problemCode);
        //newLine.textProperty().set(newLineCode);

    }
}
