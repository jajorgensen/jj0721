package test.dates;

import demo.rental.dates.Holiday;
import org.junit.jupiter.api.Test;

import demo.rental.dates.RelativeHoliday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class RelativeHolidayTest {
    @Test
    void getDateForYear_LaborDay2021() {
        Holiday relative = new RelativeHoliday(1, DayOfWeek.MONDAY.getValue(), Month.SEPTEMBER.getValue());
        LocalDate dateFor2021 = relative.getDateForYear(2021);
        assertEquals(LocalDate.of(2021, Month.SEPTEMBER, 6), dateFor2021);
    }

    @Test
    void getDateForYear_LaborDay2022() {
        Holiday relative = new RelativeHoliday(1, DayOfWeek.MONDAY.getValue(), Month.SEPTEMBER.getValue());
        LocalDate dateFor2021 = relative.getDateForYear(2022);
        assertEquals(LocalDate.of(2022, Month.SEPTEMBER, 5), dateFor2021);
    }

    @Test
    void getDateForYear_LaborDay2025() {
        Holiday relative = new RelativeHoliday(1, DayOfWeek.MONDAY.getValue(), Month.SEPTEMBER.getValue());
        LocalDate dateFor2021 = relative.getDateForYear(2025);
        assertEquals(LocalDate.of(2025, Month.SEPTEMBER, 1), dateFor2021);
    }

    @Test
    void getDateForYear_Thanksgiving2021() {
        Holiday relative = new RelativeHoliday(4, DayOfWeek.THURSDAY.getValue(), Month.NOVEMBER.getValue());
        LocalDate dateFor2021 = relative.getDateForYear(2021);
        assertEquals(LocalDate.of(2021, Month.NOVEMBER, 25), dateFor2021);
    }

    @Test
    void getDateForYear_Thanksgiving2022() {
        Holiday relative = new RelativeHoliday(4, DayOfWeek.THURSDAY.getValue(), Month.NOVEMBER.getValue());
        LocalDate dateFor2022 = relative.getDateForYear(2022);
        assertEquals(LocalDate.of(2022, Month.NOVEMBER, 24), dateFor2022);
    }

}