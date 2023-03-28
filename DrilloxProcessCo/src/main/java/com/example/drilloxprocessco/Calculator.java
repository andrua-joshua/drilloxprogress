package com.example.drilloxprocessco;

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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Calculator implements Initializable {

    public ImageView Pic;
    public Label username;
    public VBox skills_list;
    public Spinner<Integer> nodvalue;  //for number of days entry
    public Spinner<Integer> nolvalue;  //for number of laps entry
    public Spinner<Integer> lpdvalue; //for number of laps per day
    public Spinner<Integer> hoursValue; //for hours  representation of the laps
    public Spinner<Integer> lapsValue; //number of laps
    private Parent root;
    private Stage stage;
    private Scene scene;

    private Image pic = new Image(getClass().getResource("img.png").toExternalForm());

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
        Pic.setFitHeight(100);
        Pic.setFitWidth(100);
        Pic.setImage(pic);

        SpinnerValueFactory<Integer> nol = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000);
        SpinnerValueFactory<Integer> hours = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000);
        SpinnerValueFactory<Integer> lpd = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000);
        SpinnerValueFactory<Integer> lps = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000);
        SpinnerValueFactory<Integer> nod = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000);

        nodvalue.setValueFactory(nod);  //nod = nol/lpd
        lpdvalue.setValueFactory(lpd);  //lpd = nol/nod
        nolvalue.setValueFactory(nol);  //nol = nol*lpd

        hoursValue.setValueFactory(hours); //hours = lps*3
        lapsValue.setValueFactory(lps);    //lps = hours/3

        hoursValue.valueProperty().addListener((obj, val1,val2)->{
            lps.setValue(val2/3);
        });
        lapsValue.valueProperty().addListener((obj, val1, val2)->{
            hours.setValue(val2*3);
        });

        AtomicBoolean callFromlpd = new AtomicBoolean(false);
        AtomicBoolean callFromnol = new AtomicBoolean(false);
        AtomicBoolean callFromnod = new AtomicBoolean(false);

        nodvalue.valueProperty().addListener((observableValu,integer,t1) ->{
            if (t1==null) t1=0;
            if (!callFromlpd.get()){
                callFromnod.set(true);
                if (t1!=0) lpd.setValue(nol.getValue()/t1);
            }
            if (!callFromnol.get()){
                callFromnod.set(true);
                nol.setValue(t1*lpd.getValue());
            }
            callFromnod.set(false);
        });
        nolvalue.valueProperty().addListener((observableValu,integer,t1) ->{
            if (t1==null) t1 = 0;
            if (!callFromlpd.get()){
                callFromnol.set(true);
                if (nod.getValue()!=0) lpd.setValue(t1/nod.getValue());
            }
            if (!callFromnod.get()){
                callFromnol.set(true);
                if (lpd.getValue()!=0)nod.setValue(t1/lpd.getValue());
            }
            callFromnol.set(false);
        });
        lpdvalue.valueProperty().addListener((observableValu,integer,t1) ->{
            if (t1==null) t1 = 0;
            if (!callFromnol.get()){
                callFromlpd.set(true);
                nol.setValue(nod.getValue()*t1);
            }
            if (!callFromnod.get()){
                callFromlpd.set(true);
                if(t1!=0)nol.setValue(nol.getValue()/t1);
            }
            callFromlpd.set(false);
        });
    }
}
