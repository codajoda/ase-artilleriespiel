package domain.rules;

import domain.entities.board.Board;
import domain.entities.board.BoardIntf;
import domain.entities.player.Player;
import domain.entities.projectiles.HorizontalProjectile;
import domain.entities.tanks.SmallTank;
import domain.entities.tanks.BigTank;
import domain.entities.projectiles.NormalProjectile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ShootRuleTest {
    @Test
    void testHitPlayer() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(currentPlayer, opponentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }

        List<Player> playersHit = ShootRule.execute(45, 21, players, currentPlayer, board, new NormalProjectile());

        Assertions.assertEquals(List.of(opponentPlayer), playersHit);
    }

    @Test
    void testHitPlayerWithInputMoreThan360Degrees() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(currentPlayer, opponentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }

        List<Player> playersHit = ShootRule.execute(405, 21, players, currentPlayer, board, new NormalProjectile());

        Assertions.assertEquals(List.of(opponentPlayer), playersHit);
    }

    @Test
    void testHitPlayerWithInputLessThan0Degrees() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(opponentPlayer, currentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }

        List<Player> playersHit = ShootRule.execute(-45, 21, players, currentPlayer, board, new NormalProjectile());

        Assertions.assertEquals(List.of(opponentPlayer), playersHit);
    }

    @Test
    void testMissedPlayer() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(currentPlayer, opponentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }

        List<Player> playersHit = ShootRule.execute(45, 19, players, currentPlayer, board, new NormalProjectile());

        Assertions.assertEquals(new ArrayList<>(), playersHit);
    }

    @Test
    void testHorizontalProjectileHitPlayer() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(currentPlayer, opponentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }

        List<Player> playersHit = ShootRule.execute(45, 16, players, currentPlayer, board, new HorizontalProjectile());

        Assertions.assertEquals(new ArrayList<>(), playersHit);
    }

    @Test
    void testLaunchTooFar() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(currentPlayer, opponentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }
        Assertions.assertDoesNotThrow(() ->
                ShootRule.execute(45, 200, players, currentPlayer, board, new NormalProjectile())
        );
    }
    @Test
    void testLaunchTooHigh() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(currentPlayer, opponentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }
        Assertions.assertDoesNotThrow(() ->
                ShootRule.execute(89, 200, players, currentPlayer, board, new NormalProjectile())
        );
    }

    @Test
    void testLaunch90Degrees() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(currentPlayer, opponentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }
        Assertions.assertDoesNotThrow(() ->
                ShootRule.execute(90, 100, players, currentPlayer, board, new NormalProjectile())
        );
    }
    @Test
    void testLaunch0Degrees() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());
        List<Player> players = List.of(currentPlayer, opponentPlayer);
        BoardIntf board = new Board(100, 20, 1);

        int i = 0;
        for (Player player : players) {
            board.setPlayerPositionOnBoard(player, (board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2, board.getGround() + 1);
            player.setxPosition((board.getWidth()/players.size()) * i + (board.getWidth()/players.size())/2);
            player.setyPosition(board.getGround() + 1);
            i++;
        }
        Assertions.assertDoesNotThrow(() ->
                ShootRule.execute(0, 100, players, currentPlayer, board, new NormalProjectile())
        );
    }
}