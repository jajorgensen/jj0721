package demo.rental.dates;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * ClosestWeekdayHolidays are like FixedHolidays but if they would happen to land on a weekend, they are instead
 * observed on the closest weekday. Specifically, if holiday would land on a Saturday, it is moved the preceding
 * Friday, and if it would land on a Sunday, it is moved to the following Monday
 */
public class ClosestWeekdayHoliday implements Holiday {
    private final FixedHoliday fixedHoliday;

    /**
     * Constructs a ClosestWeekdayHoliday instance for a given month and day
     *
     * @param month the month for this holiday
     * @param dayOfMonth  the day of the month of this holiday
     */
    public ClosestWeekdayHoliday(int month, int dayOfMonth) {
        fixedHoliday = new FixedHoliday(month, dayOfMonth);
    }

    /**
     * Gets the date this holiday will be observed in the given year
     * If the date would be a weekend, it is instead observed on the closest weekday
     *
     * @param year the year for which the holiday date is being requested
     * @return a LocalDate with the date this holiday would be observed in the give year
     */
    @Override
    public LocalDate getDateForYear(int year) {
        LocalDate date = fixedHoliday.getDateForYear(year);
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            date = date.minusDays(1);
        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        return date;
    }
}
