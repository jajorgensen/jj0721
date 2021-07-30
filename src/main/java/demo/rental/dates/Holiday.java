package demo.rental.dates;

import java.time.LocalDate;

/**
 * Holiday is an interface for getting dates for holidays
 */
public interface Holiday {
    /**
     * Gets a particular date for the holiday in a given year
     *
     * @param year  the year for which the holiday date is being requested
     * @return a LocalDate containing the date for this holiday in the given year
     */
    LocalDate getDateForYear(int year);
}
