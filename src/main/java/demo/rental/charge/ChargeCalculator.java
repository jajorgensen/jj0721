package demo.rental.charge;

import demo.rental.dates.DayCounter;
import demo.rental.dates.Holiday;

import java.time.LocalDate;
import java.util.List;

/**
 * A class for calculating charge data for rentals
 */
public class ChargeCalculator {
    /**
     * Calculates the due date for a rental
     *
     * @param checkoutDate  the checkout date for the rental
     * @param numRentalDays  the number of consecutive days the tool will be rented
     * @return  A LocalDate containing the date of the rental's due date
     */
    public static LocalDate calcDueDate(LocalDate checkoutDate, int numRentalDays) {
        return checkoutDate.plusDays(numRentalDays);
    }

    /**
     * Calculates the number of days that will be charged for a rental
     *
     * @param checkoutDate  the checkout date for the rental
     * @param dueDate  the due date for the rental
     * @param chargePolicy  the ChargePolicy for the tool being rented
     * @param holidays  List of holidays which may affect charged days
     * @return the number of days after checkoutDate up to dueDate, for which the rental will be charged
     */
    public static int calcChargeDays(LocalDate checkoutDate, LocalDate dueDate, ChargePolicy chargePolicy, List<Holiday> holidays) {
        int numChargedDays = 0;
        if (chargePolicy.isChargedWeekdays()) {
            numChargedDays += calcChargedWeekdays(checkoutDate, dueDate, chargePolicy, holidays);
        }
        if (chargePolicy.isChargedWeekends()) {
            numChargedDays += calcChargedWeekends(checkoutDate, dueDate, chargePolicy, holidays);
        }
        return numChargedDays;
    }

    /**
     * Calculates the number of weekend days that will be charged for a rental
     * Assumes that weekends will be charged, regardless of charge policy
     *
     * @param checkoutDate  the checkout date for the rental
     * @param dueDate  the due date for the rental
     * @param chargePolicy  the ChargePolicy for the tool being rented
     * @param holidays  List of holidays which may affect charged days
     * @return the number of weekend days after checkoutDate up to dueDate, for which the rental will be charged
     */
    private static int calcChargedWeekends(LocalDate checkoutDate, LocalDate dueDate, ChargePolicy chargePolicy, List<Holiday> holidays) {
        int numChargedWeekends = DayCounter.countWeekendDaysBetween(checkoutDate, dueDate);
        if (!chargePolicy.isChargedHolidays()) {
            numChargedWeekends -= DayCounter.countWeekendHolidaysBetween(checkoutDate, dueDate, holidays);
        }
        return numChargedWeekends;
    }

    /**
     * Calculates the number of weekdays that will be charged for a rental
     * Assumes that weekdays will be charged, regardless of charge policy
     *
     * @param checkoutDate  the checkout date for the rental
     * @param dueDate  the due date for the rental
     * @param chargePolicy  the ChargePolicy for the tool being rented
     * @param holidays  List of holidays which may affect charged days
     * @return the number of weekdays after checkoutDate up to dueDate, for which the rental will be charged
     */
    private static int calcChargedWeekdays(LocalDate checkoutDate, LocalDate dueDate, ChargePolicy chargePolicy, List<Holiday> holidays) {
        int numChargedWeekdays = DayCounter.countWeekdaysBetween(checkoutDate, dueDate);
        if (!chargePolicy.isChargedHolidays()) {
            numChargedWeekdays -= DayCounter.countWeekdayHolidaysBetween(checkoutDate, dueDate, holidays);
        }
        return numChargedWeekdays;
    }

    /**
     * Calculates the pre-discount charge for a rental
     *
     * @param dailyChargeCents  the number of cents charged per day
     * @param numChargeDays  the number of days that will be charged
     * @return the number of cents that will be charged before a discount is applied
     */
    public static long calcPreDiscountCharge(long dailyChargeCents, int numChargeDays) {
        return dailyChargeCents * numChargeDays;
    }

    /**
     * Calculates the discount that will be applied to the rental
     *
     * @param preDiscountCharge  the number of cents that will be charged before a discount is applied
     * @param discountPercent  the percent as a whole number between 0 and 100 of discount to apply
     * @return the number of cents that will be deducted from the preDiscountCharge due to the discount
     */
    public static long calcDiscountAmount(long preDiscountCharge, int discountPercent) {
        double discountAsDecimal = discountPercent/100.0;
        double discountAmount = preDiscountCharge * discountAsDecimal;
        return Math.round(discountAmount);
    }

    /**
     * Calculates the final charge for the rental
     *
     * @param preDiscountCharge  the number of cents that will be charged before a discount is applied
     * @param discountAmount  the number of cents that will be deducted from the preDiscountCharge due to the discount
     * @return the number of cents that will be charged after the discount is applied
     */
    public static long calcFinalCharge(long preDiscountCharge, long discountAmount) {
        return preDiscountCharge - discountAmount;
    }
}
