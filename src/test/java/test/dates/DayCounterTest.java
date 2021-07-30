package test.dates;

import demo.rental.dates.Holiday;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static demo.rental.dates.DayCounter.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DayCounterTest {
    @Mock private static Holiday mockedHoliday;
    @Mock private static Holiday otherMockedHoliday;

    @BeforeAll
    static void setUpMockHolidays() {
        mockedHoliday = mock(Holiday.class);
        otherMockedHoliday = mock(Holiday.class);
    }

    private static void assertWeekdaysBetween(LocalDate start, LocalDate end, DayOfWeek expectedStartDay,
                                              DayOfWeek expectedEndDay, long expectedWeekdaysBetween) {
        assertSame(expectedStartDay, start.getDayOfWeek());
        assertSame(expectedEndDay, end.getDayOfWeek());
        assertEquals(expectedWeekdaysBetween, countWeekdaysBetween(start, end));
    }

    @Test
    void countWeekdays_return0IfStartBeforeEnd() {
        LocalDate wednesday = LocalDate.of(2021, Month.JULY, 28);
        LocalDate tuesday = wednesday.minusDays(1);
        assertWeekdaysBetween(wednesday, tuesday, DayOfWeek.WEDNESDAY, DayOfWeek.TUESDAY, 0);
    }

    @Test
    void countWeekdays_return0IfSameDay() {
        LocalDate wednesday = LocalDate.of(2021, Month.JULY, 28);
        LocalDate alsoWednesday = LocalDate.of(2021, Month.JULY, 28);
        assertWeekdaysBetween(wednesday, alsoWednesday, DayOfWeek.WEDNESDAY, DayOfWeek.WEDNESDAY, 0);
    }


    @Test
    void countWeekdays_countWednesdayToThursday() {
        LocalDate wednesday = LocalDate.of(2021, Month.JULY, 28);
        LocalDate thursday = wednesday.plusDays(1);
        assertWeekdaysBetween(wednesday, thursday, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, 1);
    }

    @Test
    void countWeekdays_return1ForSundayToMonday() {
        LocalDate sunday = LocalDate.of(2021, Month.AUGUST, 1);
        LocalDate monday = sunday.plusDays(1);
        assertWeekdaysBetween(sunday, monday, DayOfWeek.SUNDAY, DayOfWeek.MONDAY, 1);
    }

    @Test
    void countWeekdays_return0ForFridayToSaturday() {
        LocalDate friday = LocalDate.of(2021, Month.JULY, 30);
        LocalDate saturday = friday.plusDays(1);
        assertWeekdaysBetween(friday, saturday, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, 0);
    }

    @Test
    void countWeekdays_return0IfStartAndEndAreWithinSameWeekend() {
        LocalDate saturday = LocalDate.of(2021, Month.JULY, 31);
        LocalDate sunday = saturday.plusDays(1);
        assertWeekdaysBetween(saturday, sunday, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, 0);
    }

    @Test
    void countWeekdays_countSundayToFriday() {
        LocalDate sunday = LocalDate.of(2021, Month.AUGUST, 1);
        LocalDate friday = sunday.plusDays(5);
        assertWeekdaysBetween(sunday, friday, DayOfWeek.SUNDAY, DayOfWeek.FRIDAY, 5);
    }

    @Test
    void countWeekdays_countSaturdayToNextSaturday() {
        LocalDate saturday = LocalDate.of(2021, Month.JULY, 31);
        LocalDate nextSaturday = saturday.plusDays(7);
        assertWeekdaysBetween(saturday, nextSaturday, DayOfWeek.SATURDAY, DayOfWeek.SATURDAY, 5);
    }

    @Test
    void countWeekdays_countSeveralMonths() {
        LocalDate today = LocalDate.of(2021, Month.JULY, 28);
        LocalDate monthsLater = LocalDate.of(2021, Month.OCTOBER, 19);
        assertWeekdaysBetween(today, monthsLater, DayOfWeek.WEDNESDAY, DayOfWeek.TUESDAY, 59);
    }

    private static void assertWeekendsBetween(LocalDate start, LocalDate end, DayOfWeek expectedStartDay,
                                              DayOfWeek expectedEndDay, long expectedWeekendsBetween) {
        assertSame(expectedStartDay, start.getDayOfWeek());
        assertSame(expectedEndDay, end.getDayOfWeek());
        assertEquals(expectedWeekendsBetween, countWeekendDaysBetween(start, end));
    }

    @Test
    void countWeekends_return0IfStartAfterEnd() {
        LocalDate saturday = LocalDate.of(2021, Month.JULY, 31);
        LocalDate sunday = saturday.plusDays(1);
        assertWeekendsBetween(sunday, saturday, DayOfWeek.SUNDAY, DayOfWeek.SATURDAY, 0);
    }

    @Test
    void countWeekends_return0IfSameDay() {
        LocalDate saturday = LocalDate.of(2021, Month.JULY, 31);
        LocalDate alsoSaturday = LocalDate.of(2021, Month.JULY, 31);
        assertWeekendsBetween(alsoSaturday, saturday, DayOfWeek.SATURDAY, DayOfWeek.SATURDAY, 0);
    }

    @Test
    void countWeekends_countSaturdayToSunday() {
        LocalDate saturday = LocalDate.of(2021, Month.JULY, 31);
        LocalDate sunday = saturday.plusDays(1);
        assertWeekendsBetween(saturday, sunday, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, 1);
    }

    @Test
    void countWeekends_countFridayToSaturday() {
        LocalDate friday = LocalDate.of(2021, Month.JULY, 30);
        LocalDate saturday = friday.plusDays(1);
        assertWeekendsBetween(friday, saturday, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, 1);
    }

    @Test
    void countWeekends_countSundayToMonday() {
        LocalDate sunday = LocalDate.of(2021, Month.AUGUST, 1);
        LocalDate monday = sunday.plusDays(1);
        assertWeekendsBetween(sunday, monday, DayOfWeek.SUNDAY, DayOfWeek.MONDAY, 0);
    }

    @Test
    void countWeekends_countAcrossWeeks() {
        LocalDate wednesday = LocalDate.of(2021, Month.JULY, 28);
        LocalDate nextTuesday = wednesday.plusDays(6);
        assertWeekendsBetween(wednesday, nextTuesday, DayOfWeek.WEDNESDAY, DayOfWeek.TUESDAY, 2);
    }

    @Test
    void countWeekends_countSeveralMonths() {
        LocalDate saturday = LocalDate.of(2021, Month.JULY, 31);
        LocalDate monthsLater = LocalDate.of(2021, Month.OCTOBER, 19);
        assertWeekendsBetween(saturday, monthsLater, DayOfWeek.SATURDAY, DayOfWeek.TUESDAY, 23);
    }

    @Test
    void countWeekendHolidays_weekendHolidayInRange() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july3rd = LocalDate.of(2021, Month.JULY, 3);
        LocalDate july5th = LocalDate.of(2021, Month.JULY, 5);
        assertEquals(1, countWeekendHolidaysBetween(july3rd, july5th, mockHolidays));
    }

    @Test
    void countWeekendHolidays_weekdayHolidayInRange() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 5));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july3rd = LocalDate.of(2021, Month.JULY, 3);
        LocalDate july6th = LocalDate.of(2021, Month.JULY, 6);
        assertEquals(0, countWeekendHolidaysBetween(july3rd, july6th, mockHolidays));
    }

    @Test
    void countWeekdayHolidays_weekendHolidayInRange() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july3rd = LocalDate.of(2021, Month.JULY, 3);
        LocalDate july5th = LocalDate.of(2021, Month.JULY, 5);
        assertEquals(0, countWeekdayHolidaysBetween(july3rd, july5th, mockHolidays));
    }

    @Test
    void countWeekdayHolidays_weekdayHolidayInRange() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 5));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july3rd = LocalDate.of(2021, Month.JULY, 3);
        LocalDate july6th = LocalDate.of(2021, Month.JULY, 6);
        assertEquals(1, countWeekdayHolidaysBetween(july3rd, july6th, mockHolidays));
    }

    @Test
    void countHolidays_holidayOutOfRange() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july1st = LocalDate.of(2021, Month.JULY, 1);
        LocalDate july3rd = LocalDate.of(2021, Month.JULY, 3);
        assertEquals(0, countWeekendHolidaysBetween(july1st, july3rd, mockHolidays));
    }

    @Test
    void countHolidays_holidayOnLastDay() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july1st = LocalDate.of(2021, Month.JULY, 1);
        LocalDate july4th = LocalDate.of(2021, Month.JULY, 4);
        assertEquals(1, countWeekendHolidaysBetween(july1st, july4th, mockHolidays));
    }

    @Test
    void countHolidays_holidayOnFirstDay() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july4th = LocalDate.of(2021, Month.JULY, 4);
        LocalDate july5th = LocalDate.of(2021, Month.JULY, 5);
        assertEquals(0, countWeekendHolidaysBetween(july4th, july5th, mockHolidays));
    }

    @Test
    void countHolidays_count1HolidayAcross2Years() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        when(mockedHoliday.getDateForYear(2022)).thenReturn(LocalDate.of(2022, Month.JULY, 5));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july1st2021 = LocalDate.of(2021, Month.JULY, 1);
        LocalDate feb1st2022 = LocalDate.of(2022, Month.FEBRUARY, 1);
        assertEquals(1, countWeekendHolidaysBetween(july1st2021, feb1st2022, mockHolidays));
    }

    @Test
    void countHolidays_count0HolidaysAcross2Years() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        when(mockedHoliday.getDateForYear(2022)).thenReturn(LocalDate.of(2022, Month.JULY, 5));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate aug1st2021 = LocalDate.of(2021, Month.AUGUST, 1);
        LocalDate feb1st2022 = LocalDate.of(2022, Month.FEBRUARY, 1);
        assertEquals(0, countWeekendHolidaysBetween(aug1st2021, feb1st2022, mockHolidays));
    }

    @Test
    void countWeekendHolidays_countHolidaysAcrossManyYears() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        when(mockedHoliday.getDateForYear(2022)).thenReturn(LocalDate.of(2022, Month.JULY, 4));
        when(mockedHoliday.getDateForYear(2023)).thenReturn(LocalDate.of(2023, Month.JULY, 4));
        when(mockedHoliday.getDateForYear(2024)).thenReturn(LocalDate.of(2024, Month.JULY, 4));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july1st2021 = LocalDate.of(2021, Month.JULY, 1);
        LocalDate aug1st2024 = LocalDate.of(2024, Month.AUGUST, 1);
        assertEquals(1, countWeekendHolidaysBetween(july1st2021, aug1st2024, mockHolidays));
    }

    @Test
    void countWeekdayHolidays_countHolidaysAcrossManyYears() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        when(mockedHoliday.getDateForYear(2022)).thenReturn(LocalDate.of(2022, Month.JULY, 4));
        when(mockedHoliday.getDateForYear(2023)).thenReturn(LocalDate.of(2023, Month.JULY, 4));
        when(mockedHoliday.getDateForYear(2024)).thenReturn(LocalDate.of(2024, Month.JULY, 4));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday));

        LocalDate july1st2021 = LocalDate.of(2021, Month.JULY, 1);
        LocalDate aug1st2024 = LocalDate.of(2024, Month.AUGUST, 1);
        assertEquals(3, countWeekdayHolidaysBetween(july1st2021, aug1st2024, mockHolidays));
    }

    @Test
    void countHolidays_countMultipleHolidaysThatOverlap() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        when(otherMockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday, otherMockedHoliday));

        LocalDate july1st2021 = LocalDate.of(2021, Month.JULY, 1);
        LocalDate aug1st2021 = LocalDate.of(2021, Month.AUGUST, 1);
        assertEquals(1, countWeekendHolidaysBetween(july1st2021, aug1st2021, mockHolidays));
    }

    @Test
    void countHolidays_countMultipleSeparate() {
        when(mockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 4));
        when(otherMockedHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 3));
        List<Holiday> mockHolidays = new ArrayList<>(List.of(mockedHoliday, otherMockedHoliday));

        LocalDate july1st2021 = LocalDate.of(2021, Month.JULY, 1);
        LocalDate aug1st2021 = LocalDate.of(2021, Month.AUGUST, 1);
        assertEquals(2, countWeekendHolidaysBetween(july1st2021, aug1st2021, mockHolidays));
    }
}