package domain.rules;

import domain.entities.board.BoardIntf;
import domain.entities.player.Player;
import domain.entities.projectiles.Projectile;
import domain.valueobjects.MyPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectileRule {
    public static List<Player> execute(int angleDegrees, int strength, List<Player> players, Player currentPlayer, BoardIntf board, Projectile projectile) {
        List<MyPoint> trajectory = new ArrayList<>();
        MyPoint lastPoint = null;

        double angleRadians = Math.toRadians(Math.abs(angleDegrees));
        double g = 9.8;
        double timeStep = 0.1;
        double t = 0;
        int anchor = 0;
        if (angleDegrees != 0) {
            anchor = (angleDegrees < 0 ? -1 : 1) * (currentPlayer.getTank().getWidth() / 2);
        }
        do {
            int x = currentPlayer.getxPosition() + anchor + (int) (strength * (angleDegrees < 0 ? -1 : 1) * Math.cos(angleRadians) * t);
            int y = currentPlayer.getyPosition() + currentPlayer.getTank().getHeight() + (int) (strength * Math.sin(angleRadians) * t - 0.5 * g * t * t);
            if (y < board.getHeight()) {
                if (x < 0 || x >= board.getWidth() || y <= board.getGround()) {
                    break;
                }
                if (!board.isGroundTexture(x, y) && !players.stream().filter(player -> !player.equals(currentPlayer)).map(player -> Integer.toString(player.getId())).toList().contains(board.getMap()[x][y])) {
                    lastPoint = new MyPoint(x, y);
                    trajectory.add(new MyPoint(x, y));
                } else {
                    if (players.stream().filter(player -> !player.equals(currentPlayer)).map(player -> Integer.toString(player.getId())).toList().contains(board.getMap()[x][y])) {
                        lastPoint = new MyPoint(x, y);
                    }
                    break;
                }
            }
            t += timeStep;
        } while (true);
        List<String> playersHit = projectile.fire(board.getMap(), lastPoint);
        for (MyPoint p : trajectory) {
            if (!p.equals(trajectory.get(trajectory.size() - 1))) {
                board.drawProjectile(p.x, p.y);
            }
        }
        List<MyPoint> pattern = projectile.getPattern(lastPoint);
        for (MyPoint p : pattern) {
            if (p.x > 0 && p.x < board.getWidth() && p.y > 0 && p.y < board.getHeight()) {
                board.drawHitProjectile(p.x, p.y);
            }
        }
        return players.stream().filter(player -> playersHit.contains(Integer.toString(player.getId()))).collect(Collectors.toList());
    }
}