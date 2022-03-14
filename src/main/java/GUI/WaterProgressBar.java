package GUI;

import javax.swing.*;
import java.awt.*;

public class WaterProgressBar extends JPanel {

    JProgressBar pbar;

    static final int MINIMUM = 0;

    static int MAXIMUM = 2000;

    public WaterProgressBar() {


        // initialize Progress Bar
        pbar = new JProgressBar();
        pbar.setMinimum(MINIMUM);
        pbar.setMaximum(MAXIMUM);
        pbar.setStringPainted(true);


        // add to JPanel

        add(pbar);

    }

    public void updateBarValue(int newValue) {
        pbar.setValue(newValue);
    }

    public void updateBarString(String newString) {
        pbar.setString(newString);
    }

    public void setPreferredSize(Dimension newDimension) {
        pbar.setPreferredSize(newDimension);
    }

    public void setMaximum(int newMax) {
        MAXIMUM = newMax;
        pbar.setMaximum(MAXIMUM);
    }

    public static void main(String args[]) {
        final WaterProgressBar it = new WaterProgressBar();

        JFrame frame = new JFrame("Progress Bar Example");
        JPanel panel = new JPanel();
        //panel.setSize(new Dimension(300, 300));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 300);

        frame.add(panel);
        panel.add(it);
        it.setPreferredSize(new Dimension(200, 20));

        frame.pack();
        frame.setVisible(true);

        // run a loop to demonstrate raising
        for (int i = MINIMUM; i <= MAXIMUM; i++) {
            final int percent = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        it.updateBarValue(percent);
                    }
                });
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
                ;
            }
        }
    }
}
