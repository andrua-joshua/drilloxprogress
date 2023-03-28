package com.example.drilloxprocessco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewPro implements Initializable {
    @FXML
    public ImageView Pic;
    public TextField proName;
    public ListView<TextField> projectParts;
    public ListView<TextField> projectRequirements;
    public ListView<TextField> AvailableRequirements;
    public Spinner<Integer> TgTime;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<TextField> partsItem = FXCollections.observableArrayList();
    private ObservableList<TextField> ReqItems = FXCollections.observableArrayList();
    private ObservableList<TextField> AvReqItems = FXCollections.observableArrayList();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pic.setFitHeight(100);
        Pic.setFitWidth(100);
        Pic.setMouseTransparent(true);
        Pic.setImage(pic);

        SpinnerValueFactory<Integer> spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000);
        TgTime.setValueFactory(spin);

        projectParts.setItems(partsItem);
        projectRequirements.setItems(ReqItems);
        AvailableRequirements.setItems(AvReqItems);
    }

    public void addB(ActionEvent actionEvent) {
        ArrayList<String> parts = new ArrayList<>();
        ArrayList<String> req = new ArrayList<>();
        ArrayList<String> AvReq = new ArrayList<>();
        for (TextField part:projectParts.getItems()){
            if (part.getText()!=null)parts.add(part.getText());
        }
        for (TextField re:projectRequirements.getItems()){
            if (re.getText()!=null)req.add(re.getText());
        }
        for (TextField av:AvailableRequirements.getItems()){
            if (av.getText()!=null)AvReq.add(av.getText());
        }

        project = new ProjectBuilder(proName.getText().toString());
        project.AddNeededRequirements(req.toArray(String[]::new));
        project.AddProjectParts(parts.toArray(String[]::new));
        project.AddAvailabeRequirements(AvReq.toArray(String[]::new));
        project.setEstimatedAccomplishmentTime(TgTime.getValue());
    }

    public void newAvailableR(ActionEvent actionEvent) {
        TextField av = new TextField();
        av.setPromptText("Enter Available Req");
        AvReqItems.add(av);
        AvailableRequirements.setPrefHeight((double)33*AvReqItems.size());
    }

    public void newRequirement(ActionEvent actionEvent) {
        TextField av = new TextField();
        av.setPromptText("Enter Requirement");
        ReqItems.add(av);
        projectRequirements.setPrefHeight((double)33*ReqItems.size());
    }

    public void newPart(ActionEvent actionEvent) {
        TextField av = new TextField();
        av.setPromptText("Enter part");
        partsItem.add(av);
        projectParts.setPrefHeight((double)33*partsItem.size());
    }
}
