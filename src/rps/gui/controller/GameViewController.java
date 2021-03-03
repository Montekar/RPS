package rps.gui.controller;

// Java imports

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rps.bll.game.GameManager;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author smsj
 */
public class GameViewController {
    @FXML
    private Label textFirstPlayer, textSecondPlayer, status;
    @FXML
    private ImageView imgFirstPlayer, imgSecondPlayer;

    private GameManager gameManager;

    public void createGame(String firstPName) {
        IPlayer human = new Player(firstPName, PlayerType.Human);

        String botName = getRandomBotName();
        IPlayer bot = new Player(botName, PlayerType.AI);

        textFirstPlayer.setText(human.getPlayerName());
        textSecondPlayer.setText(bot.getPlayerName());

        gameManager = new GameManager(human, bot);

        status.setText("Your Turn:");
    }

    public void playRock(ActionEvent actionEvent) {
        int round = gameManager.getGameState().getRoundNumber();
        gameManager.playRound(Move.Rock);
        Optional<Result> result = gameManager.getGameState().getHistoricResults().stream().filter(x -> x.getRoundNumber() == round).findFirst();
        result.ifPresent(this::updateMovesGUI);
    }

    public void playPaper(ActionEvent actionEvent) {
        int round = gameManager.getGameState().getRoundNumber();
        gameManager.playRound(Move.Paper);
        Optional<Result> result = gameManager.getGameState().getHistoricResults().stream().filter(x -> x.getRoundNumber() == round).findFirst();
        result.ifPresent(this::updateMovesGUI);
    }

    public void playScissors(ActionEvent actionEvent) {
        int round = gameManager.getGameState().getRoundNumber();
        gameManager.playRound(Move.Scissor);
        Optional<Result> result = gameManager.getGameState().getHistoricResults().stream().filter(x -> x.getRoundNumber() == round).findFirst();
        result.ifPresent(this::updateMovesGUI);
    }

    private void updateMovesGUI(Result result) {
        if (result.getType() != ResultType.Tie) {
            if (result.getWinnerPlayer().getPlayerType() == PlayerType.Human) {
                setImageMove(imgFirstPlayer, result.getWinnerMove());
                setImageMove(imgSecondPlayer, result.getLoserMove());
                status.setText("You Won!");
            } else {
                setImageMove(imgSecondPlayer, result.getWinnerMove());
                setImageMove(imgFirstPlayer, result.getLoserMove());
                status.setText("You Lost!");
            }
        } else {
            setImageMove(imgSecondPlayer, result.getWinnerMove());
            setImageMove(imgFirstPlayer, result.getLoserMove());
            status.setText("It's A Tie!");
        }
    }

    private void setImageMove(ImageView playerImage, Move move) {
        switch (move) {
            case Rock:
                playerImage.setImage(new Image(getClass().getResourceAsStream("../images/rockorange.png")));
                break;
            case Paper:
                playerImage.setImage(new Image(getClass().getResourceAsStream("../images/paperorange.png")));
                break;
            case Scissor:
                playerImage.setImage(new Image(getClass().getResourceAsStream("../images/scissorsorange.png")));
                break;
        }
    }

    private String getRandomBotName() {
        String[] botNames = new String[]{
                "R2D2",
                "Mr. Data",
                "3PO",
                "Bender",
                "Marvin the Paranoid Android",
                "Bishop",
                "Robot B-9",
                "HAL"
        };
        int randomNumber = new Random().nextInt(botNames.length - 1);
        return botNames[randomNumber];
    }

    public String getResultAsString(Result result) {
        String statusText = result.getType() == ResultType.Win ? "wins over " : "ties ";

        return "Round #" + result.getRoundNumber() + ":" +
                result.getWinnerPlayer().getPlayerName() +
                " (" + result.getWinnerMove() + ") " +
                statusText + result.getLoserPlayer().getPlayerName() +
                " (" + result.getLoserMove() + ")!";
    }
}
