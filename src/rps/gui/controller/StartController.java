package rps.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Random;

public class StartController {
    @FXML
    public TextField fieldName;

    public void startGame(ActionEvent ae) {
        if(!fieldName.getText().isEmpty()) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameView.fxml"));
                GameViewController gvc = new GameViewController();
                loader.setController(gvc);
                Parent gameView = loader.load();
                Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                Scene scene = new Scene(gameView);
                stage.setScene(scene);

                gvc.createGame(fieldName.getText());

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