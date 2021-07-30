package demo.rental.dates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Relative holidays do not have a fixed day of the year and instead relative to a weekday in a month, and therefore
 * will be on different dates year to year
 * For example, Labor Day is the first Monday in September
 */
public record RelativeHoliday(int weekOfMonth, int dayOfWeek, int month) implements Holiday {

    /**
     * Gets the date for this relative holiday for a given year
     *
     * @param year the year for which the holiday date is being requested
     * @return a LocalDate with the date of this holiday in the given year
     */
    @Override
    public LocalDate getDateForYear(int year) {
        return LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(weekOfMonth, DayOfWeek.of(dayOfWeek)));
    }
}
