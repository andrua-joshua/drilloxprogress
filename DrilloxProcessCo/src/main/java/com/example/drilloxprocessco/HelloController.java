package com.example.drilloxprocessco;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    public static String RRBT = null;
    public static String PRNM = null;
    @FXML
    public ImageView Pic;
    public ListView<String> Books_list;
    public ListView<String> projects_list;
    public TextField Hour;
    public TextField Min;
    public ChoiceBox<String> Track;


    private Stage stage;
    private Scene scene;
    Parent root;

    private TreeMap<String, RevBook> books;
    private TreeMap<String, ProjectBuilder> projects;
    private ObservableList<String> Bitems = FXCollections.observableArrayList();
    private ObservableList<String> Pitems = FXCollections.observableArrayList();
    private Set<String> titles;
    private Set<String> proNames;
    private Image pic = new Image(getClass().getResource("img.png").toExternalForm());
    private static Timer timer = new Timer("Alarm",true);

    private final Media track1 = new Media(getClass().getResource("tracks/unity.mp3").toExternalForm().toString());
    private final MediaPlayer mediaPlayer = new MediaPlayer(track1);
    private ObservableList<String> tracks = FXCollections.observableArrayList();

    public HelloController() {
    }

    @FXML
    protected void newRev(ActionEvent e) throws IOException {
        //todo here
        root = FXMLLoader.load(getClass().getResource("NewRev.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    @FXML
    protected void newPro(ActionEvent e) throws IOException {
        //todo here
        root = FXMLLoader.load(getClass().getResource("NewPro.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700, 450);
        scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //to retrive data to the RevBook class
        try {
            RevBook.Retrive();
            ProjectBuilder.Retrive();
            books = RevBook.getAllBooks(); //getting all the book objects from the RevBook bean
            projects = ProjectBuilder.getProjects(); //getting all the projects from the ProjectBuilder bean
            titles = books.keySet();
            proNames = projects.keySet();
        } catch (IOException e) {
            System.out.println("@Drillox Exceptions (IN.initialize method): "+e);
        }
        Bitems.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                int x=(int)(Math.ceil(Bitems.size()/2.0));
                Books_list.setPrefHeight(x*50);
                Books_list.setItems(Bitems);
            }
        });
        Pitems.addListener((ListChangeListener<? super String>) (change)->{
                projects_list.setPrefHeight(30*Pitems.size());
                projects_list.setItems(Pitems);
        });
        for (String title : titles) {
            Bitems.add(String.valueOf(title));
        }
        for (String name: proNames){
            Pitems.add(name);
        }

        //Pitems.addAll("CodTech Web Pro","Chess game Pro","Minhz Videoz Web Pro","Awakening Voice Church Web Pro","Tetris game");

        Pic.setFitHeight(100);
        Pic.setFitWidth(100);
        Pic.setImage(pic);



        //alarm Contents
        tracks.addAll("Unity","Nights");
        Track.setItems(tracks);

        /*Media track1 = new Media(getClass().getResource("tracks/unity.mp3").toExternalForm().toString());
        Media track2 = new Media(getClass().getResource("tracks/nights.mp3").toExternalForm().toString());
        if (Track.getValue() == "Nights")mediaPlayer = new MediaPlayer(track2);
        else mediaPlayer = new MediaPlayer(track1);
        */

    }

    public void getDetails(MouseEvent mouseEvent) throws IOException {
        int x=mouseEvent.getClickCount();
        if(x==2){
            HelloController.RRBT = Books_list.getSelectionModel().getSelectedItem();
            root = FXMLLoader.load(getClass().getResource("RevView.fxml"));
            stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root,700, 450);
            scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            //System.out.println("getDetails called: "+x);
        }
    }
    public void getDetail(MouseEvent mouseEvent) throws IOException {
        int x=mouseEvent.getClickCount();
        if(x==2){
            HelloController.PRNM = projects_list.getSelectionModel().getSelectedItem();
            root = FXMLLoader.load(getClass().getResource("ProView.fxml"));
            stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root,700, 450);
            scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            //System.out.println("getDetails called: "+x);
        }
    }

    public void play(ActionEvent actionEvent) {
        int hour = 0;
        if (Hour.getText()!=null) hour=Integer.parseInt(Hour.getText().toString());
        int min = 0;
        if (Min.getText()!=null) min=Integer.parseInt(Min.getText().toString());
        Alarm.player = mediaPlayer;
        Alarm.Hours = hour;
        Alarm.Min = min;
        timer.schedule(new Alarm(), 0);
    }

    public void pause(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }

    public void stop(ActionEvent actionEvent) {
        Alarm.stop();
    }
    private MediaPlayer kk(MediaPlayer player){
        return  player;
    }
}