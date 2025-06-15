package infrastructure;

import application.services.configservices.AddPlayerService;
import application.services.configservices.GameInfoService;
import application.services.configservices.GameStartService;
import application.services.gameservices.LaunchProjectileService;
import application.services.gameservices.MovePlayerService;
import domain.entities.board.Board;
import domain.entities.board.BoardIntf;
import domain.entities.player.Player;
import domain.entities.tanks.SmallTank;
import domain.entities.tanks.TankType;
import domain.aggregates.game.Game;
import domain.aggregates.game.GameIntf;
import infrastructure.repositories.InMemoryPlayerRepository;
import domain.repositories.PlayerRepositoryIntf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CliControllerTest {
    @Mock
    CliView view;

    MovePlayerService movePlayerService;
    LaunchProjectileService launchProjectileService;
    BoardIntf board;
    GameInfoService gameInfoService;
    AddPlayerService addPlayerService;
    GameStartService gameStartService;
    PlayerRepositoryIntf playerRepository;
    GameIntf game;

    CliController controller;

    @BeforeEach
    void setUp() {
        board = new Board(100, 20, 1);
        game = Mockito.spy(new Game(board));
        launchProjectileService = new LaunchProjectileService(game);
        movePlayerService = new MovePlayerService(game);
        playerRepository = new InMemoryPlayerRepository();
        gameStartService = new GameStartService(playerRepository, game);
        addPlayerService = new AddPlayerService(playerRepository);
        gameInfoService = Mockito.spy(new GameInfoService(game));
    }

    @Test
    void testFirstIterationMediumConfigRun() {
        String simulatedInput = "Alice Medium\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);

        controller.configRun();
        Assertions.assertEquals("Alice", playerRepository.getPlayers().get(0).getName());
        Assertions.assertEquals(TankType.fromString("Medium"), playerRepository.getPlayers().get(0).getTank().getTankType());
    }
    @Test
    void testFirstIterationBigConfigRun() {
        String simulatedInput = "Alice Big\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);

        controller.configRun();
        Assertions.assertEquals("Alice", playerRepository.getPlayers().get(0).getName());
        Assertions.assertEquals(TankType.fromString("Big"), playerRepository.getPlayers().get(0).getTank().getTankType());
    }
    @Test
    void testFirstIterationSmallConfigRun() {
        String simulatedInput = "Alice Small\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);

        controller.configRun();
        Assertions.assertEquals("Alice", playerRepository.getPlayers().get(0).getName());
        Assertions.assertEquals(TankType.fromString("Small"), playerRepository.getPlayers().get(0).getTank().getTankType());
    }
    @Test
    void testInvalidWrongInputFormatConfigRun() {
        String simulatedInput = "Alice error Small\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);

        controller.configRun();
        assertTrue(playerRepository.getPlayers().isEmpty());
    }
    @Test
    void testInvalidNoExistingTankFormatConfigRun() {
        String simulatedInput = "Alice ImaginableTank\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);

        controller.configRun();
        assertTrue(playerRepository.getPlayers().isEmpty());
    }
    @Test
    void testInvalidSameNameConfigRun() {
        String simulatedInput = "Alice Small\nAlice Big\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);

        controller.configRun();
        assertEquals(1, playerRepository.getPlayers().size());
    }
    @Test
    void testTurnMove() {
        Mockito.doReturn(true).when(gameInfoService).currentPlayerCanMove();
        Player player = new Player("Player 1", new SmallTank());
        String simulatedInput = "move\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = Mockito.spy(new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner));

        controller.turn(player);
        verify(controller, times(1)).move(player);
        verify(controller, never()).launchProjectile(player);
    }
    @Test
    void testInvalidTurnMove() {
        String simulatedInput = "Alice Small\nBob Small\nmove\nright 20\nmove\nright 20\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = Mockito.spy(new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner));
        controller.configRun();

        verify(controller, times(1)).move(gameInfoService.getCurrentPlayer());
    }
    @Test
    void testTurnLaunchProjectile() {
        Mockito.doReturn("TEST").when(gameInfoService).getCurrentPlayerProjectile();
        Player player = new Player("Player 1", new SmallTank());
        String simulatedInput = "launch\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = Mockito.spy(new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner));

        controller.turn(player);
        verify(controller, times(1)).launchProjectile(player);
        verify(controller, never()).move(player);
    }
    @Test
    void testLaunchProjectile() {
        String simulatedInput = "Alice Big\nBob Big\nlaunch\n45 21 Normal\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);
        controller.configRun();
        assertThat(playerRepository.getPlayers().get(1).getTank().getMaxHealth(), greaterThan(playerRepository.getPlayers().get(1).getHealth()));
    }
    @Test
    void testInvalidNoExisistingProjectileLaunchProjectile() {
        String simulatedInput = "Alice Big\nBob Big\nlaunch\n45 21 ImaginableProjectile\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);
        controller.configRun();
        assertEquals(playerRepository.getPlayers().get(1).getTank().getMaxHealth(), playerRepository.getPlayers().get(1).getHealth());
    }

    @Test
    void testMove() {
        String simulatedInput = "Alice Big\nBob Big\nmove\nRIGHT 3\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);
        controller.configRun();
    }

    @Test
    void testWinner() {
        String simulatedInput = "Alice Small\nBob Small\nlaunch\n45 21 normal\nlaunch\n45 21 normal\nlaunch\n45 21 normal\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);
        controller.configRun();
        assertEquals("Alice", gameInfoService.getWinner());
    }

    @Test
    void testRestartGame() {
        String simulatedInput = "Alice Small\nBob Small\nlaunch\n45 21 normal\nlaunch\n45 21 normal\nlaunch\n45 21 normal\nrestart\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);
        controller.configRun();
        assertNull(game.getWinner());
    }
    @Test
    void testNewGame() {
        String simulatedInput = "Alice Small\nBob Small\nlaunch\n45 21 normal\nlaunch\n45 21 normal\nlaunch\n45 21 normal\nnew\nexit";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))
        );
        controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, scanner);
        controller.configRun();
        assertEquals(0, playerRepository.getPlayers().size());
    }
}