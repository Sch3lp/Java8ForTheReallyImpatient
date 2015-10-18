package chapter5;

import org.assertj.core.api.Condition;
import org.assertj.core.data.Index;
import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static chapter5.Ch5.daysAliveSince;
import static chapter5.Ch5.next;
import static chapter5.Ch5.superstitiousDaysInBetween;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;


public class Ch5Test {

    @Test
    public void ex2() throws Exception {
        LocalDate leapDay = LocalDate.of(2000, 2, 29);
        assertThat(leapDay.plusYears(1)).isEqualTo(LocalDate.of(2001, 2, 28));
        assertThat(leapDay.plusYears(4)).isEqualTo(LocalDate.of(2004, 2, 29));
        assertThat(leapDay.plusYears(1).plusYears(1).plusYears(1).plusYears(1)).isEqualTo(LocalDate.of(2004, 2, 28)); //because 2003/02/28 +1 year = 2004/02/28
    }

    @Test
    public void ex3() throws Exception {
        LocalDate fri = LocalDate.of(2015, Month.OCTOBER, 16);
        LocalDate sat = fri.plusDays(1);
        LocalDate sun = sat.plusDays(1);
        LocalDate mon = sun.plusDays(1);
        LocalDate tue = mon.plusDays(1);
        Predicate<LocalDate> workday = w -> w.getDayOfWeek().getValue() < 6;

        assertThat(fri.with(next(workday))).isEqualTo(mon);
        assertThat(sat.with(next(workday))).isEqualTo(mon);
        assertThat(sun.with(next(workday))).isEqualTo(mon);
        assertThat(mon.with(next(workday))).isEqualTo(tue);
    }

    @Test
    public void ex5() throws Exception {
        assertThat(daysAliveSince(LocalDate.now().minusDays(1))).isEqualTo(1);
        assertThat(daysAliveSince(LocalDate.now().minusWeeks(1))).isEqualTo(7);
        assertThat(daysAliveSince(LocalDate.of(1981, Month.JUNE, 18))).isEqualTo(12540);
    }

    @Test
    public void ex6() throws Exception {
        LocalDate startOf19thCentury = LocalDate.ofYearDay(1900, 1);
        LocalDate endOf20thCentury = LocalDate.ofYearDay(2000, 1);
        Stream<LocalDate> superstitiousStream = superstitiousDaysInBetween(startOf19thCentury, endOf20thCentury);
        assertThat(superstitiousStream.map(LocalDate::getDayOfWeek).collect(toList())).containsOnly(DayOfWeek.FRIDAY);
        // Really wishing there were assertions with predicates on streams and lists.
        // Something like assertThat(superstitiousStream).hasOnly(d -> d.getDayOfWeek.equals(FRIDAY));
    }

}