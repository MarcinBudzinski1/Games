package puzzleGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PuzzleButton extends JButton {

    private Boolean isFreeSpace;

    public PuzzleButton(){
        super();
        initial();

    }

    public PuzzleButton(Image imageOfAardelea) {
        super(new ImageIcon(imageOfAardelea));
        initial();
    }

    private void initial(){
        isFreeSpace = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.RED));
            }

            @Override
            public void mouseExited(MouseEvent e) {setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));}
        });
    }
    void setFreeSpace(){
        isFreeSpace=true;
    }
    Boolean isFreeSpace(){
        return isFreeSpace;
    }
}
