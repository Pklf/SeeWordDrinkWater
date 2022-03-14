package SWDW;

import GUI.MainPageGUI;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimeLeft extends TimerTask {

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime ldt = LocalDateTime.parse("2020-11-13 23:59:57",df);

    JLabel timeLeftLabel;
    MainPageGUI mainGUI;

    private void setInterval() {
        ldt = ldt.plusSeconds(1);
        //return --interval;
    }

    public TimeLeft(JLabel timeLeftLabel,MainPageGUI mainGUI){
        this.timeLeftLabel = timeLeftLabel;
        this.mainGUI = mainGUI;
    }

    public TimeLeft(){
    }

    public String endDay(){
        Date today = new Date();

        Date dayEnd = atEndOfDay(today);
        long difference = dayEnd.getTime() - today.getTime();
//        int hours = (int) (difference / (1000*60*60) % 24);
//        int minutes = (int) (difference / (1000*60) % 60);
//        int seconds = (int) (difference / 1000 % 60);
        //String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        String timeString = String.format("%02d:%02d:%02d ",
                TimeUnit.MILLISECONDS.toHours(difference),
                TimeUnit.MILLISECONDS.toMinutes(difference) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)),
                TimeUnit.MILLISECONDS.toSeconds(difference) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference))
        );

        return timeString;
    }

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        //LocalDateTime endOfDay = localDateTime.plusSeconds(3);
        return localDateTimeToDate(endOfDay);
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void run() {
        timeLeftLabel.setText("Day End Time Left: "+endDay().trim());
        setInterval();
        if (LocalDate.now().compareTo(MainPageGUI.today) > 0){
            mainGUI.makeSetting();
        }
    }


    public static void main(String[] args){
        //TimeLeft test = new TimeLeft();
        Timer timer = new Timer();
        timer.schedule(new TimeLeft(), 0, 1000);
    }
}

