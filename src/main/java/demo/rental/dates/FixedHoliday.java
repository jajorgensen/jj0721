package demo.rental.dates;

import java.time.LocalDate;

/**
 * FixedHolidays are always on the same day, and the same month every year
 */
public record FixedHoliday(int month, int dayOfMonth) implements Holiday {

    /**
     * Gets the date for this holiday this year.
     * Holiday date will always be the same month and day, regardless of year
     *
     * @param year the year for which the holiday date is being requested
     * @return a LocalDate for the date of this holiday in the given year
     */
    @Override
    public LocalDate getDateForYear(int year) {
        return LocalDate.of(year, month, dayOfMonth);
    }
}
