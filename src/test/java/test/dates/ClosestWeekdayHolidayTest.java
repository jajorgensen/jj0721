package test.dates;

import demo.rental.dates.Holiday;
import demo.rental.dates.ClosestWeekdayHoliday;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ClosestWeekdayHolidayTest {
    @Test
    void getDateForYear_movedToMondayIn2021() {
        Holiday holiday = new ClosestWeekdayHoliday(Month.JULY.getValue(), 4);
        LocalDate dateFor2021 = holiday.getDateForYear(2021);
        assertEquals(LocalDate.of(2021, Month.JULY, 5), dateFor2021);
        assertSame(dateFor2021.getDayOfWeek(), DayOfWeek.MONDAY);
    }

    @Test
    void getDateForYear_movedToFridayIn2020() {
        Holiday holiday = new ClosestWeekdayHoliday(Month.JULY.getValue(), 4);
        LocalDate dateFor2020 = holiday.getDateForYear(2020);
        assertEquals(LocalDate.of(2020, Month.JULY, 3), dateFor2020);
        assertSame(dateFor2020.getDayOfWeek(), DayOfWeek.FRIDAY);
    }

    @Test
    void getDateForYear_notMovedIn2023() {
        Holiday holiday = new ClosestWeekdayHoliday(Month.JULY.getValue(), 4);
        LocalDate dateFor2023 = holiday.getDateForYear(2023);
        assertEquals(LocalDate.of(2023, Month.JULY, 4), dateFor2023);
    }

}