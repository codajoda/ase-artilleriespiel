import application.services.configservices.AddPlayerService;
import application.services.configservices.GameInfoService;
import application.services.configservices.GameStartService;
import application.services.gameservices.LaunchProjectileService;
import application.services.gameservices.MovePlayerService;
import domain.entities.board.Board;
import domain.entities.board.BoardIntf;
import domain.aggregates.game.Game;
import domain.aggregates.game.GameIntf;
import infrastructure.repositories.InMemoryPlayerRepository;
import domain.repositories.PlayerRepositoryIntf;
import infrastructure.CliController;
import infrastructure.CliView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Domain-Layer
        PlayerRepositoryIntf playerRepository = new InMemoryPlayerRepository();
        BoardIntf board = new Board(100, 20, 1);
        GameIntf game = new Game(board);

        //Application-Layer
        GameInfoService gameInfoService = new GameInfoService(game);
        AddPlayerService addPlayerService = new AddPlayerService(playerRepository);
        GameStartService gameStartService = new GameStartService(playerRepository, game);
        LaunchProjectileService launchProjectileService = new LaunchProjectileService(game);
        MovePlayerService movePlayerService = new MovePlayerService(game);

        //Infrastructure-Layer
        CliView view = new CliView(100, 20);
        CliController controller = new CliController(view, gameInfoService, addPlayerService, gameStartService, launchProjectileService, movePlayerService, new Scanner(System.in));

        controller.configRun();
    }
}
