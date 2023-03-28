package com.example.drilloxprocessco;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ProView implements Initializable {
    public static String ViewAction = null;
    public ImageView Pic;
    public Label username;
    public ProgressBar BProgress;
    public Label AvReq2NeReqRatio;
    public Label Cparts2PartsRatio;
    public Label readynessValue;
    public Label ProgressValue;
    public Label projectName;
    public Label Time;

    private Parent root;
    private Stage stage;
    private Scene scene;


    private final Image pic = new Image(getClass().getResource("img.png").toExternalForm());
    private ProjectBuilder project;
    public void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void Update(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ProUpdate.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void Terminate(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        projectName.setText(HelloController.PRNM);
        project = ProjectBuilder.getProject(projectName.getText());

        SpinnerValueFactory<Integer> spin  = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000);

        //initializing the contents
        ProgressValue.setText(String.valueOf((int)Math.round(project.getProgress()))+"%");
        readynessValue.setText(String.valueOf((int)Math.round(project.getRatioOfAvailableRequirements_NeededRequirements()*100))+"%");
        AvReq2NeReqRatio.setText(String.valueOf(project.getRatioOfAvailableRequirements_NeededRequirements()));
        if (project.getProjectParts().size()!=0)Cparts2PartsRatio.setText(String.valueOf(project.getProjectCompletedParts().size()/project.getProjectParts().size()));
        else System.out.println("@@@@@@@@@@@@@ "+project.getProjectCompletedParts().size()+" : "+project.getProjectParts().size());
        BProgress.setProgress(project.getProgress()/100);
        Time.setText(String.valueOf(project.getEstimatedAccomplishmentTime()));

        //styling the nodes depending on some given criteria
        if (Integer.parseInt(readynessValue.getText().toString().substring(0,readynessValue.getText().toString().length()-1))<45)
            readynessValue.setStyle("-fx-text-fill: #ff1010");
        else if (Integer.parseInt(readynessValue.getText().toString().substring(0,readynessValue.getText().toString().length()-1))<60)
            readynessValue.setStyle("-fx-text-fill: #1010ff");
        else readynessValue.setStyle("-fx-text-fill: #10ff10");
        if (Integer.parseInt(ProgressValue.getText().toString().substring(0,ProgressValue.getText().toString().length()-1))<45) {
            ProgressValue.setStyle("-fx-text-fill: #ff1010");
            BProgress.setStyle("-fx-accent: #ff1010");
        }
        else if (Integer.parseInt(ProgressValue.getText().toString().substring(0,ProgressValue.getText().toString().length()-1))<60) {
            ProgressValue.setStyle("-fx-text-fill: #1010ff");
            BProgress.setStyle("-fx-accent: #1010ff");
        }
        else {
            ProgressValue.setStyle("-fx-text-fill: #10ff10");
            BProgress.setStyle("-fx-accent: #10ff10");
        }

        if (Float.parseFloat(AvReq2NeReqRatio.getText().toString())<0.4)
            AvReq2NeReqRatio.setStyle("-fx-text-fill: #ff1010");
        else if (Float.parseFloat(AvReq2NeReqRatio.getText().toString())<0.5)
            AvReq2NeReqRatio.setStyle("-fx-text-fill: #1010ff");
        else AvReq2NeReqRatio.setStyle("-fx-text-fill: #10ff10");

        if (Float.parseFloat(Cparts2PartsRatio.getText().toString())<0.4)
            Cparts2PartsRatio.setStyle("-fx-text-fill: #ff1010");
        else if (Float.parseFloat(Cparts2PartsRatio.getText().toString())<0.5)
            Cparts2PartsRatio.setStyle("-fx-text-fill: #1010ff");
        else Cparts2PartsRatio.setStyle("-fx-text-fill: #10ff10");

        Pic.setFitHeight(100);
        Pic.setFitWidth(100);
        Pic.setImage(pic);
    }

    public void ShowNeRq(ActionEvent e) throws IOException {
        ProView.ViewAction = "NeReq";
        root = FXMLLoader.load(getClass().getResource("View.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void ShowParts(ActionEvent e) throws IOException {
        ProView.ViewAction = "Parts";
        root = FXMLLoader.load(getClass().getResource("View.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
