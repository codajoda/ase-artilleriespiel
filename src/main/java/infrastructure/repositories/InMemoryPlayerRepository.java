package infrastructure.repositories;

import domain.entities.player.Player;
import domain.repositories.PlayerRepositoryIntf;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPlayerRepository implements PlayerRepositoryIntf {

    private final List<Player> players;

    public InMemoryPlayerRepository() {
        players = new ArrayList<>();
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public boolean addNewPlayer(Player newPlayer) {
        if (players.stream().anyMatch(player -> player.getName().equals(newPlayer.getName()))) {
            return false;
        }
        players.add(newPlayer);
        return true;
    }

    @Override
    public void clearPlayers() {
        players.clear();
    }
}
