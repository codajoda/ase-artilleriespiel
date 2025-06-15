package infrastructure;

public class CliView {

    private final String[][] view;
    private final int width, height;
    private final String borderTexture = "-";

    public CliView(int width, int height) {
        this.width = width;
        this.height = height;
        view = new String[this.width + 2][this.height + 2];
        initView();
    }

    public void initView() {
        for (int x = 0; x < width + 2; x++) {
            for (int y = 0; y < height + 2; y++) {
                if (x == 0 || y == 0 || x == width + 1 || y == height + 1) {
                    view[x][y] = borderTexture;
                } else {
                    view[x][y] = borderTexture;
                }
            }
        }
    }

    public void printView() {
        for (int y = view[0].length - 1; y >= 0; y--) {
            for (String[] lines : view) {
                System.out.print(lines[y]);
            }
            System.out.println();
        }
    }

    public void mapBoardToView(String[][] map) {
        for (int x = 1; x < width + 1; x++) {
            if (height + 1 - 1 >= 0) System.arraycopy(map[x - 1], 0, view[x], 1, height + 1 - 1);
        }
    }
}