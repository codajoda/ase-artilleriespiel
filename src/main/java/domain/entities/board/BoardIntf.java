package domain.entities.board;

import domain.entities.player.Player;

public interface BoardIntf {
    int getWidth();
    int getHeight();
    int getGround();
    String[][] getMap();
    void clearProjectileTexturesFromBoard();
    void drawProjectile(int x, int y);
    boolean isGroundTexture(int x, int y);
    void setPlayerPositionOnBoard(Player player, int newX, int newY);
    void initMap();
    void drawHitProjectile(int x, int y);
}
