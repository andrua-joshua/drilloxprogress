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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class View implements Initializable {

    public ImageView Pic;
    public Label viewName;
    public VBox values;

    private Parent root;
    private Stage stage;
    private Scene scene;

    private ObservableList<AnchorPane> Reqs = FXCollections.observableArrayList();
    private ObservableList<AnchorPane> Parts = FXCollections.observableArrayList();
    private final Image pic = new Image(getClass().getResource("img.png").toExternalForm());
    private final ProjectBuilder project = ProjectBuilder.getProjects().get(HelloController.PRNM);

    public void back(ActionEvent e) throws IOException {
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
        try {
            ProjectBuilder.Retrive();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean partOtherWise = false;
        boolean ReqOtherWise = false;

        if (ProView.ViewAction.equals("NeReq")){
            int reserve = 0;
            for (String req: project.getNeededRequirements()){
                if (project.getAvailableRequirements().size()==0){
                    AnchorPane anchor = new AnchorPane();
                    Label p = new Label(req);
                    p.setLayoutY(2);
                    p.setLayoutX(100);
                    anchor.getChildren().add(p);
                    Reqs.add(anchor);
                    ReqOtherWise = true;
                    //System.out.println(project.getProjectName()+"<<<<>>>>><<<<<Other Wise>>>>><<<<>>>>> "+req+" : X"+project.getNeededRequirements().size()+" : "+project.getAvailableRequirements().size());
                }
                else if (project.getAvailableRequirements().contains(req)){
                    AnchorPane anchor = new AnchorPane();
                    Label p = new Label(req);
                    ImageView img = new ImageView(new Image(getClass().getResource("tick.png").toExternalForm()));
                    img.setLayoutX(300);
                    img.setLayoutY(2);
                    p.setLayoutY(2);
                    p.setLayoutX(100);
                    anchor.getChildren().addAll(p,img);
                    Reqs.add(anchor);
                    reserve++;
                    //System.out.println(project.getProjectName()+"<<<<>>>>><<<<<True>>>>><<<<>>>>> "+req+" : "+project.getNeededRequirements().size()+" : "+project.getAvailableRequirements().size());
                }
            }
            if (!ReqOtherWise)if (reserve<project.getNeededRequirements().size()){
                for (String req: project.getNeededRequirements()){
                    if (!project.getAvailableRequirements().contains(req)){
                        AnchorPane anchor = new AnchorPane();
                        Label p = new Label(req);
                        p.setLayoutY(2);
                        p.setLayoutX(100);
                        anchor.getChildren().add(p);
                        Reqs.add(anchor);
                        //System.out.println(project.getProjectName()+"<<<<>>>>><<<<<False>>>>><<<<>>>>> "+req+" : "+project.getNeededRequirements().size()+" : "+project.getAvailableRequirements().size());
                    }
                }
            }

            viewName.setText(HelloController.PRNM+": Requirements");
            values.getChildren().addAll(Reqs);
        }

        if (ProView.ViewAction.equals("Parts")){
            int reserver = 0;
            for (String part: project.getProjectParts()){
                if (project.getProjectCompletedParts().size()==0){
                    AnchorPane anchor = new AnchorPane();
                    Label p = new Label(part);
                    p.setLayoutY(2);
                    p.setLayoutX(100);
                    anchor.getChildren().add(p);
                    Parts.add(anchor);
                    partOtherWise = true;
                }
                if (project.getProjectCompletedParts().contains(part)){
                    //System.out.println(project.getProjectName()+"<<<<>>>>><<<<<True>>>>><<<<>>>>> "+part+" : ");
                    AnchorPane anchor = new AnchorPane();
                    Label p = new Label(part);
                    ImageView img = new ImageView(new Image(getClass().getResource("tick.png").toExternalForm()));
                    img.setLayoutX(300);
                    img.setLayoutY(2);
                    p.setLayoutY(2);
                    p.setLayoutX(100);
                    anchor.getChildren().addAll(p,img);
                    Parts.add(anchor);
                    reserver++;
                }
            }
            if (!partOtherWise)if (reserver!=project.getProjectParts().size()){
                for (String part: project.getProjectParts()){
                    if (!project.getProjectCompletedParts().contains(part)){
                        //System.out.println(project.getProjectName()+"<<<<>>>>><<<<<False>>>>><<<<>>>>> "+part+" : ");
                        AnchorPane anchor = new AnchorPane();
                        Label p = new Label(part);
                        p.setLayoutY(2);
                        p.setLayoutX(100);
                        anchor.getChildren().addAll(p);
                        Parts.add(anchor);
                        reserver++;
                    }
                }
            }
            viewName.setText(HelloController.PRNM+": Parts");
            values.getChildren().addAll(Parts);
        }

        Reqs = FXCollections.observableArrayList();
        Parts = FXCollections.observableArrayList();

        Pic.setFitHeight(100);
        Pic.setFitWidth(100);
        Pic.setImage(pic);
    }
}
