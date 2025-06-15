package application.services.configservices;

import domain.entities.player.Player;
import domain.entities.tanks.TankType;
import domain.repositories.PlayerRepositoryIntf;
import infrastructure.events.AddPlayerEvent;

public class AddPlayerService {

    private final PlayerRepositoryIntf playerRepository;

    public AddPlayerService(PlayerRepositoryIntf playerRepository) {
        this.playerRepository = playerRepository;
    }

    public boolean addPlayer(AddPlayerEvent event) {
        try {
            TankType tt = TankType.fromString(event.tank());
            return playerRepository.addNewPlayer(new Player(event.name(), tt.createTank()));
        }
        catch (Exception e) {
            return false;
        }
    }

    public void clearPlayers() {
        playerRepository.clearPlayers();
    }
}
