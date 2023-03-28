package com.example.drilloxprocessco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProUpdate implements Initializable {

    public Label Nbook; //for the project name
    public ListView<TextField> projectParts;
    public ListView<TextField> projectCParts;
    public ListView<TextField> projectRequirements;
    public ListView<TextField> AvailableRequirements;
    public ImageView Pic;

    private Parent root;
    private Stage stage;
    private Scene scene;
    private ProjectBuilder project;

    private Image pic = new Image(getClass().getResource("img.png").toExternalForm());
    private ObservableList<TextField> Parts = FXCollections.observableArrayList();
    private ObservableList<TextField> Cparts = FXCollections.observableArrayList();
    private ObservableList<TextField> NeReq = FXCollections.observableArrayList();
    private ObservableList<TextField> AvReq = FXCollections.observableArrayList();

    public void newPart(ActionEvent actionEvent) {
        TextField av = new TextField();
        av.setPromptText("Enter part");
        Parts.add(av);
        projectParts.setItems(Parts);
        projectParts.setPrefHeight((double)33*Parts.size());
    }

    public void newCPart(ActionEvent actionEvent) {
        TextField av = new TextField();
        av.setPromptText("Enter part");
        Cparts.add(av);
        projectCParts.setItems(Cparts);
        projectCParts.setPrefHeight((double)33*Cparts.size());
    }

    public void newRequirement(ActionEvent actionEvent) {
        TextField av = new TextField();
        av.setPromptText("Enter Requirement");
        NeReq.add(av);
        projectRequirements.setItems(NeReq);
        projectRequirements.setPrefHeight((double)33*NeReq.size());
    }

    public void newAvailableR(ActionEvent actionEvent) {
        TextField av = new TextField();
        av.setPromptText("Enter Available Req");
        AvReq.add(av);
        AvailableRequirements.setItems(AvReq);
        AvailableRequirements.setPrefHeight((double)33*AvReq.size());
    }

    public void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ProView.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public void update(ActionEvent e) throws IOException{
        ArrayList<String> parts = new ArrayList<>();
        ArrayList<String> Cpart = new ArrayList<>();
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
        for (TextField cp: projectCParts.getItems()){
            if (cp!=null) Cpart.add(cp.getText());
        }

        project.AddProjectParts(parts.toArray(String[]::new));
        project.AddProjectCompletedParts(Cpart.toArray(String[]::new));
        project.AddNeededRequirements(req.toArray(String[]::new));
        project.AddAvailabeRequirements(AvReq.toArray(String[]::new));

        root = FXMLLoader.load(getClass().getResource("ProView.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        project  = ProjectBuilder.getProjects().get(HelloController.PRNM);

        projectCParts.setItems(Parts);
        projectCParts.setItems(Cparts);
        projectRequirements.setItems(NeReq);
        AvailableRequirements.setItems(AvReq);

        Pic.setFitHeight(100);
        Pic.setFitWidth(100);
        Pic.setImage(pic);
    }
}
