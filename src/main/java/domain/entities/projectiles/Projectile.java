package domain.entities.projectiles;

import domain.valueobjects.MyPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public interface Projectile {
    Pattern INT_PATTERN = Pattern.compile("-?\\d+");
    default List<String> fire(String[][] board, MyPoint p) {
        List<String> result = new ArrayList<>();
        for (MyPoint z : getPattern(p)) {
            if (z.x >= 0 && z.x < board.length && z.y >= 0 && z.y < board[0].length) {
                if (INT_PATTERN.matcher(board[z.x][z.y]).matches()) {
                    result.add(board[z.x][z.y]);
                }
            }
        }
        return result;
    }
    List<MyPoint> getPattern(MyPoint p);
    ProjectileType getProjectileType();
    int getDamage();
}

