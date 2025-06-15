package infrastructure;

import application.services.configservices.AddPlayerService;
import application.services.configservices.GameInfoService;
import application.services.configservices.GameStartService;
import application.services.gameservices.LaunchProjectileService;
import application.services.gameservices.MovePlayerService;
import domain.entities.player.Player;
import infrastructure.events.*;

import java.util.Scanner;

public class CliController {
    private final Scanner scanner;
    private final CliView view;

    private final GameInfoService gameInfoService;
    private final AddPlayerService addPlayerService;
    private final GameStartService gameStartService;
    private final LaunchProjectileService launchProjectileService;
    private final MovePlayerService movePlayerService;

    private boolean appRunning = true;

    public CliController(CliView view, GameInfoService gameInfoService, AddPlayerService addPlayerService, GameStartService gameStartService, LaunchProjectileService launchProjectileService, MovePlayerService movePlayerService, Scanner scanner) {
        this.scanner = scanner;
        this.view = view;
        this.gameInfoService = gameInfoService;
        this.addPlayerService = addPlayerService;
        this.gameStartService = gameStartService;
        this.launchProjectileService = launchProjectileService;
        this.movePlayerService = movePlayerService;
    }

    public void configRun() {
        for (int i = 1; i < 3; i++) {
            while (true) {
                showInstruction("Add Player " + i + " by enter your name and your chosen Tank (" + gameInfoService.getAllTanks() + " ) -> {NAME} {TANK}");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("exit")) {
                    appRunning = false;
                    return;
                }
                if (command.matches("\\w+ \\w+")) {
                    String[] commandSplit = command.split(" ");
                    AddPlayerEvent event = new AddPlayerEvent(commandSplit[0], commandSplit[1]);
                    if (addPlayerService.addPlayer(event)) {
                        showSuccess("Player " + i + " added successfully");
                        break;
                    }
                }
                showException("Player " + i + " could not be added");
            }
        }
        if (gameStartService.start()) {
            gameRun();
        }
    }

    private void gameRun() {
        while (appRunning) {
            view.mapBoardToView(gameInfoService.getBoard());
            if (!gameInfoService.checkGameEnd()) {
                turn(gameInfoService.getCurrentPlayer());
            } else {
                endScreen();
            }
        }
    }

    private void endScreen() {
        view.printView();
        showSuccess("AND THE WINNER IS: " + gameInfoService.getWinner());
        while (true) {
            showInstruction("Type 'exit' to exit the game");
            showInstruction("Type 'restart' to restart the game");
            showInstruction("Type 'new' to create a new game");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("exit")) {
                appRunning = false;
                break;
            } else if (command.equalsIgnoreCase("restart")) {
                gameStartService.start();
                break;
            } else if (command.equalsIgnoreCase("new")) {
                addPlayerService.clearPlayers();
                configRun();
                break;
            }
        }
    }

    public void turn(Player player) {
        view.printView();
        showInstruction(player.getName() + "(ID: " + player.getId() + ")" + ", it's your turn ( Health: " + player.getHealth() + "/" + player.getTank().getMaxHealth() + " ): ");
        showInstruction("Type 'move' if you want to move");
        showInstruction("Type 'launch' if you want to launch");

        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("exit")) {
            appRunning = false;
        }
        if (command.equalsIgnoreCase("move")) {
            if (gameInfoService.currentPlayerCanMove()) {
                move(player);
            } else {
                showException("You have no fuel left in this round");
                turn(player);
            }
        } else if (command.equalsIgnoreCase("launch")) {
            launchProjectile(player);
        } else {
            showException("Something is wrong with your format");
        }
    }

    void move(Player player) {
        System.out.println("ha");
        view.printView();
        showInstruction(player.getName() + ", you want to move (Fuel left: " + player.getFuel() + "/" + player.getTank().getMaxFuel() + ")");
        showInstruction("Type '{{ left/right }} {{ fuel }}' to move");
        showInstruction("Type 'back' to go back");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("exit")) {
            appRunning = false;
            return;
        }
        if (command.equalsIgnoreCase("back")) {
            return;
        }
        else if (!command.matches("(left|right|LEFT|RIGHT) \\d+")) {
            showException("Something is wrong with your format");
        } else {
            MoveEvent event = new MoveEvent(Integer.parseInt(command.split(" ")[1]), DirectionType.fromString(command.split(" ")[0]));
            if (movePlayerService.movePlayer(event)) {
                return;
            } else {
                showException("Invalid Move");
            }
        }
        move(player);
    }

    void launchProjectile(Player player) {
        view.printView();
        showInstruction(player.getName() + ", you want to Launch your projectile");
        showInstruction("Projectiles left:");
        System.out.println(gameInfoService.getCurrentPlayerProjectile());
        showInstruction("Type '{{ angle in degrees }} {{ strength }} {{ projectile }}' to launch");
        showInstruction("Type 'back' to go back");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("exit")) {
            appRunning = false;
            return;
        }
        if (command.equalsIgnoreCase("back")) {
            return;
        }
        else if (!command.matches("-?\\d+ \\d+ \\w+")) {
            showException("Something is wrong with your format");
            launchProjectile(player);
        } else {
            LaunchProjectileEvent event = new LaunchProjectileEvent(Integer.parseInt(command.split(" ")[0]), Integer.parseInt(command.split(" ")[1]), command.split(" ")[2]);
            if (launchProjectileService.launchProjectile(event)) {
                return;
            } else {
                showException("Invalid Launch");
            }
        }
        launchProjectile(player);
    }
    private void showException(String exceptionMessage) {
        System.out.println(" ! " + exceptionMessage + " ! ");
    }
    private void showInstruction(String instruction) {
        System.out.println(" - " + instruction + " - ");
    }
    private void showSuccess(String successMessage) {
        System.out.println(" * " + successMessage + " * ");
    }
}