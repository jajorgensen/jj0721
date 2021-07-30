package demo.rental.charge;

/**
 * A record for storing information about how to charge a rental
 */
public record ChargePolicy(long dailyChargeCents, boolean isChargedWeekdays, boolean isChargedWeekends,
                           boolean isChargedHolidays) {
    /**
     * Constructs an instance of ChargePolicy
     *
     * @param dailyChargeCents  the number of cents charged each day, must not be negative
     * @param isChargedWeekdays  boolean indicating if charge is applied on weekdays
     * @param isChargedWeekends  boolean indicating if charge is applied on weekends
     * @param isChargedHolidays  boolean indicating if charge is applied on holidays
     */
    public ChargePolicy {
        if (dailyChargeCents < 0)
            throw new IllegalArgumentException("daily charge less than zero: " + dailyChargeCents);
    }
}
