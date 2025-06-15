package domain.repositories;

import domain.entities.player.Player;

import java.util.List;

public interface PlayerRepositoryIntf {
    List<Player> getPlayers();
    boolean addNewPlayer(Player newPlayer);
    void clearPlayers();
}
