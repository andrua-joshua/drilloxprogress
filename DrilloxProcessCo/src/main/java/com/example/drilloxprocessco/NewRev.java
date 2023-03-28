package com.example.drilloxprocessco;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewRev implements Initializable {
    @FXML
    public ImageView Pic;
    public TextField book_itle;
    public Spinner book_chpts;
    public Spinner book_pages;
    public Spinner book_PChpt;
    public Spinner book_PPage;
    public ChoiceBox book_hardness;
    public ChoiceBox book_Category;
    public Spinner book_lcp;
    public Spinner book_mcp;
    public Spinner book_hcp;


    private Stage stage;
    private Scene scene;
    private Parent root;

    private Image pic = new Image(getClass().getResource("img.png").toExternalForm());
    private String[] cat = {"PRACTICAL","THEORY"};
    private String[] hard = {"HARD","MEDIUM","EASY"};

    public void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pic.setFitWidth(100);
        Pic.setFitHeight(100);
        Pic.setImage(pic);

        book_Category.getItems().addAll(cat);;
        book_hardness.getItems().addAll(hard);

        SpinnerValueFactory<Integer> Total_chapters = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,500);
        SpinnerValueFactory<Integer> Total_pages = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5000);
        SpinnerValueFactory<Integer> HCP = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,500);
        SpinnerValueFactory<Integer> MCP = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,300);
        SpinnerValueFactory<Integer> LCP = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,200);
        SpinnerValueFactory<Integer> PPAGE = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5000);
        SpinnerValueFactory<Integer> PCHAPTER = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,500);
        Total_chapters.setValue(1);
        Total_pages.setValue(1);
        HCP.setValue(1);
        LCP.setValue(1);
        MCP.setValue(1);
        PPAGE.setValue(1);
        PCHAPTER.setValue(1);

        book_chpts.setValueFactory(Total_chapters);
        book_pages.setValueFactory(Total_pages);
        book_PChpt.setValueFactory(PCHAPTER);
        book_PPage.setValueFactory(PPAGE);
        book_hcp.setValueFactory(HCP);
        book_lcp.setValueFactory(LCP);
        book_mcp.setValueFactory(MCP);

        book_lcp.valueProperty().addListener((observableValue, o, t1) -> {
            if(Integer.parseInt(observableValue.getValue().toString())==0) LCP.setValue(1);
        });
        book_mcp.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(Integer.parseInt(observableValue.getValue().toString())==0) MCP.setValue(1);
            }
        });
        book_hcp.valueProperty().addListener((observableValue, o, t1) -> {
            if(Integer.parseInt(observableValue.getValue().toString())==0) HCP.setValue(1);
        });
        book_pages.valueProperty().addListener((observableValue, o, t1) -> {
            if(Integer.parseInt(observableValue.getValue().toString())==0) Total_pages.setValue(1);
        });
        book_chpts.valueProperty().addListener((observableValue, o, t1) -> {
            if(Integer.parseInt(observableValue.getValue().toString())==0) Total_chapters.setValue(1);
        });
        book_PPage.valueProperty().addListener((observableValue, o, t1) -> {
            if(Integer.parseInt(observableValue.getValue().toString())==0) PPAGE.setValue(1);
        });
        book_PChpt.valueProperty().addListener((observableValue, o, t1) -> {
            if (Integer.parseInt(observableValue.getValue().toString())==0) PCHAPTER.setValue(1);
        });
    }

    public void cancle(ActionEvent actionEvent) {
    }

    public void addB(ActionEvent actionEvent) throws IOException, CloneNotSupportedException {
        HardnessConstant hard = null;
        Category cat = null;

        switch (book_hardness.getValue().toString()){
            case "HARD":
                hard = HardnessConstant.HARD;
                break;
            case "MEDIUM":
                hard = HardnessConstant.MEDIUM;
                break;
            case "EASY":
                hard = HardnessConstant.EASY;
                break;
            default:
                break;
        }
        switch (book_Category.getValue().toString()){
            case "PRACTICAL":
                cat = Category.PRACTICAL;
                break;
            case "THEORY":
                cat = Category.THEORY;
                break;
            default:
                break;
        }
        //String BookTitle, int TotalChapters, int TotalPages, HardnessConstant Hardness, Category category,int lCP, int hCP, int mCP
        RevBook book  = new RevBook(book_itle.getText(),Integer.parseInt(book_chpts.getValue().toString()),Integer.parseInt(book_pages.getValue().toString()),
                hard,cat,Integer.parseInt(book_lcp.getValue().toString()),Integer.parseInt(book_hcp.getValue().toString()),Integer.parseInt(book_mcp.getValue().toString()));
        book.setInitialProgressChapter(Integer.parseInt(book_PChpt.getValue().toString()));
        book.setInitialProgressPage(Integer.parseInt(book_PPage.getValue().toString()));
    }
    public void changed(ObservableValue observableValue, Object o, Object t1) {

    }
}
