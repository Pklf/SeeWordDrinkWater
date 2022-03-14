package GUI;

import GUI.Util.FilteredField;
import SWDW.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeMaxValueGUI extends JFrame implements ActionListener {
    private JLabel hourLabel = new JLabel("New Hours: ");
    private JLabel minLabel = new JLabel("New Mins: ");
    private JLabel secLabel = new JLabel("New Secs: ");

    private FilteredField filterTextField = new FilteredField();
    String[] filters = {"^[A-Za-z][A-Za-z0-9_ ]*$", "^[0-9][0-9]*$"};
    private JTextField hourText = filterTextField.createFilteredField(filters[1]);
    private JTextField minText = filterTextField.createFilteredField(filters[1]);
    private JTextField secText = filterTextField.createFilteredField(filters[1]);

    private Stopwatch stopwatchFromMainGUI = new Stopwatch();
    private MainPageGUI MainGUI;

    private JButton changeButton = new JButton("Change");
    private JButton cancelButton = new JButton("Cancel");

    public ChangeMaxValueGUI(Stopwatch stopwatchFromMainGUI, MainPageGUI MainGUI) {
        super("ChangeMaxValueGUI");

        setLayout(new GridBagLayout());
        setResizable(false);
        this.stopwatchFromMainGUI = stopwatchFromMainGUI;
        this.MainGUI = MainGUI;

        //hourText.setPreferredSize(new Dimension(5,5));
        hourText.setText("" + 0);
        minText.setText("" + 30);
        secText.setText("" + 0);

        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.gridwidth = 1;
        c0.gridheight = 1;
        c0.weightx = 0;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.WEST;
        add(hourLabel, c0);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        c1.weightx = 0;
        c1.weighty = 0;
        c1.fill = GridBagConstraints.BOTH;
        c1.anchor = GridBagConstraints.WEST;
        add(minLabel, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 2;
        c2.gridwidth = 1;
        c2.gridheight = 1;
        c2.weightx = 0;
        c2.weighty = 0;
        c2.fill = GridBagConstraints.BOTH;
        c2.anchor = GridBagConstraints.WEST;
        add(secLabel, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 1;
        c3.gridy = 0;
        c3.gridwidth = 2;
        c3.gridheight = 1;
        c3.weightx = 0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.BOTH;
        c3.anchor = GridBagConstraints.CENTER;
        c3.insets = new Insets(10, 10, 10, 0);
        add(hourText, c3);


        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 1;
        c4.gridy = 1;
        c4.gridwidth = 2;
        c4.gridheight = 1;
        c4.weightx = 0;
        c4.weighty = 0;
        c4.fill = GridBagConstraints.BOTH;
        c4.anchor = GridBagConstraints.CENTER;
        c4.insets = new Insets(10, 10, 10, 0);
        add(minText, c4);

        GridBagConstraints c5 = new GridBagConstraints();
        c5.gridx = 1;
        c5.gridy = 2;
        c5.gridwidth = 2;
        c5.gridheight = 1;
        c5.weightx = 0;
        c5.weighty = 0;
        c5.fill = GridBagConstraints.BOTH;
        c5.anchor = GridBagConstraints.CENTER;
        c5.insets = new Insets(10, 10, 10, 0);
        add(secText, c5);

        GridBagConstraints c6 = new GridBagConstraints();
        c6.gridx = 1;
        c6.gridy = 3;
        c6.gridwidth = 1;
        c6.gridheight = 1;
        c6.weightx = 0;
        c6.weighty = 0;
        c6.fill = GridBagConstraints.BOTH;
        c6.anchor = GridBagConstraints.CENTER;
        c6.insets = new Insets(10, 10, 10, 0);
        add(changeButton, c6);

        GridBagConstraints c7 = new GridBagConstraints();
        c7.gridx = 2;
        c7.gridy = 3;
        c7.gridwidth = 1;
        c7.gridheight = 1;
        c7.weightx = 0;
        c7.weighty = 0;
        c7.fill = GridBagConstraints.BOTH;
        c7.anchor = GridBagConstraints.CENTER;
        c7.insets = new Insets(10, 10, 10, 0);
        add(cancelButton, c7);

        pack();
        setResizable(false);

        cancelButton.addActionListener(this);
        changeButton.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        //----------------- cancelButton -----------------//
        if (source == cancelButton) {
            this.dispose();
        }
        //----------------- changeButton -----------------//
        else if (source == changeButton) {
            int newInterval = Integer.parseInt(hourText.getText()) * 3600 + Integer.parseInt(minText.getText()) * 60 + Integer.parseInt(secText.getText());

            stopwatchFromMainGUI.setIntervalValue(newInterval);
            MainGUI.dataJson.setNextTimeJson(newInterval);
            this.dispose();
        }
    }


}
