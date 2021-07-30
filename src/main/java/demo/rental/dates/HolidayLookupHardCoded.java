package demo.rental.dates;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of HolidayLookup that hard codes a list of holidays.
 * This is deprecated now that I have a more extensible way to track holidays
 */
public class HolidayLookupHardCoded implements HolidayLookup {
    public List<Holiday> getAllHolidays() {
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(new ClosestWeekdayHoliday(Month.JULY.getValue(), 4));
        holidays.add(new RelativeHoliday(1, DayOfWeek.MONDAY.getValue(), Month.SEPTEMBER.getValue()));
        return holidays;
    }
}
