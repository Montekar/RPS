package rps.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Random;

public class StartController {
    public TextField fieldName;

    public void startGame(ActionEvent ae) {
        if(!fieldName.getText().isEmpty()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../view/GameView.fxml"));
                Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickGenerate(ActionEvent actionEvent) {
        Random rand = new Random();
        fieldName.setText("Player" + (rand.nextInt(999) + 1));
    }
}
