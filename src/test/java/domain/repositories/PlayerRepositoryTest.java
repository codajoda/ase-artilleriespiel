package domain.repositories;

import domain.entities.player.Player;
import domain.entities.tanks.SmallTank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import infrastructure.repositories.InMemoryPlayerRepository;

import static org.junit.jupiter.api.Assertions.*;

class PlayerRepositoryTest {

    PlayerRepositoryIntf playerRepository;

    @BeforeEach
    void setUp() {
        playerRepository = new InMemoryPlayerRepository();
        playerRepository.addNewPlayer(new Player("Player 1", new SmallTank()));
    }

    @Test
    void testAddValidPlayer() {
        playerRepository.addNewPlayer(new Player("Player 2", new SmallTank()));
        assertEquals(2, playerRepository.getPlayers().size());
    }
    @Test
    void testAddDuplicateNamePlayer() {
        playerRepository.addNewPlayer(new Player("Player 1", new SmallTank()));
        assertEquals(1, playerRepository.getPlayers().size());
    }
}