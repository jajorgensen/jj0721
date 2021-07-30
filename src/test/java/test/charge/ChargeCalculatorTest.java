package test.charge;

import demo.rental.charge.ChargeCalculator;
import demo.rental.charge.ChargePolicy;
import demo.rental.dates.Holiday;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChargeCalculatorTest {
    @Mock private static Holiday mockedWeekendHoliday;
    @Mock private static Holiday mockedWeekdayHoliday;
    private static List<Holiday> holidays;

    @BeforeAll
    static void setUpMocks() {
        mockedWeekdayHoliday = mock(Holiday.class);
        when(mockedWeekdayHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 28));
        mockedWeekendHoliday = mock(Holiday.class);
        when(mockedWeekendHoliday.getDateForYear(2021)).thenReturn(LocalDate.of(2021, Month.JULY, 24));
        holidays = new ArrayList<>(List.of(mockedWeekdayHoliday, mockedWeekendHoliday));
    }

    @Test
    void calcDueDate_add1Day() {
        LocalDate dueDate = ChargeCalculator.calcDueDate(LocalDate.of(2021, Month.JULY, 28), 1);
        assertEquals(LocalDate.of(2021, Month.JULY, 29), dueDate);
    }

    @Test
    void calcDueDate_add10Days() {
        LocalDate dueDate = ChargeCalculator.calcDueDate(LocalDate.of(2021, Month.JULY, 28), 10);
        assertEquals(LocalDate.of(2021, Month.AUGUST, 7), dueDate);
    }

    @Test
    void calcChargeDays_JustWeekdays() {
        LocalDate start = LocalDate.of(2021, Month.JULY, 1);
        LocalDate end = LocalDate.of(2021, Month.AUGUST, 1);

        ChargePolicy policy = new ChargePolicy(1, true, false, false);
        assertEquals(20, ChargeCalculator.calcChargeDays(start, end, policy, holidays));
    }

    @Test
    void calcChargeDays_WeekdaysAndHolidays() {
        LocalDate start = LocalDate.of(2021, Month.JULY, 1);
        LocalDate end = LocalDate.of(2021, Month.AUGUST, 1);

        ChargePolicy policy = new ChargePolicy(1, true, false, true);
        assertEquals(21, ChargeCalculator.calcChargeDays(start, end, policy, holidays));
    }

    @Test
    void calcChargeDays_JustWeekends() {
        LocalDate start = LocalDate.of(2021, Month.JULY, 1);
        LocalDate end = LocalDate.of(2021, Month.AUGUST, 1);

        ChargePolicy policy = new ChargePolicy(1, false, true, false);
        assertEquals(9, ChargeCalculator.calcChargeDays(start, end, policy, holidays));
    }

    @Test
    void calcChargeDays_WeekendsAndHolidays() {
        LocalDate start = LocalDate.of(2021, Month.JULY, 1);
        LocalDate end = LocalDate.of(2021, Month.AUGUST, 1);

        ChargePolicy policy = new ChargePolicy(1, false, true, true);
        assertEquals(10, ChargeCalculator.calcChargeDays(start, end, policy, holidays));
    }

    @Test
    void calcChargeDays_JustHolidays() {
        LocalDate start = LocalDate.of(2021, Month.JULY, 1);
        LocalDate end = LocalDate.of(2021, Month.AUGUST, 1);

        ChargePolicy policy = new ChargePolicy(1, false, false, true);
        assertEquals(0, ChargeCalculator.calcChargeDays(start, end, policy, holidays));
    }

    @Test
    void calcChargeDays_AllDays() {
        LocalDate start = LocalDate.of(2021, Month.JULY, 1);
        LocalDate end = LocalDate.of(2021, Month.AUGUST, 1);

        ChargePolicy policy = new ChargePolicy(1, true, true, true);
        assertEquals(31, ChargeCalculator.calcChargeDays(start, end, policy, holidays));
    }

    @Test
    void calcPreDiscountCharge_0daysOfCharge() {
        assertEquals(0, ChargeCalculator.calcPreDiscountCharge(100, 0));
    }

    @Test
    void calcPreDiscountCharge_1daysOfCharge() {
        assertEquals(100, ChargeCalculator.calcPreDiscountCharge(100, 1));
    }

    @Test
    void calcPreDiscountCharge_5daysOfCharge() {
        assertEquals(500, ChargeCalculator.calcPreDiscountCharge(100, 5));
    }

    @Test
    void calcDiscountAmount_0PercentDiscount() {
        assertEquals(0, ChargeCalculator.calcDiscountAmount(100, 0));
    }

    @Test
    void calcDiscountAmount_1PercentDiscount() {
        assertEquals(1, ChargeCalculator.calcDiscountAmount(100, 1));
    }

    @Test
    void calcDiscountAmount_10PercentDiscount() {
        assertEquals(10, ChargeCalculator.calcDiscountAmount(100, 10));
    }

    @Test
    void calcDiscountAmount_100PercentDiscount() {
        assertEquals(100, ChargeCalculator.calcDiscountAmount(100, 100));
    }

    @Test
    void calcDiscountAmount_1point5RoundsTo2() {
        assertEquals(2, ChargeCalculator.calcDiscountAmount(50, 3));
    }

    @Test
    void calcDiscountAmount_point75RoundsTo1() {
        assertEquals(1, ChargeCalculator.calcDiscountAmount(50, 1));
    }

    @Test
    void calcDiscountAmount_1point2RoundsTo1() {
        assertEquals(1, ChargeCalculator.calcDiscountAmount(30, 4));
    }

    @Test
    void calcFinalCharge_0discount() {
        assertEquals(100, ChargeCalculator.calcFinalCharge(100, 0));
    }

    @Test
    void calcFinalCharge_1discount() {
        assertEquals(99, ChargeCalculator.calcFinalCharge(100, 1));
    }

    @Test
    void calcFinalCharge_10discount() {
        assertEquals(90, ChargeCalculator.calcFinalCharge(100, 10));
    }

    @Test
    void calcFinalCharge_FullDiscount() {
        assertEquals(0, ChargeCalculator.calcFinalCharge(100, 100));
    }
}