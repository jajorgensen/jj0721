package demo.rental;

import java.time.LocalDate;

/**
 * Rental class is used for calling checkout to get RentalAgreements
 */
public class Rental {
    /**
     * Gets a RentalAgreement for a particular tool
     *
     * @param toolCode  unique identifying code for tool being rented
     * @param numRentalDays  number of days after checkout the tool will be rented, must be at least 1
     * @param discountPercent  discount applied to total cost, must be within range [0,100]
     * @param checkoutDate  date of checkout, charges begin the day after checkout
     * @return a new RentalAgreement based on the parameters
     * @throws Exception if numRentalDays is less than 1
     * @throws Exception if discountPercent is outside [0,100]
     */
    public static RentalAgreement checkout(String toolCode, int numRentalDays, int discountPercent,
                                           LocalDate checkoutDate) throws Exception {
        return new RentalAgreement(toolCode, numRentalDays, discountPercent, checkoutDate);
    }
}
