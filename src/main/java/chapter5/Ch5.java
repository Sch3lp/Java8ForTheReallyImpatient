package chapter5;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.time.DayOfWeek.FRIDAY;

public class Ch5 {

    public static TemporalAdjuster next(Predicate<LocalDate> predicate) {
        return TemporalAdjusters.ofDateAdjuster(localDate -> {
                    return Stream.iterate(localDate.plusDays(1), l -> l.plusDays(1))
                            .filter(predicate)
                            .findFirst()
                            .get();
                });
    }

    public static long daysAliveSince(LocalDate birthDate) {
        return birthDate.until(LocalDate.now(), ChronoUnit.DAYS);
    }

    public static Stream<LocalDate> superstitiousDaysInBetween(LocalDate start, LocalDate end) {
        long days = start.until(end, ChronoUnit.DAYS);
        return Stream.iterate(start, d -> d.plusDays(1))
                .limit(days)
                .filter(d -> d.getDayOfWeek().equals(FRIDAY))
                .filter(d -> d.getDayOfMonth() == 13);
    }
}
