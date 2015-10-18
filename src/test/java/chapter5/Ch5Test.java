package chapter5;

import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.function.Predicate;

import static chapter5.Ch5.next;
import static org.assertj.core.api.Assertions.assertThat;


public class Ch5Test extends TestCase {

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
}