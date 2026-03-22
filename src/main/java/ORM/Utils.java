package ORM;

import org.postgresql.util.PGInterval;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;

public class Utils {

    public static LocalDate toLocalDateOrNull(Date date){
        if (date == null)
            return null;
        else
            return date.toLocalDate();
    }

    public static Duration PGIntervalToDuration(PGInterval duration) {
        return Duration.ofHours(duration.getHours()).plusMinutes(duration.getMinutes()).plusSeconds(duration.getWholeSeconds());
    }
}
