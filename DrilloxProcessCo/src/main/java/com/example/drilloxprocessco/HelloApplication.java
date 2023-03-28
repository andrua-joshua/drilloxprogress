package com.example.drilloxprocessco;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load()/*FXMLLoader.load(getClass().getResource("NewPro.fxml"))*/, 700, 450);
            scene.getStylesheets().add(this
                    .getClass().getResource("hello.css").toExternalForm());
            stage.setResizable(false);
            Image icon = new Image(getClass().getResource("img.png").toExternalForm());
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setTitle("DrilloxProgress Pro");
            stage.show();
        }catch (Exception e){
            System.err.println("@Drillox Exceptions ^^: "+e);
        }
    }
    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        if (!RevBook.DataTitle.exists()) RevBook.DataTitle.createNewFile();
        if (!RevBook.DataProp.exists()) RevBook.DataProp.createNewFile();
        if (!ProjectBuilder.DataNames.exists()) ProjectBuilder.DataNames.createNewFile();
        if (!ProjectBuilder.DataProps.exists()) ProjectBuilder.DataProps.createNewFile();
        launch();
    }
}