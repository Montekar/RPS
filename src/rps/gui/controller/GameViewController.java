package rps.gui.controller;

// Java imports

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

import java.util.Optional;
import java.util.Random;

/**
 * @author smsj
 */
public class GameViewController {
    @FXML
    private Label textFirstPlayer, textSecondPlayer, textFirstPlayerScore, textSecondPlayerScore, status;
    @FXML
    private ImageView imgFirstPlayer, imgSecondPlayer;

    private GameManager gameManager;

    public void createGame(String firstPName) {
        IPlayer human = new Player(firstPName, PlayerType.Human);

        String botName = getRandomBotName();
        IPlayer bot = new Player(botName, PlayerType.AI);

        gameManager = new GameManager(human, bot);

        textFirstPlayer.setText(human.getPlayerName());
        textSecondPlayer.setText(bot.getPlayerName());

        status.setText("Your Turn:");
    }

    public void playRock(ActionEvent actionEvent) {
        int roundNumber = gameManager.getGameState().getRoundNumber();
        gameManager.playRound(Move.Rock);
        updateGUI(roundNumber);
    }

    public void playPaper(ActionEvent actionEvent) {
        int roundNumber = gameManager.getGameState().getRoundNumber();
        gameManager.playRound(Move.Paper);
        updateGUI(roundNumber);
    }

    public void playScissors(ActionEvent actionEvent) {
        int roundNumber = gameManager.getGameState().getRoundNumber();
        gameManager.playRound(Move.Scissor);
        updateGUI(roundNumber);
    }

    private void updateGUI(int roundNumber) {
        Optional<Result> res = gameManager.getGameState().getHistoricResults().stream().filter(x -> x.getRoundNumber() == roundNumber).findFirst();
        if (res.isPresent()) {
            Result result = res.get();
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
                updateScore();
            } else {
                setImageMove(imgSecondPlayer, result.getWinnerMove());
                setImageMove(imgFirstPlayer, result.getLoserMove());
                status.setText("It's A Tie!");
            }

        }
    }

    private void updateScore() {
        int firstPlayerScore = 0;
        int secondPlayerScore = 0;

        for (Result result : gameManager.getGameState().getHistoricResults()) {
            if(result.getType() != ResultType.Tie) {
                if (result.getWinnerPlayer().getPlayerType() == PlayerType.Human) {
                    firstPlayerScore++;
                } else {
                    secondPlayerScore++;
                }
            }
        }
        textFirstPlayerScore.setText(String.valueOf(firstPlayerScore));
        textSecondPlayerScore.setText(String.valueOf(secondPlayerScore));
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
