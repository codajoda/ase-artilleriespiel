package domain.rules;

import domain.entities.board.BoardIntf;
import domain.entities.player.Player;
import domain.entities.tanks.SmallTank;
import infrastructure.events.DirectionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MoveRuleTest {

    @Test
    void testIfPlayerCanMoveRightWithoutLimitations() {
        BoardIntf board = mock(BoardIntf.class);
        when(board.getWidth()).thenReturn(100);
        when(board.getHeight()).thenReturn(20);

        Player currentPlayer = new Player("Player 1", new SmallTank());
        currentPlayer.setxPosition(50);
        int oldPosition = currentPlayer.getxPosition();
        MoveRule.execute(1, DirectionType.RIGHT, currentPlayer, board);

        Assertions.assertEquals(oldPosition + 1, currentPlayer.getxPosition());
    }

    @Test
    void testIfPlayerCanMoveLeftWithoutLimitations() {
        BoardIntf board = mock(BoardIntf.class);
        when(board.getWidth()).thenReturn(100);
        when(board.getHeight()).thenReturn(20);

        Player currentPlayer = new Player("Player 1", new SmallTank());
        currentPlayer.setxPosition(50);
        int oldPosition = currentPlayer.getxPosition();
        MoveRule.execute(1, DirectionType.LEFT, currentPlayer, board);

        Assertions.assertEquals(oldPosition - 1, currentPlayer.getxPosition());
    }

    @Test
    void testIfPlayerCanMoveLeftToEdge() {
        BoardIntf board = mock(BoardIntf.class);
        when(board.getWidth()).thenReturn(100);
        when(board.getHeight()).thenReturn(20);
        String[][] pseudoMap = new String[100][20];
        for (int x = 0; x < 100; x++) {
            Arrays.fill(pseudoMap[x], "");
        }
        when(board.getMap()).thenReturn(pseudoMap);

        Player currentPlayer = new Player("Player 1", new SmallTank());
        currentPlayer.setxPosition(10);
        MoveRule.execute(10, DirectionType.LEFT, currentPlayer, board);

        Assertions.assertEquals(0, currentPlayer.getxPosition());
    }

    @Test
    void testIfPlayerCanMoveRightToEdge() {
        BoardIntf board = mock(BoardIntf.class);
        when(board.getWidth()).thenReturn(100);
        when(board.getHeight()).thenReturn(20);
        String[][] pseudoMap = new String[100][20];
        for (int x = 0; x < 100; x++) {
            Arrays.fill(pseudoMap[x], "");
        }
        when(board.getMap()).thenReturn(pseudoMap);

        Player currentPlayer = new Player("Player 1", new SmallTank());
        currentPlayer.setxPosition(90);
        MoveRule.execute(10, DirectionType.RIGHT, currentPlayer, board);

        Assertions.assertEquals(board.getWidth() - 1, currentPlayer.getxPosition());
    }

    @Test
    void testIfPlayerCanMoveLefterToEdge() {
        BoardIntf board = mock(BoardIntf.class);
        when(board.getWidth()).thenReturn(100);
        when(board.getHeight()).thenReturn(20);
        String[][] pseudoMap = new String[100][20];
        for (int x = 0; x < 100; x++) {
            Arrays.fill(pseudoMap[x], "");
        }
        when(board.getMap()).thenReturn(pseudoMap);

        Player currentPlayer = new Player("Player 1", new SmallTank());
        currentPlayer.setxPosition(10);
        MoveRule.execute(11, DirectionType.LEFT, currentPlayer, board);

        Assertions.assertEquals(0, currentPlayer.getxPosition());
    }

    @Test
    void testIfPlayerCanMoveRighterToEdge() {
        BoardIntf board = mock(BoardIntf.class);
        when(board.getWidth()).thenReturn(100);
        when(board.getHeight()).thenReturn(20);
        String[][] pseudoMap = new String[100][20];
        for (int x = 0; x < 100; x++) {
            Arrays.fill(pseudoMap[x], "");
        }
        when(board.getMap()).thenReturn(pseudoMap);

        Player currentPlayer = new Player("Player 1", new SmallTank());
        currentPlayer.setxPosition(90);
        MoveRule.execute(11, DirectionType.RIGHT, currentPlayer, board);

        Assertions.assertEquals(board.getWidth() - 1, currentPlayer.getxPosition());
    }

    @Test
    void testIfPlayerCanMoveWithInvalidFuel() {
        BoardIntf board = mock(BoardIntf.class);
        Player currentPlayer = new Player("Player 1", new SmallTank());
        int oldPosition = currentPlayer.getxPosition();
        MoveRule.execute(currentPlayer.getTank().getMaxFuel() + 1, DirectionType.RIGHT, currentPlayer, board);

        Assertions.assertEquals(oldPosition, currentPlayer.getxPosition());
    }

    @Test
    void testIfPlayerCanMoveWith0Input() {
        BoardIntf board = mock(BoardIntf.class);
        Player currentPlayer = new Player("Player 1", new SmallTank());
        int oldPosition = currentPlayer.getxPosition();
        MoveRule.execute(0, DirectionType.RIGHT, currentPlayer, board);

        Assertions.assertEquals(oldPosition, currentPlayer.getxPosition());
    }
}