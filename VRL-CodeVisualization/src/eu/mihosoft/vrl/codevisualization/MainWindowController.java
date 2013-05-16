/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.codevisualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class MainWindowController implements Initializable {
    
    File currentDocument;

    @FXML
    TextArea editor;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
                    new FileChooser.ExtensionFilter("Text Files (*.md, *.txt)", "*.md", "*.txt");

            FileChooser.ExtensionFilter allFilesfilter =
                    new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");

            currentDocument =
                    FileChooserBuilder.create().title("Save Markdown File").
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
    }

    @FXML
    public void onCloseAction(ActionEvent e) {
    }



    private void loadTextFile(File f) {


        try {
            if (f == null) {
                FileChooser.ExtensionFilter mdFilter =
                        new FileChooser.ExtensionFilter("Text Files (*.md, *.txt)", "*.md", "*.txt");

                FileChooser.ExtensionFilter allFilesfilter =
                        new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");

                currentDocument =
                        FileChooserBuilder.create().title("Open Markdown File").
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
        throw new UnsupportedOperationException("Not supported yet."); // TODO NB-AUTOGEN
    }
}
