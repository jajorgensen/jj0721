package test.dates;

import demo.rental.dates.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HolidayLookupCSVTest {
    private static final String csvPath = "src/test/java/test/dates/HolidayLookupCSVTest.csv";
    private static final HolidayLookup holidayLookup = new HolidayLookupCSV(csvPath);

    @Test
    void testGetAll() {
        List<Holiday> holidays = holidayLookup.getAllHolidays();
        assertEquals(3, holidays.size());
        Holiday first = holidays.get(0);
        assertEquals(ClosestWeekdayHoliday.class, first.getClass());
        assertEquals(LocalDate.of(2021, Month.JULY, 5), first.getDateForYear(2021));
        Holiday second = holidays.get(1);
        assertEquals(RelativeHoliday.class, second.getClass());
        assertEquals(LocalDate.of(2021, Month.SEPTEMBER, 6), second.getDateForYear(2021));
        Holiday third = holidays.get(2);
        assertEquals(FixedHoliday.class, third.getClass());
        assertEquals(LocalDate.of(2021, Month.DECEMBER, 25), third.getDateForYear(2021));
    }

}