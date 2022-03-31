package net.sopod.soim.common.util;

import com.google.common.base.Preconditions;

import java.time.*;
import java.util.Date;

/**
 * ImClock
 *
 * @author tmy
 * @date 2022-03-31 21:16
 */
public class ImClock {

    private static Clock clock = Clock.systemDefaultZone();

    public static long millis() {
        return clock.millis();
    }

    public static int seconds() {
        return (int) (millis() / 1000);
    }

    public static LocalDateTime dateTime() {
        return LocalDateTime.now(clock);
    }

    public static LocalDate localDate() {
        return LocalDate.now(clock);
    }

    public static Date date() {
        return new Date(clock.millis());
    }

    public static LocalDateTime msec2time(long msec) {
        Instant instant = Instant.ofEpochMilli(msec);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static void setClock(Clock newClock) {
        Preconditions.checkNotNull(newClock);
        Preconditions.checkArgument(
                clock.millis() <= newClock.millis(),
                "不允许回调时间: current=%s, new=%s",
                LocalDateTime.now(clock),
                LocalDateTime.now(newClock));
        ImClock.clock = newClock;
    }

}
