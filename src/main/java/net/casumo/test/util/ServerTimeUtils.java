package net.casumo.test.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ServerTimeUtils {

    private static final String DEFAULT_UTC = "Etc/Greenwich";

    public static long getCurrentTimeMillis() {
        final LocalDateTime ldt = LocalDateTime.now();
        return ldt.atZone(ZoneId.of(DEFAULT_UTC)).toEpochSecond() * 1000;
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(DEFAULT_UTC));
    }

    public static long getTimeInMillis(final LocalDateTime ldt) {
        final ZoneId zoneId = ZoneId.of(DEFAULT_UTC);
        return ldt.atZone(zoneId).toEpochSecond() * 1000;
    }

    public static LocalDateTime getLocalDateTime(final Long timeStamp) {
        final Date date = new Date(timeStamp);
        final Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.of(DEFAULT_UTC));
    }

}
