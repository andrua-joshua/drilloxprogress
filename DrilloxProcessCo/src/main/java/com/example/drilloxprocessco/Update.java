package com.example.drilloxprocessco;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Update implements Initializable {

    public ImageView Pic;
    public Label username;
    public VBox skills_list;
    public Label Heading;
    public Spinner book_PChpt;
    public Label BPChpt;
    public Spinner book_PPage;
    public Label BPPage;
    private Parent root;
    private Stage stage;
    private Scene scene;

    private Image pic = new Image(getClass().getResource("img.png").toExternalForm());
    private RevBook book;
    
    public void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RevView.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Heading.setText(HelloController.RRBT);
        book = RevBook.getAllBooks().get(HelloController.RRBT);

        SpinnerValueFactory<Integer> PPAGE = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5000);
        SpinnerValueFactory<Integer> PCHAPTER = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,500);
        book_PChpt.setValueFactory(PCHAPTER);
        book_PPage.setValueFactory(PPAGE);

        Pic.setFitHeight(100);
        Pic.setFitWidth(100);
        Pic.setImage(pic);

        book_PPage.valueProperty().addListener((observableValue, o, t1) -> {
            if(Integer.parseInt(observableValue.getValue().toString())==0) PPAGE.setValue(1);
        });
        book_PChpt.valueProperty().addListener((observableValue, o, t1) -> {
            if (Integer.parseInt(observableValue.getValue().toString())==0) PCHAPTER.setValue(1);
        });
    }

    public void update(ActionEvent e) throws IOException {
        book.setInitialProgressChapter(Integer.parseInt(book_PChpt.getValue().toString()));
        book.setInitialProgressPage(Integer.parseInt(book_PPage.getValue().toString()));

        //go back to the view screen
        root = FXMLLoader.load(getClass().getResource("RevView.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
