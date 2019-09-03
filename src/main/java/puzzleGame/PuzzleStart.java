package puzzleGame;

import java.awt.*;

public class PuzzleStart {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                PuzzleFrame puzzleFrame = new PuzzleFrame();
            puzzleFrame.setVisible(true);
        });
    }
}
