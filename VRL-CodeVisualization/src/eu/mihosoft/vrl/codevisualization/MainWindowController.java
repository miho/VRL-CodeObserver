/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.codevisualization;

import eu.mihosoft.vrl.instrumentation.Invocation;
import eu.mihosoft.vrl.instrumentation.Scope;
import eu.mihosoft.vrl.instrumentation.UIBinding;
import eu.mihosoft.vrl.workflow.FlowFactory;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXSkinFactory;
import eu.mihosoft.vrl.workflow.fx.ScalableContentPane;
import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class MainWindowController implements Initializable {

    File currentDocument;
    @FXML
    TextArea editor;
    @FXML
    Pane view;
    private Pane rootPane;
    private VFlow flow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("Init");

        ScalableContentPane canvas = new ScalableContentPane();
        canvas.setStyle("-fx-background-color: rgb(0,0, 0)");
        view.getChildren().add(canvas);

        Pane root = new Pane();
        canvas.setContentPane(root);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(10,32,60), rgb(42,52,120));");

        rootPane = root;

        flow = FlowFactory.newFlow();
    }

    @FXML
    public void onKeyTyped(KeyEvent evt) {
//        String output = editor.getText();
//
//        output = MultiMarkdown.convert(output);
//
//        System.out.println(output);
//
//        
//
//        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
//
//            @Override
//            public URLStreamHandler createURLStreamHandler(String protocol) {
//                
//            }
//        });
//        
//        
//        outputView.getEngine().s
    }

    @FXML
    public void onLoadAction(ActionEvent e) {
        loadTextFile(null);
    }

    @FXML
    public void onSaveAction(ActionEvent e) {
        saveDocument(false);
    }

    private void saveDocument(boolean askForLocationIfAlreadyOpened) {

        if (askForLocationIfAlreadyOpened || currentDocument == null) {
            FileChooser.ExtensionFilter mdFilter =
                    new FileChooser.ExtensionFilter("Text Files (*.groovy, *.txt)", "*.groovy", "*.txt");

            FileChooser.ExtensionFilter allFilesfilter =
                    new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");

            currentDocument =
                    FileChooserBuilder.create().title("Save Groovy File").
                    extensionFilters(mdFilter, allFilesfilter).build().
                    showSaveDialog(null).getAbsoluteFile();
        }

        try (FileWriter fileWriter = new FileWriter(currentDocument)) {
            fileWriter.write(editor.getText());
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

    private void insertStringAtCurrentPosition(String s) {
        editor.insertText(editor.getCaretPosition(), s);
    }

    @FXML
    public void onSaveAsAction(ActionEvent e) {
        saveDocument(true);
        updateView();
    }

    @FXML
    public void onCloseAction(ActionEvent e) {
    }

    void loadTextFile(File f) {


        try {
            if (f == null) {
                FileChooser.ExtensionFilter mdFilter =
                        new FileChooser.ExtensionFilter("Text Files (*.groovy, *.txt)", "*.groovy", "*.txt");

                FileChooser.ExtensionFilter allFilesfilter =
                        new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");

                currentDocument =
                        FileChooserBuilder.create().title("Open Groovy File").
                        extensionFilters(mdFilter, allFilesfilter).build().
                        showOpenDialog(null).getAbsoluteFile();
            } else {
                currentDocument = f;
            }

            List<String> lines =
                    Files.readAllLines(Paths.get(currentDocument.getAbsolutePath()),
                    Charset.defaultCharset());

            String document = "";

            for (String l : lines) {
                document += l + "\n";
            }

            editor.setText(document);

            updateView();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    private void updateView() {

        GroovyClassLoader gcl = new GroovyClassLoader();
        gcl.parseClass(editor.getText());

        flow.getModel().setVisible(true);

        System.out.println("UPDATE UI");

        rootPane.getChildren().clear();

        if (UIBinding.scopes == null) {
            System.err.println("NO SCOPES");
            return;
        }

        for (Collection<Scope> scopeList : UIBinding.scopes.values()) {
            for (Scope s : scopeList) {
                scopeToFlow(s, flow);
            }
        }

        flow.setSkinFactories(new FXSkinFactory(rootPane));

    }

    public void scopeToFlow(Scope scope, VFlow parent) {

        VFlow result = parent.newSubFlow();

        String title = "" + scope.getType() + " " + scope.getName() + "(): " + scope.getId();

        result.getModel().setWidth(400);
        result.getModel().setHeight(300);

        result.getModel().setTitle(title);

        System.out.println("Title: " + title);
        
        
        VNode prevNode = null;
        

        for (Invocation i : scope.getControlFlow().getInvocations()) {
            
            VNode n = result.newNode();
            n.setInput(true, "control");
            n.setOutput(true, "control");
            
            if (prevNode!=null) {
                result.connect(prevNode, n, "control");
            }
            
            String mTitle = "" + i.getVarName() + "." + i.getMethodName() + "(): " + i.getId();
            n.setTitle(mTitle);
            n.setWidth(400);
            n.setHeight(100);
            
            prevNode = n;
        }

        for (Scope s : scope.getScopes()) {
            scopeToFlow(s, result);
        }
    }
}