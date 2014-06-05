package com.lattisi.peg.app;

import com.lattisi.peg.dsl.Shell;
import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.Theorems;
import com.lattisi.peg.engine.entities.Container;
import com.lattisi.peg.engine.entities.Item;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private BorderPane borderPane;

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
        String line = newLine.textProperty().get() + "\n";
        dsl.appendText(line);
        shell.evaluate(line);
        newLine.textProperty().set("");
        Problem problem = shell.getLanguage().getProblem();
        problem.refresh();
        refresh(problem);
    }

    @FXML
    protected void applyTheorem(MouseEvent event) {

        // Items
        ObservableList items = itemsView.getSelectionModel().getSelectedItems();
        if( items.size() != 2 ){
            dsl.appendText("//You need to select two items to apply a theorem.\n");
            return;
        }
        Item item1 = (Item) items.get(0);
        Item item2 = (Item) items.get(1);
        String out = "declare \"" + item1.getName() + "\" equals \"" + item2.getName() + "\" ";
        Map<String, String> map = (Map<String, String>) theoremsView.getSelectionModel().getSelectedItem();
        out += " due \"" + map.get("id") + "\"";
        if( !item1.getType().equals(item2.getType()) ){
            dsl.appendText("// " + out + "\n");
            dsl.appendText("// The selected items must have the same type.\n");
            return;
        }
        //newLine.clear();
        newLine.appendText(out);
    }

    @FXML
    private void loadProblem(ActionEvent event) {
        String code = loadCode();
        if( code != null ) {
            dsl.clear();
            dsl.appendText(code);
            execute(code);
        }
    }

    @FXML
    private void loadSolution(ActionEvent event) {
        String code = loadCode();
        if( code != null ) {
            dsl.appendText(code);
            execute(code);
        }
    }

    private String loadCode() {
        String code = null;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extension =new FileChooser.ExtensionFilter("Problem (*.txt)","*.txt");
        fileChooser.getExtensionFilters().add(extension);
        File file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            code = new String(bytes, Charset.defaultCharset());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    /*
    @FXML
    protected void applyOn(MouseEvent event) {
        Item item = (Item) itemsView.getSelectionModel().getSelectedItem();
        String out = "\"" + item.getName() + "\", ";
        newLine.appendText(out);
    }
    */

    @FXML
    protected void testDslAction(ActionEvent event) {
        executeProblem();
    }

    private void executeProblem() {
        String text = dsl.getText();
        execute(text);
    }

    private void execute(String text) {
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

        // Multiple selection model in tree and item view
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
                "extend the segment \"AC\" to \"D\" with length of \"BC\"\n" +
                "extend \"BC\" to \"E\" with length of \"AC\"\n" +
                "create the segment \"ED\"\n" +
                "extend the segment \"ED\" to \"H\"\n" +
                "extend the segment \"AB\" to \"H\"\n" +
                "\n";

        /*
        problemCode += "declare \"dce\" equals \"bca\" due \"NAA\" // not adjacent angles\n" +
                "declare \"CED\" equals \"ABC\" due \"SAS\" // side angle side\n" +
                "declare \"cba\" equals \"edc\" due \"ETOA\" // equals triangle opposite angles\n" +
                "create the segment \"BD\"\n" +
                "declare \"cdb\" equals \"dbc\" due \"TICA\" // triangle isosceles corresponding angles\n" +
                "declare \"edb\" equals \"abd\" due \"SEA\" // sum of equals angles\n" +
                "declare \"hdb\" equals \"hbd\" due \"DEA\" // difference of equals angles\n";
        */

        //String newLineCode = "apply \"10.8\" on \"ad\", \"bc\"";
        //dsl.textProperty().set(problemCode);
        //newLine.textProperty().set(newLineCode);

    }
}
