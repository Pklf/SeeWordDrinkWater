package GUI;

import SWDW.DataJson;
import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarGUI extends JFrame {
    private static DatePickerSettings settings = new DatePickerSettings();
    private static CalendarPanel calendarPanel = new CalendarPanel(settings);


    private static JPanel infoPanel = new JPanel();
    private static JLabel fromLabel = new JLabel("From");
    private static JLabel toLabel = new JLabel("To");
    private static DatePicker datePicker1;
    private static DatePicker datePicker2;
    private static JLabel totalDayLabel = new JLabel("Total Days: ");
    private static JLabel passLabel = new JLabel("No. Target Fullfilled: ");

    private static JLabel selectedDayLabel = new JLabel("Day Selected: ");
    private static JLabel targetLabel = new JLabel("Target: ");
    private static JLabel waterAmountLabel = new JLabel("Water Amount: ");
    private static JLabel statusLabel = new JLabel("Status: ");

    private static ArrayList<String> passDate = new ArrayList<String>();
    private static ArrayList<String> failDate = new ArrayList<String>();

    static MainPageGUI mainGUI;

    public CalendarGUI(MainPageGUI mainGUI) {
        this.mainGUI = mainGUI;
        setResizable(false);
//        updatePassDate();


        setTitle("Calendar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridBagLayout());
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        settings.setHighlightPolicy(new SampleHighlightPolicy());
        calendarPanel.setSelectedDate(LocalDate.now());
        calendarPanel.addCalendarListener(new SampleCalendarListener());
        calendarPanel.setBorder(new LineBorder(Color.lightGray));

        settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd/MM/yyyy");
        settings.setFormatForDatesBeforeCommonEra("dd/MM/uuuu");
        datePicker1 = new DatePicker(settings);
        datePicker1.addDateChangeListener(new SampleDateChangeListener("dataPickerOne"));

        settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd/MM/yyyy");
        settings.setFormatForDatesBeforeCommonEra("dd/MM/uuuu");


        datePicker2 = new DatePicker(settings);
        datePicker2.addDateChangeListener(new SampleDateChangeListener("dataPickerTwo"));

        GridBagConstraints i0 = new GridBagConstraints();
        i0.gridx = 0;
        i0.gridy = 0;
        i0.gridwidth = 1;
        i0.gridheight = 1;
        i0.weightx = 0;
        i0.weighty = 0;
        i0.fill = GridBagConstraints.NONE;
        i0.anchor = GridBagConstraints.WEST;
        i0.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(fromLabel, i0);

        GridBagConstraints i1 = new GridBagConstraints();
        i1.gridx = 1;
        i1.gridy = 0;
        i1.gridwidth = 1;
        i1.gridheight = 1;
        i1.weightx = 0;
        i1.weighty = 0;
        i1.fill = GridBagConstraints.NONE;
        i1.anchor = GridBagConstraints.WEST;
        i1.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(toLabel, i1);

        GridBagConstraints i2 = new GridBagConstraints();
        i2.gridx = 0;
        i2.gridy = 1;
        i2.gridwidth = 1;
        i2.gridheight = 1;
        i2.weightx = 0;
        i2.weighty = 0;
        i2.fill = GridBagConstraints.NONE;
        i2.anchor = GridBagConstraints.WEST;
        i2.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(datePicker1, i2);

        GridBagConstraints i3 = new GridBagConstraints();
        i3.gridx = 1;
        i3.gridy = 1;
        i3.gridwidth = 1;
        i3.gridheight = 1;
        i3.weightx = 0;
        i3.weighty = 0;
        i3.fill = GridBagConstraints.NONE;
        i3.anchor = GridBagConstraints.WEST;
        i3.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(datePicker2, i3);

        GridBagConstraints i4 = new GridBagConstraints();
        i4.gridx = 0;
        i4.gridy = 2;
        i4.gridwidth = 1;
        i4.gridheight = 1;
        i4.weightx = 0;
        i4.weighty = 0;
        i4.fill = GridBagConstraints.NONE;
        i4.anchor = GridBagConstraints.WEST;
        i4.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(totalDayLabel, i4);

        GridBagConstraints i5 = new GridBagConstraints();
        i5.gridx = 0;
        i5.gridy = 3;
        i5.gridwidth = 2;
        i5.gridheight = 1;
        i5.weightx = 0;
        i5.weighty = 0;
        i5.fill = GridBagConstraints.NONE;
        i5.anchor = GridBagConstraints.WEST;
        i5.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(passLabel, i5);

        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setPreferredSize(new Dimension(220, 1));

        GridBagConstraints i6 = new GridBagConstraints();
        i6.gridx = 0;
        i6.gridy = 4;
        i6.gridwidth = 2;
        i6.gridheight = 1;
        i6.weightx = 1;
        i6.weighty = 0;
        i6.fill = GridBagConstraints.NONE;
        i6.anchor = GridBagConstraints.WEST;
        i6.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(sep, i6);

        GridBagConstraints i7 = new GridBagConstraints();
        i7.gridx = 0;
        i7.gridy = 5;
        i7.gridwidth = 2;
        i7.gridheight = 1;
        i7.weightx = 0;
        i7.weighty = 0;
        i7.fill = GridBagConstraints.NONE;
        i7.anchor = GridBagConstraints.WEST;
        i7.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(selectedDayLabel, i7);

        GridBagConstraints i8 = new GridBagConstraints();
        i8.gridx = 0;
        i8.gridy = 6;
        i8.gridwidth = 2;
        i8.gridheight = 1;
        i8.weightx = 0;
        i8.weighty = 0;
        i8.fill = GridBagConstraints.NONE;
        i8.anchor = GridBagConstraints.WEST;
        i8.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(targetLabel, i8);

        GridBagConstraints i9 = new GridBagConstraints();
        i9.gridx = 0;
        i9.gridy = 7;
        i9.gridwidth = 2;
        i9.gridheight = 1;
        i9.weightx = 0;
        i9.weighty = 0;
        i9.fill = GridBagConstraints.NONE;
        i9.anchor = GridBagConstraints.WEST;
        i9.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(waterAmountLabel, i9);

        GridBagConstraints i10 = new GridBagConstraints();
        i10.gridx = 0;
        i10.gridy = 8;
        i10.gridwidth = 2;
        i10.gridheight = 1;
        i10.weightx = 0;
        i10.weighty = 0;
        i10.fill = GridBagConstraints.NONE;
        i10.anchor = GridBagConstraints.WEST;
        i10.insets = new Insets(10, 0, 10, 10);
        infoPanel.add(statusLabel, i10);

        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.gridwidth = 1;
        c0.gridheight = 1;
        c0.weightx = 0;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.NONE;
        c0.anchor = GridBagConstraints.WEST;
        c0.insets = new Insets(10, 10, 10, 10);
        add(calendarPanel, c0);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 1;
        c1.gridy = 0;
        c1.gridwidth = 2;
        c1.gridheight = 1;
        c1.weightx = 0;
        c1.weighty = 0;
        c1.fill = GridBagConstraints.NONE;
        c1.anchor = GridBagConstraints.WEST;
        c1.insets = new Insets(10, 0, 10, 10);
        add(infoPanel, c1);

        pack();
    }

    private static class SampleDateChangeListener implements DateChangeListener {

        /**
         * datePickerName, This holds a chosen name for the date picker that we are listening to,
         * for generating date change messages in the demo.
         */
        public String datePickerName;

        /**
         * Constructor.
         */
        private SampleDateChangeListener(String datePickerName) {
            this.datePickerName = datePickerName;
        }

        /**
         * dateChanged, This function will be called each time that the date in the applicable date
         * picker has changed. Both the old date, and the new date, are supplied in the event
         * object. Note that either parameter may contain null, which represents a cleared or empty
         * date.
         */
        @Override
        public void dateChanged(DateChangeEvent event) {
            LocalDate oldDate = event.getOldDate();
            LocalDate newDate = event.getNewDate();
            String oldDateString = PickerUtilities.localDateToString(oldDate, "(null)");
            String newDateString = PickerUtilities.localDateToString(newDate, "(null)");
            String messageStart = "\nThe date in " + datePickerName + " has changed from: ";
            String fullMessage = messageStart + oldDateString + " to: " + newDateString + ".";
            System.out.println(fullMessage);

            String dateString = datePicker1.getDateStringOrSuppliedString("(null)");
            String dateString2 = datePicker2.getDateStringOrSuppliedString("(null)");

            if (dateString != null && dateString2 != null) {
                ArrayList<Integer> result = mainGUI.dataJson.getTotalDay_Pass(LocalDate.parse(dateString), LocalDate.parse(dateString2));
                totalDayLabel.setText("Total Days: " + result.get(0));
                passLabel.setText("No. Target Fullfilled: " + result.get(1));
            }
        }
    }

    private static class SampleCalendarListener implements CalendarListener {

        /**
         * selectedDateChanged, This function will be called each time that a date is selected in
         * the independent CalendarPanel. The new and old selected dates are supplied in the event
         * object. These parameters may contain null, which represents a cleared or empty date.
         * <p>
         * By intention, this function will be called even if the user selects the same date value
         * twice in a row. This is so that the programmer can catch all events of interest.
         * Duplicate events can optionally be detected with the function
         * CalendarSelectionEvent.isDuplicate().
         */
        @Override
        public void selectedDateChanged(CalendarSelectionEvent event) {
            LocalDate oldDate = event.getOldDate();
            LocalDate newDate = event.getNewDate();
            String oldDateString = PickerUtilities.localDateToString(oldDate, "(null)");
            String newDateString = PickerUtilities.localDateToString(newDate, "(null)");
            String messageStart = "\nIndependent Calendar Panel:";
            String messagePartTwo = " The selected date has changed from '";
            String fullMessage = messageStart + messagePartTwo
                    + oldDateString + "' to '" + newDateString + "'. ";
            //fullMessage += (event.isDuplicate()) ? "(Event marked as duplicate.)" : "";
            fullMessage += mainGUI.dataJson.getTargetJson(newDate);
            System.out.println(fullMessage);

            selectedDayLabel.setText("Day Selected: " + newDateString);

            int target = mainGUI.dataJson.getTargetJson(newDate);
            int amount = mainGUI.dataJson.getAmountJson(newDate);

            targetLabel.setText("Target: " + (target > 0 ? target : "No data"));
            waterAmountLabel.setText("Water Amount: " + (amount > 0 ? amount : "No data"));
            if (target < 0 || amount < 0) {
                statusLabel.setText("Status: No data");
                statusLabel.setForeground(Color.BLACK);
            } else {
                if (amount >= target) {
                    statusLabel.setText("Status: YES");
                    statusLabel.setForeground(Color.GREEN);
                } else {
                    statusLabel.setText("Status: NO");
                    statusLabel.setForeground(Color.RED);
                }

            }

        }

        @Override
        public void yearMonthChanged(YearMonthChangeEvent event) {
            YearMonth oldYearMonth = event.getOldYearMonth();
            YearMonth newYearMonth = event.getNewYearMonth();
            String oldYearMonthString = oldYearMonth.toString();
            String newYearMonthString = newYearMonth.toString();
            String messageStart = "\nIndependent Calendar Panel:";
            String messagePartTwo = " The displayed YearMonth has changed from '";
            String fullMessage = messageStart + messagePartTwo
                    + oldYearMonthString + "' to '" + newYearMonthString + "'. ";
            fullMessage += (event.isDuplicate()) ? "(Event marked as duplicate.)" : "";
            System.out.println(fullMessage);
        }
    }

    private static class SampleHighlightPolicy implements DateHighlightPolicy {
        /**
         * getHighlightInformationOrNull, Implement this function to indicate if a date should be
         * highlighted, and what highlighting details should be used for the highlighted date.
         * <p>
         * If a date should be highlighted, then return an instance of HighlightInformation. If the
         * date should not be highlighted, then return null.
         * <p>
         * You may (optionally) fill out the fields in the HighlightInformation class to give any
         * particular highlighted day a unique foreground color, background color, or tooltip text.
         * If the color fields are null, then the default highlighting colors will be used. If the
         * tooltip field is null (or empty), then no tooltip will be displayed.
         * <p>
         * Dates that are passed to this function will never be null.
         */
        @Override
        public HighlightInformation getHighlightInformationOrNull(LocalDate date) {
            // Highlight a chosen date, with a tooltip and a red background color.

            if (DataJson.date_pass.contains(date.toString())) {
                return new HighlightInformation(Color.GREEN);
            } else if (DataJson.date_fail.contains(date.toString())) {
                return new HighlightInformation(Color.RED);
            }

            return null;
        }
    }

}

