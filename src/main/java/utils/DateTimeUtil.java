package utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {

    public static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static Long parseTime (String time) {
        Long timeStamp = null;
        try {
            LocalTime localTime = LocalTime.parse(time, TIME_FORMAT);
            timeStamp = localTime.toNanoOfDay();
        } catch (Exception ex) {
            System.out.println("Exception while parsing time: " + time);
        }
        return timeStamp;
    }
}
