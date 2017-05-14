package VehicleParkSystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pasindu on 11/16/2016.
 */

public class DateTime {
    private String date, time;

    DateTime() {
        super();

        Date dateTime = new Date();

        DateFormat d = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat t = new SimpleDateFormat("HH:mm:ss");

        this.date = d.format(dateTime);
        this.time = t.format(dateTime);
    }

    String getDate() {
        return date;
    }

    String getTime() {
        return time;
    }
}
