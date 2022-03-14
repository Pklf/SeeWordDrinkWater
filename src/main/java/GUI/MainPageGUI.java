package GUI;

import GUI.Util.FilteredField;
import SWDW.DataJson;
import SWDW.Stopwatch;
import SWDW.TimeLeft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Timer;

public class MainPageGUI extends JFrame implements ActionListener, ItemListener, KeyListener {

    //----------------- Declare DataJson (DataJson) -----------------//
    public DataJson dataJson = new DataJson();
    public static LocalDate today = LocalDate.now();

    //----------------- Declare Panel (waterStatusPanel, inputDrankPanel, timePanel, quickAddPanel) -----------------//
    private JPanel waterStatusPanel = new JPanel();
    private JPanel inputDrankPanel = new JPanel();
    private JPanel timePanel = new JPanel();
    private JPanel quickAddPanel = new JPanel();

    //----------------- Declare Label (waterStatusLabel, inputDrankLabel, timeLabel, quickAddLabel, timeLeftLabel, targetLeftLabel) -----------------//
    private JLabel waterStatusLabel = new JLabel("Target Progress: ");
    private JLabel inputDrankLabel = new JLabel("Add Water Amount Drank: ");
    private JLabel timeLabel = new JLabel("Not Yet Start Counting ");
    private JLabel quickAddLabel = new JLabel("Add Amount Quickly : ");
    private JLabel timeLeftLabel = new JLabel("Today time left: HH:MM:SS");
    private JLabel targetLeftLabel = new JLabel("Target left: 2000 ml");

    //----------------- Declare TextField (inputDrankText) -----------------//
    //private JTextField inputDrankText = new JTextField(10);
    private FilteredField filterTextField = new FilteredField();
    String[] filters = {"^[A-Za-z][A-Za-z0-9_ ]*$", "^[1-9][0-9]*$"};
    private JTextField inputDrankText = filterTextField.createFilteredField(filters[1]);

    //----------------- Declare Button (drankButton, stopwatchStartButton, resetButton, add50, add100, add200, add500) -----------------//
    private JButton drankButton = new JButton("Submit");
    private JButton stopwatchStartButton = new JButton("Start");
    private JButton resetButton = new JButton("Reset");

    private JButton add50 = new JButton("+ 50 ml");
    private JButton add100 = new JButton("+ 100 ml");
    private JButton add200 = new JButton("+ 200 ml");
    private JButton add500 = new JButton("+ 500 ml");

    //----------------- Declare Progress Bar (waterProgressBar) -----------------//
    private WaterProgressBar waterProgressBar = new WaterProgressBar();

    //----------------- Declare Menu Bar (menuChangeMaxValue, menuChangeNextTimeDrink, menuReduceWaterAmount, menuChangeNextTimeDrink, menuReduceWaterAmount) -----------------//
    private JMenuBar menuBar = new JMenuBar();

    //========== optionMenu ==========//
    private JMenu optionMenu = new JMenu("Option");
    private JMenuItem menuChangeMaxValue = new JMenuItem("Change Target Value");
    private JMenuItem menuChangeNextTimeDrink = new JMenuItem("Change Time to Drink Next");
    private JMenuItem menuReduceWaterAmount = new JMenuItem("Reduce Water Amount");

    //========== taskManagerMenu ==========//
    private JMenu taskManagerMenu = new JMenu("Task Manager");
    private JMenuItem menuWhiteList = new JMenuItem("White List");

    //========== taskManagerMenu ==========//
    private JMenu soundMenu = new JMenu("Sound Setting");
    private JMenuItem beepInterval = new JMenuItem("Beep Interval");
    private JCheckBoxMenuItem beepLoop = new JCheckBoxMenuItem("Non-Stop Beeping");

    //========== calendarMenu ==========//
    private JMenu calendarMenu = new JMenu("Calendar");
    private JMenuItem calendarMenuItem = new JMenuItem("Open Calendar");


    //----------------- Declare TaskManagerGUI (TaskManagerGUI) -----------------//
    private TaskManagerGUI taskManagerGUI = new TaskManagerGUI(this);

    //----------------- Declare CalendarGUI (CalendarGUI) -----------------//
    private CalendarGUI calendarGUI = new CalendarGUI(this);

    //----------------- Declare Variable (waterAmount) -----------------//
    private int waterAmount = 0;

    public int getWaterAmount() {
        return waterAmount;
    }

    //----------------- Declare Variable (whiteListMain) -----------------//
    private boolean whiteListMain = true;

    public void setWhiteListMain(boolean newWhiteList) {
        whiteListMain = newWhiteList;
    }

    public boolean getWhiteListMain() {
        return whiteListMain;
    }

    public void checkWhiteListMain() {
        taskManagerGUI.checkWhiteListTaskGUI(this);
    }

    //----------------- Declare Stopwatch (Stopwatch) -----------------//
    private Stopwatch stopwatch = new Stopwatch(timeLabel, stopwatchStartButton, this, this);

    //----------------- Declare ChangeMaxValueGUI (ChangeMaxValueGUI) -----------------//
    private ChangeMaxValueGUI changeMaxValueGUI = new ChangeMaxValueGUI(stopwatch, this);

    //----------------- Declare SystemTray -----------------//
    final SystemTray tray = SystemTray.getSystemTray();
    final TrayIcon trayIcon = new TrayIcon(createImage("icon/water_icon_tray.png", "tray icon"));

    protected static Image createImage(String path, String description) {//Obtain the image URL
        URL imageURL = MainPageGUI.class.getClassLoader().getResource(path);
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

    //----------------- Declare Pop-Up Menu -----------------//
    final PopupMenu popup = new PopupMenu();
    private MenuItem aboutItem = new MenuItem("About");
    private CheckboxMenuItem whiteListQuite = new CheckboxMenuItem("Only White List Quite", true);
    private CheckboxMenuItem allQuite = new CheckboxMenuItem("All Quite");
    private MenuItem openItem = new MenuItem("Open");
    private MenuItem exitItem = new MenuItem("Exit");

    public MainPageGUI() {
        super("見字飲水 See Word Drink Water ");

        System.out.println(today);

        //----------------- Check the SystemTray support -----------------//
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        popup.add(aboutItem); //Add components to popup menu
        popup.addSeparator();
        popup.add(whiteListQuite);
        popup.add(allQuite);
        popup.addSeparator();
        popup.add(openItem);
        popup.add(exitItem);
        trayIcon.setPopupMenu(popup);
        trayIcon.setToolTip("見字飲水");

        //----------------- Declare Frame Layout [BorderLayout] -----------------//
        setLayout(new GridBagLayout());

        //----------------- Declare Panel Layout (waterStatusPanel,inputDrankPanel, timePanel, quickAddPanel) [FlowLayout] -----------------//
        waterStatusPanel.setLayout(new FlowLayout());
        inputDrankPanel.setLayout(new FlowLayout());
        timePanel.setLayout(new FlowLayout());
        quickAddPanel.setLayout(new FlowLayout());

        //----------------- Water Panel elements  -----------------//
        waterStatusPanel.add(waterStatusLabel);
        waterStatusPanel.add(waterProgressBar);

        //----------------- Input Panel elements  -----------------//
        inputDrankPanel.add(inputDrankLabel);
        inputDrankPanel.add(inputDrankText);
        inputDrankPanel.add(drankButton);

        //----------------- Time Panel elements  -----------------//
        timePanel.add(timeLabel);
        timePanel.add(stopwatchStartButton);
        timePanel.add(resetButton);
        stopwatch.setIntervalValue(2700);
        timeLabel.setForeground(Color.RED);

        //----------------- Quick Add Panel elements  -----------------//
        quickAddPanel.add(quickAddLabel);
        quickAddPanel.add(add50);
        quickAddPanel.add(add100);
        quickAddPanel.add(add200);
        quickAddPanel.add(add500);

        //----------------- Set Font Properties -----------------//
        inputDrankLabel.setFont(new Font("Verdana ", Font.PLAIN, 16));
        waterStatusLabel.setFont(new Font("Verdana ", Font.PLAIN, 16));
        timeLabel.setFont(new Font("Verdana ", Font.PLAIN, 16));
        quickAddLabel.setFont(new Font("Verdana ", Font.PLAIN, 16));
        timeLeftLabel.setFont(new Font("Verdana ", Font.PLAIN, 13));
        targetLeftLabel.setFont(new Font("Verdana ", Font.PLAIN, 13));

        //----------------- Time Left Label (Start Count Down)  -----------------//
        Timer DayLeftTimer = new Timer();
        DayLeftTimer.schedule(new TimeLeft(timeLeftLabel, this), 0, 1000);

        //----------------- Progress Bar Properties  -----------------//
        waterProgressBar.setPreferredSize(new Dimension(250, 23));
        waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
        int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
        if (targetLeft < 0) {
            targetLeft = Math.abs(targetLeft);
            targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
        } else {
            targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
        }
        //----------------- Menu Bar elements  -----------------//
        setJMenuBar(menuBar);
        menuBar.add(optionMenu);
        optionMenu.add(menuChangeMaxValue);
        optionMenu.add(menuChangeNextTimeDrink);
        optionMenu.add(menuReduceWaterAmount);

        menuBar.add(taskManagerMenu);
        taskManagerMenu.add(menuWhiteList);

        menuBar.add(soundMenu);
        soundMenu.add(beepInterval);
        soundMenu.add(beepLoop);

        menuBar.add(calendarMenu);
        calendarMenu.add(calendarMenuItem);


        //----------------- Main Frame elements  -----------------//
        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.gridwidth = 2;
        c0.gridheight = 1;
        c0.weightx = 0;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.WEST;
        c0.insets = new Insets(10, 10, 10, 10);
        add(waterStatusPanel, c0);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 2;
        c1.gridwidth = 2;
        c1.gridheight = 1;
        c1.weightx = 0;
        c1.weighty = 0;
        c1.fill = GridBagConstraints.BOTH;
        c1.anchor = GridBagConstraints.WEST;
        c1.insets = new Insets(10, 10, 10, 10);
        add(inputDrankPanel, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 3;
        c2.gridwidth = 2;
        c2.gridheight = 1;
        c2.weightx = 0;
        c2.weighty = 0;
        c2.fill = GridBagConstraints.BOTH;
        c2.anchor = GridBagConstraints.WEST;
        c2.insets = new Insets(0, 10, 10, 0);
        add(quickAddPanel, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 4;
        c3.gridwidth = 2;
        c3.gridheight = 1;
        c3.weightx = 0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.BOTH;
        c3.anchor = GridBagConstraints.WEST;
        c3.insets = new Insets(0, 10, 10, 0);
        add(timePanel, c3);

        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 0;
        c4.gridy = 1;
        c4.gridwidth = 1;
        c4.gridheight = 1;
        c4.weightx = 0;
        c4.weighty = 0;
        c4.fill = GridBagConstraints.NONE;
        c4.anchor = GridBagConstraints.CENTER;
        c4.insets = new Insets(0, 80, 0, 0);
        add(timeLeftLabel, c4);

        GridBagConstraints c5 = new GridBagConstraints();
        c5.gridx = 1;
        c5.gridy = 1;
        c5.gridwidth = 1;
        c5.gridheight = 1;
        c5.weightx = 0;
        c5.weighty = 0;
        c5.fill = GridBagConstraints.NONE;
        c5.anchor = GridBagConstraints.WEST;
        c5.insets = new Insets(0, 20, 0, 0);
        add(targetLeftLabel, c5);

        //----------------- Main Frame Properties  -----------------//
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);

        makeSetting();


        //----------------- Add ActionListener -----------------//
        menuChangeMaxValue.addActionListener(this);
        menuChangeNextTimeDrink.addActionListener(this);
        drankButton.addActionListener(this);
        stopwatchStartButton.addActionListener(this);
        menuReduceWaterAmount.addActionListener(this);
        menuWhiteList.addActionListener(this);
        trayIcon.addActionListener(this);
        aboutItem.addActionListener(this);
        openItem.addActionListener(this);
        exitItem.addActionListener(this);
        resetButton.addActionListener(this);
        add50.addActionListener(this);
        add100.addActionListener(this);
        add200.addActionListener(this);
        add500.addActionListener(this);
        beepInterval.addActionListener(this);
        calendarMenuItem.addActionListener(this);

        whiteListQuite.addItemListener(this);
        allQuite.addItemListener(this);
        inputDrankText.addKeyListener(this);
        beepLoop.addItemListener(this);

        //----------------- Add WindowStateListener for SystemTray -----------------//
        addWindowStateListener(e -> {
            if (e.getNewState() == ICONIFIED) {
                setVisible(false);
            }
        });

    }

    //----------------- Enter value of TextField By Pressing "Enter" -----------------//
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            if (inputDrankText.getText() != null && !(inputDrankText.getText().equals(""))) {
                waterAmount += Integer.parseInt(inputDrankText.getText());
                waterProgressBar.updateBarValue(waterAmount);
                waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
                int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
                if (targetLeft < 0) {
                    targetLeft = Math.abs(targetLeft);
                    targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
                } else {
                    targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
                }
                //System.out.println("enter");
            }
        }
    }

    //----------------- ActionPerformed -----------------//
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        //================= drankButton =================//
        if (source == drankButton) {
            if (inputDrankText.getText() != null && !(inputDrankText.getText().equals(""))) {
                waterAmount += Integer.parseInt(inputDrankText.getText());
                waterProgressBar.updateBarValue(waterAmount);
                waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");

                dataJson.setAmountJson(today, waterAmount);

                int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
                if (targetLeft < 0) {
                    targetLeft = Math.abs(targetLeft);
                    targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
                } else {
                    targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
                }

            }
        }
        //================= menuChangeMaxValue =================//
        else if (source == menuChangeMaxValue) {
            String input;
            do {
                input = JOptionPane.showInputDialog("Enter Number to Change the Target ");
                if (input.matches("^[1-9][0-9]*$")) {
                    waterProgressBar.setMaximum(Integer.parseInt(input));
                    waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
                    waterProgressBar.updateBarValue(waterAmount);

                    dataJson.setTargetJson(today, (Integer.parseInt(input)));

                    int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
                    if (targetLeft < 0) {
                        targetLeft = Math.abs(targetLeft);
                        targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
                    } else {
                        targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please Enter Number");
                }
            } while (!input.matches("^[1-9][0-9]*$"));

        }
        //================= menuChangeNextTimeDrink =================//
        else if (source == menuChangeNextTimeDrink) {
            changeMaxValueGUI.setVisible(true);
        }
        //================= menuReduceWaterAmount =================//
        else if (source == menuReduceWaterAmount) {
            String input;
            do {
                input = JOptionPane.showInputDialog("Enter Number to Reduce the Water Amount ");
                if (input.matches("^[1-9][0-9]*$")) {
                    waterAmount -= Integer.parseInt(input);
                    waterProgressBar.updateBarValue(waterAmount);

                    if (waterAmount < 0) {
                        waterAmount = 0;
                    }
                    waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
                    dataJson.setAmountJson(today, waterAmount);

                    int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
                    if (targetLeft < 0) {
                        targetLeft = Math.abs(targetLeft);
                        targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
                    } else {
                        targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please Enter Number");
                }
            } while (!input.matches("^[1-9][0-9]*$"));
        }
        //================= stopwatchStartButton =================//
        else if (source == stopwatchStartButton) {
            if (stopwatchStartButton.getText().equals("Start")) {
                stopwatch.startStopwatch();
                timeLabel.setForeground(Color.BLACK);
                stopwatchStartButton.setText("Pause");
            } else if (stopwatchStartButton.getText().equals("Pause")) {
                stopwatch.pause();
                timeLabel.setForeground(Color.RED);
                stopwatchStartButton.setText("Resume");
            } else if (stopwatchStartButton.getText().equals("Resume")) {
                stopwatch.resume();
                timeLabel.setForeground(Color.BLACK);
                stopwatchStartButton.setText("Pause");
            }
        }
        //================= add50 Button =================//
        else if (source == add50) {
            waterAmount += 50;
            waterProgressBar.updateBarValue(waterAmount);
            waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
            dataJson.setAmountJson(today, waterAmount);
            int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
            if (targetLeft < 0) {
                targetLeft = Math.abs(targetLeft);
                targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
            } else {
                targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
            }
        }
        //================= add100 Button =================//
        else if (source == add100) {
            waterAmount += 100;
            waterProgressBar.updateBarValue(waterAmount);
            waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
            dataJson.setAmountJson(today, waterAmount);
            int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
            if (targetLeft < 0) {
                targetLeft = Math.abs(targetLeft);
                targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
            } else {
                targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
            }
        }
        //================= add200 Button =================//
        else if (source == add200) {
            waterAmount += 200;
            waterProgressBar.updateBarValue(waterAmount);
            waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
            dataJson.setAmountJson(today, waterAmount);
            int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
            if (targetLeft < 0) {
                targetLeft = Math.abs(targetLeft);
                targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
            } else {
                targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
            }
        }
        //================= add500 Button =================//
        else if (source == add500) {
            waterAmount += 500;
            waterProgressBar.updateBarValue(waterAmount);
            waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
            dataJson.setAmountJson(today, waterAmount);
            int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
            if (targetLeft < 0) {
                targetLeft = Math.abs(targetLeft);
                targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
            } else {
                targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
            }
        }
        //================= resetButton =================//
        else if (source == resetButton) {
            stopwatch.reset();
            timeLabel.setForeground(Color.RED);
            stopwatchStartButton.setText("Start");
        }
        //================= menuWhiteList =================//
        else if (source == menuWhiteList) {
            taskManagerGUI.updateTaskList();
            taskManagerGUI.setVisible(true);
        }
        //================= trayIcon =================//
        else if (source == trayIcon) {
            //tray.remove(trayIcon);
            setVisible(true);
            setState(JFrame.NORMAL);
        }
        //================= aboutItem (form systray) =================//
        else if (source == aboutItem) {
            JPanel iconPanel = new JPanel(new GridBagLayout());

            ImageIcon icon = new ImageIcon(MainPageGUI.class.getClassLoader().getResource("icon/water_icon_about.png"));

            Image image = icon.getImage(); // transform it
            Image newimg = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newimg);  // transform it back

            JLabel iconLabel = new JLabel(icon);
            iconPanel.add(iconLabel);

            JLabel label = new JLabel("<html><center>SeeWordDrinkWater (Version 1.2 2020)<br>!  GO DRINK WATER  !<br>By Cheng Wong Kwan");

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(label, BorderLayout.SOUTH);
            mainPanel.add(iconPanel, BorderLayout.NORTH);

            label.setHorizontalAlignment(SwingConstants.CENTER);
            JOptionPane.showMessageDialog(null, mainPanel, "About this Java", JOptionPane.PLAIN_MESSAGE);
        }
        //================= exitItem (form systray) =================//
        else if (source == exitItem) {
            System.exit(0);
        }
        //================= openItem (form systray) =================//
        else if (source == openItem) {
            setVisible(true);
            setState(JFrame.NORMAL);
        }
        //================= beepInterval =================//
        else if (source == beepInterval) {
            String input;
            int currentPeriod = stopwatch.getBeepPeriod() / 1000;
            do {
                input = JOptionPane.showInputDialog("<html>Enter Integer to Set How Long Next Beep After Beep(If Non-Stop Function ON)<br>Current Period is " + currentPeriod + "s<html>");
                if (input.matches("^[1-9][0-9]*$")) {
                    stopwatch.setBeepPeriod(Integer.parseInt(input) * 1000);
                    dataJson.setBeepIntervalJson(Integer.parseInt(input) * 1000);
                    //System.out.println(dataJson.getBeepIntervalJson());
                } else {
                    JOptionPane.showMessageDialog(this, "Please Enter Integer");
                }
            } while (!input.matches("^[1-9][0-9]*$"));
        }
        //================= calendarMenuItem =================//
        else if (source == calendarMenuItem) {
            calendarGUI.setVisible(true);
        }
    }

    public void makeSetting() {
        //----------------- Setting -----------------//
        stopwatch.setBeepPeriod(dataJson.getBeepIntervalJson());

        if (dataJson.getAmountJson(today) < 0 && dataJson.getTargetJson(today) < 0) {
            //System.out.println(dataJson.jsonFile);
            dataJson.createTodayJson();
            System.out.println("Enter" + today + dataJson.today);
        }

        waterAmount = dataJson.getAmountJson(today);

        //System.out.println(waterAmount);
        waterProgressBar.setMaximum(dataJson.getTargetJson(today));
        waterProgressBar.updateBarValue(waterAmount);

        waterProgressBar.updateBarString("" + waterAmount + "/" + WaterProgressBar.MAXIMUM + " ml (" + (waterAmount * 100.0f / WaterProgressBar.MAXIMUM) + "%)");
        //waterProgressBar.updateBarString("" + waterAmount + "/" + waterProgressBar.MAXIMUM + " ml (" + String.format("%.01f",(waterAmount * 100.0f) / waterProgressBar.MAXIMUM) + "%)");

        int targetLeft = WaterProgressBar.MAXIMUM - waterAmount;
        if (targetLeft < 0) {
            targetLeft = Math.abs(targetLeft);
            targetLeftLabel.setText("Target Exceed: ++" + targetLeft + " ml");
        } else {
            targetLeftLabel.setText("Target Left: " + targetLeft + " ml");
        }

        stopwatch.setIntervalValue(dataJson.getNextTimeJson());

        beepLoop.setState(dataJson.getBeepLoopJson());
        allQuite.setState(dataJson.getAllQuiteJson());
        whiteListQuite.setState(dataJson.getWhiteQuiteJson());

        stopwatch.setLoop(beepLoop.getState());
        stopwatch.setAllQuite(allQuite.getState());
        stopwatch.setWhiteListQuite(whiteListQuite.getState());
    }

    //----------------- itemStateChanged -----------------//
    public void itemStateChanged(ItemEvent ae) {
        Object source = ae.getSource();
        //================= whiteListQuite (form systray) =================//
        if (source == whiteListQuite) {
            /*
                int whiteListQuite = e.getStateChange();
                if (whiteListQuite == ItemEvent.SELECTED) {
                    stopwatch.setWhiteListQuite(true);
                } else {
                    stopwatch.setWhiteListQuite(false);
                }
            }*/
            stopwatch.setWhiteListQuite(whiteListQuite.getState());
            dataJson.setWhiteQuiteJson(whiteListQuite.getState());
        }
        //================= allQuite (form systray) =================//
        else if (source == allQuite) {

            stopwatch.setAllQuite(allQuite.getState());
            dataJson.setAllQuiteJson(allQuite.getState());
        }
        //================= beepLoop =================//
        else if (source == beepLoop) {
            stopwatch.setLoop(beepLoop.getState());
            dataJson.setBeepLoopJson(beepLoop.getState());
            //System.out.println(beepLoop.getState());
        }

    }

    public static void main(String[] args) {
        MainPageGUI mainFrame = new MainPageGUI();
        mainFrame.setVisible(true);

    }

}
