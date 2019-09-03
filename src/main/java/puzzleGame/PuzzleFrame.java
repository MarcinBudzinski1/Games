package puzzleGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class PuzzleFrame extends JFrame{

    private JPanel mainPanel;
    private BufferedImage resized;
    private PuzzleButton freeSpace;
    private java.util.List<PuzzleButton> buttonList;
    private List<Point> solution;
    private final int desiredWidth = 300;

    PuzzleFrame(){
        initial();
    }

    private void initial(){
    solution = new ArrayList<>();
        solution.add(new Point(0,0));
        solution.add(new Point(0,1));
        solution.add(new Point(0,2));
        solution.add(new Point(1,0));
        solution.add(new Point(1,1));
        solution.add(new Point(1,2));
        solution.add(new Point(2,0));
        solution.add(new Point(2,1));
        solution.add(new Point(2,2));
        solution.add(new Point(3,0));
        solution.add(new Point(3,1));
        solution.add(new Point(3,2));

        buttonList = new ArrayList<>();

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        mainPanel.setLayout(new GridLayout(4,3,0,0));

        try{
            BufferedImage source = loadImage();
            int h = getNewHeight(source.getWidth(), source.getHeight());
            resized = resizeImage(source, desiredWidth, h);
        } catch (IOException ex) {
            Logger.getLogger(PuzzleButton.class.getName()).log(Level.SEVERE, null, ex);        }

        int width = resized.getWidth(null);
        int height = resized.getHeight(null);

        add(mainPanel, BorderLayout.CENTER);

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 3; j++) {
                Image imageOfAardelea = createImage(new FilteredImageSource(resized.getSource(),
                        new CropImageFilter(j * width / 3, i * height / 4, width / 3, height / 4)));

            PuzzleButton button = new PuzzleButton(imageOfAardelea);
            button.putClientProperty("position", new Point(i,j));
                if (i == 3 && j == 2) {
                    freeSpace = new PuzzleButton();
                    freeSpace.setBorderPainted(false);
                    freeSpace.setContentAreaFilled(false);
                    freeSpace.setFreeSpace();
                    freeSpace.putClientProperty("position", new Point(i, j));
                } else {
                    buttonList.add(button);
                }
            }
        }
        Collections.shuffle(buttonList);
        buttonList.add(freeSpace);

        int numberOfButtons = 12;
        for (int i = 0; i < numberOfButtons; i++) {

            PuzzleButton btn = buttonList.get(i);
            mainPanel.add(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.gray));
            btn.addActionListener(new ClickAction());
    }
        pack();
        setTitle("Puzzle");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0,0, width,height,null);
        graphics2D.dispose();
        return resizedImage;
    }

    private BufferedImage loadImage() throws IOException {
        return ImageIO.read(new File("src/main/resources/puzzle/randomGirl.jpg"));
    }


    private int getNewHeight(int w, int h) {
        double ratio = desiredWidth / (double) w;
        return (int) (h * ratio);
    }

    private class ClickAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkButton(e);
            checkSolution();
        }

        private void checkSolution() {
            List<Point> checkSolution = new ArrayList<>();
            for (JComponent btn:buttonList){checkSolution.add((Point) btn.getClientProperty("position"));
            }
            if (CompareList(solution, checkSolution)){
                JOptionPane.showMessageDialog(mainPanel, "Wygrałeś", "Brawo", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private boolean CompareList(List<Point> list1, List<Point> list2) {
            return list1.toString().equals(list2.toString());
        }

        private void checkButton(ActionEvent e) {
            int lidx = 0;

            for (PuzzleButton button : buttonList) {
                if (button.isFreeSpace()) {
                    lidx = buttonList.indexOf(button);
                }
            }

            JButton button = (JButton) e.getSource();
            int bidx = buttonList.indexOf(button);

            if ((bidx - 1 == lidx) || (bidx + 1 == lidx)
                    || (bidx - 3 == lidx) || (bidx + 3 == lidx)) {
                Collections.swap(buttonList, bidx, lidx);
                updateButtons();
            }
        }

        private void updateButtons() {
            mainPanel.removeAll();

            for (JComponent btn : buttonList) {

                mainPanel.add(btn);
            }

            mainPanel.validate();
        }
    }
}