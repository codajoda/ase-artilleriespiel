package domain.entities.board;

import domain.entities.player.Player;

import java.util.List;

public class Board implements BoardIntf {

    private final String[][] map;
    private final int width, height, ground;
    private final String groundTexture = "G";
    private final String projectileTexture = "o";
    private final String hitProjectileTexture = "x";


    public Board(int width, int height, int ground) {
        this.width = width;
        this.height = height;
        this.ground = ground;
        this.map = new String[width][height];
        this.initMap();
    }

    public void initMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y <= ground) {
                    map[x][y] = groundTexture;
                } else {
                    map[x][y] = " ";
                }
            }
        }
    }

    public void setPlayerPositionOnBoard(Player player, int newX, int newY) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (map[x][y].equals(Integer.toString(player.getId()))) {
                    map[x][y] = " ";
                }
            }
        }
        for (int x = newX - (player.getTank().getWidth()-1)/2; x <= newX + (player.getTank().getWidth()-1)/2; x++) {
            for (int y = newY; y < newY + player.getTank().getHeight(); y++) {
                //if (x >= 0 && x < width && y >= 0 && y < height) {
                    //if (!map[x][y].equals(groundTexture)) {
                        map[x][y] = Integer.toString(player.getId());
                    //}
                //}
            }
        }
    }

    @Override
    public void drawProjectile(int x, int y) {
        map[x][y] = projectileTexture;
    }

    @Override
    public void drawHitProjectile(int x, int y) {
        map[x][y] = hitProjectileTexture;
    }

    public void clearProjectileTexturesFromBoard() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (map[x][y].equals(projectileTexture) || map[x][y].equals(hitProjectileTexture)) {
                    map[x][y] = " ";
                }
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y <= ground; y++) {
                map[x][y] = groundTexture;
            }
        }
    }

    public boolean isGroundTexture(int x, int y) {
        return map[x][y].equals(groundTexture);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGround() {
        return ground;
    }

    public String[][] getMap() {
        return map;
    }
}