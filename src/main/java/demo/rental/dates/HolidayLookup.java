package demo.rental.dates;

import java.util.List;

/**
 * HolidayLookup is an interface for getting holidays which may affect rental charges
 */
public interface HolidayLookup {
    /**
     * Gets all the holidays which affect rental charges
     *
     * @return a List of Holidays in the system
     */
    List<Holiday> getAllHolidays();
}
