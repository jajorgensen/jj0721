package demo.rental.dates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DayCounter is a classed used for counting the number of days between two days, including the number of weekdays
 * between two date, the number of weekend days between two dates, the number of holidays between two dates, etc
 */
public class DayCounter {
    interface HolidayFilter {
        boolean filter(LocalDate date);
    }

    /**
     * Gets the number of weekdays between two dates, not including the start day
     *
     * @param start  The date after which we start counting
     * @param end  The date on which we stop counting
     * @return the number of weekdays from the day after start up to and including end, 0 if start is not before end
     */
    public static int countWeekdaysBetween(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) return 0;
        int weekDaysBetween = 0;
        LocalDate adjustedStart = start;

        // walk the start day forward until it's on a Sunday, or it reaches end day, counting days as we go
        while(adjustedStart.isBefore(end) && adjustedStart.getDayOfWeek() != DayOfWeek.SUNDAY) {
            adjustedStart = adjustedStart.plusDays(1);
            if (isWeekday(adjustedStart.getDayOfWeek())) {
                weekDaysBetween++;
            }
        }
        // walk end day backward until it's on a Sunday, or it reaches the adjusted start day, counting days as we go
        LocalDate adjustedEnd = end;
        while (adjustedEnd.isAfter(adjustedStart) && adjustedEnd.getDayOfWeek() != DayOfWeek.SUNDAY) {
            if (isWeekday(adjustedEnd.getDayOfWeek())) {
                weekDaysBetween++;
            }
            adjustedEnd = adjustedEnd.minusDays(1);
        }

        /*
           Now, either adjustedStart and adjustedEnd are on the same day OR both of them are on Sundays. So we
           know that the number of days between adjustedStart and adjustedEnd is a multiple of 7, and for every 7
           days between, 5 are weekdays and 2 are weekends. So add ((daysBetween / 7) * 5) weekdays;
         */
        int daysBetween = getDaysBetween(adjustedStart, adjustedEnd);
        return weekDaysBetween + ((daysBetween / 7) * 5);
    }

    /**
     * Gets the number of weekend days between two dates, not including the start day
     *
     * @param start  The date after which we start counting
     * @param end  The date on which we stop counting
     * @return the number of weekend days from the day after start up to and including end, 0 if start is not before end
     */
    public static int countWeekendDaysBetween(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) return 0;
        int daysBetween = getDaysBetween(start, end);
        return daysBetween - countWeekdaysBetween(start, end);
    }

    private static int getDaysBetween(LocalDate start, LocalDate end) {
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Gets the number of weekend holidays between two dates, not including the start day
     *
     * @param start  The date after which we start counting
     * @param end  The date on which we stop counting
     * @param holidays  A list of holidays to count
     * @return the number of weekend holidays from the day after start up to and including end
     */
    public static int countWeekendHolidaysBetween(LocalDate start, LocalDate end, List<Holiday> holidays) {
        return countFilteredHolidaysBetween(start, end, holidays, date -> !isWeekday(date.getDayOfWeek()));
    }

    /**
     * Gets the number of weekday holidays between two dates, not including the start day
     *
     * @param start  The date after which we start counting
     * @param end  The date on which we stop counting
     * @param holidays  A list of holidays to count
     * @return the number of weekday holidays from the day after start up to and including end
     */
    public static int countWeekdayHolidaysBetween(LocalDate start, LocalDate end, List<Holiday> holidays) {
        return countFilteredHolidaysBetween(start, end, holidays, date -> isWeekday(date.getDayOfWeek()));
    }

    /**
     * Counts holiday between two dates but applies a filter to only count some holiday date
     * Ex. Filter for only holidays that are on weekdays
     *
     * @param start  The date after which we start counting
     * @param end  The date on which we stop counting
     * @param holidays  A list of holidays to count
     * @param filter  a lambda (LocalDate) -> boolean which decides whether a particular Holiday date should be counted,
     *                where a result of true means the date should be counted
     * @return the number of filtered holidays from the day after start up to and including end
     */
    private static int countFilteredHolidaysBetween(LocalDate start, LocalDate end, List<Holiday> holidays, HolidayFilter filter) {
        Set<LocalDate> holidayDates = new HashSet<>();
        for (Holiday holiday : holidays) {
            // a Set only allows one of each unique date, so holidays on the same day won't be double counted
            holidayDates.addAll(getHolidayDatesBetween(start, end, holiday, filter));
        }
        return holidayDates.size();
    }


    /**
     * Gets a Set of filtered holiday dates between two dates for a particular Holiday
     *
     * @param start  The date after which holidays can be added to the set
     * @param end  The date before or on which holidays can be added to the set
     * @param holiday  A Holiday whose yearly dates are being added to the set
     * @param filter  a lambda (LocalDate) -> boolean which decides whether a particular Holiday date should be added
     *                to the set, where a result of true means the date should be added
     * @return a Set of LocalDates containing filtered holiday dates between start and end
     */
    private static Set<LocalDate> getHolidayDatesBetween(LocalDate start, LocalDate end, Holiday holiday, HolidayFilter filter) {
        Set<LocalDate> holidayDates = new HashSet<>();
        for(int year = start.getYear(); year <= end.getYear(); year++) {
            LocalDate holidayDate = holiday.getDateForYear(year);
            if (holidayDate.isAfter(start) && !holidayDate.isAfter(end) && filter.filter(holidayDate)) {
                holidayDates.add(holidayDate);
            }
        }
        return holidayDates;
    }

    private static boolean isWeekday (DayOfWeek dayOfWeek) {
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }
}
