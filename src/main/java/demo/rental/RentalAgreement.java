package demo.rental;

import demo.rental.charge.*;
import demo.rental.dates.HolidayLookup;
import demo.rental.dates.HolidayLookupCSV;
import demo.rental.tool.ToolLookupCSV;
import demo.rental.tool.ToolLookup;
import demo.rental.tool.Tool;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The RentalAgreement class is used generate and print information about tool rentals
 *
 * Upon construction, a RentalAgreement will look up information about the particular tool being rented including
 * type, brand, charge policies. Then it will calculate charge data such as number of days charged, pre-discount price,
 * discount amount, and final price.
 *
 * After construction, a RentalAgreement can print all of its rental data to System.out.
 */
public class RentalAgreement {

    private final Tool tool;
    private final ChargePolicy chargePolicy;
    private final int numRentalDays;
    private final int numChargeDays;
    private final long preDiscountCharge;
    private final int discountPercent;
    private final long discountAmount;
    private final long finalCharge;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;

    /**
     * Constructs a RentalAgreement for a particular tool
     *
     * @param toolCode  unique identifying code for tool being rented
     * @param numRentalDays  number of days after checkout the tool will be rented, must be at least 1
     * @param discountPercent  discount applied to total cost, must be within range [0,100]
     * @param checkoutDate  date of checkout, charges begin the day after checkout
     * @throws Exception if numRentalDays is less than 1
     * @throws Exception if discountPercent is outside [0,100]
     */
    public RentalAgreement(String toolCode, int numRentalDays, int discountPercent, LocalDate checkoutDate) throws Exception {
        if (numRentalDays < 1) throw new Exception("Number of rental days must be greater than 0");
        if (discountPercent < 0 || discountPercent > 100) throw new Exception("Discount percentage must be within range [0, 100]");

        ToolLookup toolLookup = new ToolLookupCSV("csv/Tools.csv");
        ChargePolicyLookup policyLookup = new ChargePolicyLookupCSV("csv/ChargePolicies.csv");
        HolidayLookup holidayLookup = new HolidayLookupCSV("csv/Holidays.csv");

        this.numRentalDays = numRentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
        this.tool = toolLookup.getToolFromCode(toolCode);
        this.chargePolicy = policyLookup.getChargePolicyFromToolType(this.tool.type());
        this.dueDate = ChargeCalculator.calcDueDate(this.checkoutDate, this.numRentalDays);
        this.numChargeDays = ChargeCalculator.calcChargeDays(this.checkoutDate, this.dueDate, this.chargePolicy,
                holidayLookup.getAllHolidays());
        this.preDiscountCharge = ChargeCalculator.calcPreDiscountCharge(this.chargePolicy.dailyChargeCents(),
                this.numChargeDays);
        this.discountAmount = ChargeCalculator.calcDiscountAmount(this.preDiscountCharge, this.discountPercent);
        this.finalCharge = ChargeCalculator.calcFinalCharge(this.preDiscountCharge, this.discountAmount);
    }

    /**
     * Prints rental data for this RentalAgreement
     * Example:
     * Tool code: LADW
     * Tool type: Ladder
     * Tool brand: Werner
     * Rental days: 3
     * Check out date: 07/02/20
     * Due date: 07/05/20
     * Daily rental charge: $1.99
     * Charge days: 2
     * Pre-discount charge: $3.98
     * Discount percent: 10%
     * Discount amount: $0.40
     * Final charge: $3.58
     */
    public void print() {
        System.out.println("Tool code: " + tool.code());
        System.out.println("Tool type: " + tool.type());
        System.out.println("Tool brand: " + tool.brand());
        System.out.println("Rental days: " + numRentalDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        System.out.println("Check out date: " + checkoutDate.format(formatter));
        System.out.println("Due date: " + dueDate.format(formatter));
        System.out.println("Daily rental charge: " + printableCurrency(chargePolicy.dailyChargeCents()));
        System.out.println("Charge days: " + numChargeDays);
        System.out.println("Pre-discount charge: " + printableCurrency(preDiscountCharge));
        System.out.println("Discount percent: " + discountPercent + "%");
        System.out.println("Discount amount: " + printableCurrency(discountAmount));
        System.out.println("Final charge: " + printableCurrency(finalCharge));
    }

    private static String printableCurrency(long currencyCents) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setGroupingUsed(true);
        double currencyDollars = currencyCents / 100.0;
        return currencyFormat.format(currencyDollars);
    }

    public Tool getTool() {
        return tool;
    }

    public ChargePolicy getChargePolicy() {
        return chargePolicy;
    }

    public int getNumRentalDays() {
        return numRentalDays;
    }

    public int getNumChargeDays() {
        return numChargeDays;
    }

    public long getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public long getFinalCharge() {
        return finalCharge;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
