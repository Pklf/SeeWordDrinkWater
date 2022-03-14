package SWDW;

import GUI.MainPageGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch {
    private static int interval = 0;
    static Timer timer = new Timer();
    static Timer beepTimer = new Timer();
    private JLabel label;
    private JButton button;
    private JFrame frame;
    private MainPageGUI mainPage;

    private int delay = 0;
    private int period = 1000;

    private int beepPeriod = 5000;

    int totalSecsTempStor = interval;

    String timeString = totalSecsToString(totalSecsTempStor);

    String[] status = {"Not Yet Start Counting: ", "Next Time to Drink: "};

    Boolean whiteListQuite = true;
    Boolean allQuite = false;
    Boolean loop = false;

    public void setWhiteListQuite(Boolean status) {
        whiteListQuite = status;
        //System.out.println(whiteListQuite);
    }

    public void setAllQuite(Boolean status) {
        allQuite = status;
        //System.out.println(allQuite);
    }

    public int getBeepPeriod() {
        return beepPeriod;
    }

    public void setBeepPeriod(int beepPeriod) {
        this.beepPeriod = beepPeriod;
    }


    public void setLoop(Boolean state) {
        loop = state;
    }

    public Stopwatch(JLabel label, JButton button, JFrame frame, MainPageGUI mainPage) {
        this.label = label;
        this.button = button;
        this.frame = frame;
        this.mainPage = mainPage;
    }

    public Stopwatch() {
    }

    public String totalSecsToString(int sec) {
        int hours = sec / 3600;
        int minutes = (sec % 3600) / 60;
        int seconds = sec % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void startStopwatch() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                int totalSecs = setInterval();
                int hours = totalSecs / 3600;
                int minutes = (totalSecs % 3600) / 60;
                int seconds = totalSecs % 60;
                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                if (interval > 0) {
                    label.setText(status[1] + timeString);
                } else {
                    hours = totalSecsTempStor / 3600;
                    minutes = (totalSecsTempStor % 3600) / 60;
                    seconds = totalSecsTempStor % 60;
                    timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    label.setText(status[0] + timeString);
                }
            }
        }, delay, period);
    }

    public void pause() {
        timer.cancel();
        timer.purge();
        timeString = totalSecsToString(interval);

        label.setText(status[0] + timeString);
        //label.setForeground(Color.RED);
    }

    public void reset() {
        timer.cancel();
        timer.purge();
        interval = totalSecsTempStor;
        timeString = totalSecsToString(interval);
        label.setText(status[0] + timeString);
    }

    public void resume() {
        timer = new Timer();
        startStopwatch();
        //label.setForeground(Color.BLACK);
    }

    static class Beeping extends TimerTask {
        public void run() {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private int setInterval() {
        if (interval == 1) {
            timer.cancel();
            timer.purge();
            button.setText("Start");
            label.setForeground(Color.RED);
            label.setText(status[0] + timeString);

            if ((mainPage.getWhiteListMain() || !whiteListQuite) && !allQuite) {
                if (loop) {
                    beepTimer = new Timer();
                    beepTimer.scheduleAtFixedRate(new Beeping(), 0, beepPeriod);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            if ((mainPage.getWhiteListMain() || !whiteListQuite) && !allQuite) {
                final JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                int result = JOptionPane.showConfirmDialog(dialog, "WATER!", "Get It!", JOptionPane.DEFAULT_OPTION);
                if (result == JOptionPane.YES_OPTION || result == JOptionPane.CLOSED_OPTION) {
                    if (loop) {
                        beepTimer.cancel();
                        beepTimer.purge();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "WATER!");

            }

        } else if (interval == 0) {
            interval = totalSecsTempStor + 1;
            label.setForeground(Color.BLACK);
        }
        return --interval;
    }

    public void setIntervalValue(int newInterval) {
        interval = newInterval;
        totalSecsTempStor = interval;

        timeString = totalSecsToString(interval);

        if (button.getText().equals("Start") || button.getText().equals("Resume")) {
            label.setText(status[0] + timeString);
        } else {
            label.setText(status[1] + timeString);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Input seconds => : ");
        String secs = sc.nextLine();
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.setIntervalValue(Integer.parseInt(secs));
        stopwatch.startStopwatch();

        System.out.println(secs);

    }


}
