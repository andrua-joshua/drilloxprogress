package com.example.drilloxprocessco;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RevView implements Initializable {

    public ImageView Pic;
    public Label RBTitle;
    public Label EstimatedAccomplishmentTimeValue;
    public Label AverageChapterPagesValue;
    public Label TotalPagesValue;
    public Label TotalChaptersValue;
    public Label LastLapProgressValue;
    public Label OverallProgressValue;
    public Label ProgressPageValue;
    public Label ProgressChapterValue;
    public ProgressBar BProgress;

    private Parent root;
    private Stage stage;
    private Scene scene;

    private Image pic = new Image(getClass().getResource("img.png").toExternalForm());
    private RevBook book;

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
        try {
            RevBook.Retrive();
        } catch (IOException e) {
            System.out.println("@Drillox Exceptions (IN.initialize[RevView]) : "+e);
        }
        RBTitle.setText(HelloController.RRBT);
        book = RevBook.getAllBooks().get(HelloController.RRBT);
        OverallProgressValue.setText(String.valueOf((int)Math.round(book.getOverallProgress()))+"%");
        LastLapProgressValue.setText(String.valueOf((int)Math.round(book.getLapProgress()))+"%");
        TotalChaptersValue.setText(String.valueOf((int)Math.round(book.getTotalNumberOfChapters())));
        TotalPagesValue.setText(String.valueOf((int)Math.round(book.getTotalnumberOfPages())));
        AverageChapterPagesValue.setText(String.valueOf((int)Math.round(book.getAverageChapterPages())));
        EstimatedAccomplishmentTimeValue.setText(String.valueOf((int)Math.round(book.getEstimatedRemainingPeriod()))+" (3hrs)laps");
        ProgressChapterValue.setText(String.valueOf((int)Math.round(book.getInitialProgressChapter())));
        ProgressPageValue.setText(String.valueOf((int)Math.round(book.getInitialProgressPage())));

        BProgress.setProgress((double) (book.getOverallProgress()/100));

        //some custom styling
        if (Integer.parseInt(OverallProgressValue.getText().toString().substring(0,OverallProgressValue.getText().toString().length()-1))<45) {
            OverallProgressValue.setStyle("-fx-text-fill:#ff1010");
            BProgress.setStyle("-fx-accent:#ff1010");
        }
        else if (Integer.parseInt(OverallProgressValue.getText().toString().substring(0,OverallProgressValue.getText().toString().length()-1))<60) {
            OverallProgressValue.setStyle("-fx-text-fill:#1010ff");
            BProgress.setStyle("-fx-accent:#1010ff");
        }
        else {OverallProgressValue.setStyle("-fx-text-fill:#10ff10"); BProgress.setStyle("-fx-accent:#10ff10");}
        if (Integer.parseInt(LastLapProgressValue.getText().toString().substring(0,LastLapProgressValue.getText().toString().length()-1))<45){
            LastLapProgressValue.setStyle("-fx-text-fill:#ff1010");
        }
        else if (Integer.parseInt(LastLapProgressValue.getText().toString().substring(0,LastLapProgressValue.getText().toString().length()-1))<60)
            LastLapProgressValue.setStyle("-fx-text-fill:#1010ff");
        else LastLapProgressValue.setStyle("-fx-text-fill:#10ff10");
        if ((Integer.parseInt(ProgressChapterValue.getText().toString())/Integer.parseInt(TotalChaptersValue.getText().toString())<1))
            ProgressChapterValue.setStyle("-fx-text-fill:red");
        else ProgressChapterValue.setStyle("-fx-text-fill:green");
        if ((Integer.parseInt(ProgressPageValue.getText().toString())/Integer.parseInt(TotalPagesValue.getText().toString())<1))
            ProgressPageValue.setStyle("-fx-text-fill:red");
        else ProgressPageValue.setStyle("-fx-text-fill:green");

        Pic.setFitHeight(100);
        Pic.setFitWidth(100);
        Pic.setImage(pic);
    }

    public void Calculator(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Calculator.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void Update(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Update.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
