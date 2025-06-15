package domain.rules;

import domain.entities.board.BoardIntf;
import domain.entities.player.Player;
import infrastructure.events.DirectionType;

public class MoveRule {
    public static boolean execute(int steps, DirectionType direction, Player currentPlayer, BoardIntf board) {
        int availableFuel = currentPlayer.getFuel();
        if (availableFuel < steps || steps == 0) {
            return false;
        }
        int move = switch (direction) {
            case LEFT -> -1 * steps;
            case RIGHT -> steps;
        };
        if (move < 0) {
            for (int i = -1; i > move; i--) {
                int xPosition = currentPlayer.getxPosition() + i - (currentPlayer.getTank().getWidth()/2);
                if (xPosition >= 0 && xPosition < board.getWidth() && !board.getMap()[xPosition][currentPlayer.getyPosition()].isBlank()) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < move; i++) {
                int xPosition = currentPlayer.getxPosition() + i + (currentPlayer.getTank().getWidth()/2);
                if (xPosition >= 0 && xPosition < board.getWidth() && !board.getMap()[xPosition][currentPlayer.getyPosition()].isBlank()) {
                    return false;
                }
            }
        }

        if (currentPlayer.getxPosition() + move < board.getWidth() && currentPlayer.getxPosition() + move >= 0) {
            currentPlayer.setxPosition(currentPlayer.getxPosition() + move);
        } else {
            switch (direction) {
                case LEFT:
                    currentPlayer.setxPosition(0);
                    break;
                case RIGHT:
                    currentPlayer.setxPosition(board.getWidth() - 1);
                    break;
            }
        }
        currentPlayer.consumeFuel(steps);
        board.setPlayerPositionOnBoard(currentPlayer, currentPlayer.getxPosition(), currentPlayer.getyPosition());

        return true;
    }
}
