package rps.bll.player;

//Project imports

import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.ArrayList;
import java.util.Random;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String getPlayerName() {
        return name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
    }


    /**
     * Decides the next move for the bot...
     *
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        Random random = new Random();
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        //Getting opponent match history
        ArrayList<Move> opponentHistory = new ArrayList<>();
        for (Result result : results) {
            if (result.getWinnerPlayer().getPlayerType() == getPlayerType()) {
                opponentHistory.add(result.getLoserMove());
            } else {
                opponentHistory.add(result.getWinnerMove());
            }
        }

        //AI LOGIC
            if (opponentHistory.size() > 3 && random.nextInt(100) > 49) {
                int rockUsed = 0;
                int paperUsed = 0;
                int scissorsUsed = 0;

                for (int i = opponentHistory.size() - 3; i < opponentHistory.size(); i++) {
                    if (opponentHistory.get(i) == Move.Rock) {
                        rockUsed++;
                    } else if (opponentHistory.get(i) == Move.Paper) {
                        paperUsed++;
                    } else if (opponentHistory.get(i) == Move.Scissor) {
                        scissorsUsed++;
                    }

                }
                int mostOccurences = Math.max(Math.max(rockUsed, paperUsed), scissorsUsed);

                if (mostOccurences == rockUsed) {
                    return Move.Paper;
                } else if (mostOccurences == paperUsed) {
                    return Move.Scissor;
                } else {
                    return Move.Rock;
                }
            }

        //Random IF AI doesnt execute
        Move nextMove = null;
        switch (random.nextInt(3)) {
            case 0:
                nextMove = Move.Rock;
                break;
            case 1:
                nextMove = Move.Paper;
                break;
            case 2:
                nextMove = Move.Scissor;
                break;
        }

        return nextMove;
    }
}
