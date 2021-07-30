package test;

import demo.rental.Rental;
import demo.rental.RentalAgreement;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private final PrintStream origOut = System.out;

    @BeforeEach
    public void setOutStream() {
        System.setOut(new PrintStream(outStream));
    }

    @AfterEach
    public void resetSystemOut() {
        System.setOut(origOut);
    }

    @Test
    void checkout_Test1() {
        assertThrows(Exception.class, () -> Rental.checkout("JAKR", 5, 101,
                LocalDate.of(2015, 9, 3)), "Discount percentage must be within range [0, 100]");
    }

    @Test
    void checkout_Test2() throws Exception {
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        RentalAgreement agreement = Rental.checkout("LADW", 3, 10, checkoutDate);
        assertAgreementEquals(agreement, checkoutDate, checkoutDate.plusDays(3), "LADW","Werner",
                "Ladder", 199, 3, 2, 398, 10,
                40,358);
        agreement.print();
        assertEquals("""
                Tool code: LADW
                Tool type: Ladder
                Tool brand: Werner
                Rental days: 3
                Check out date: 07/02/20
                Due date: 07/05/20
                Daily rental charge: $1.99
                Charge days: 2
                Pre-discount charge: $3.98
                Discount percent: 10%
                Discount amount: $0.40
                Final charge: $3.58
                """, outStream.toString().replace("\r", ""));
    }

    @Test
    void checkout_Test3() throws Exception {
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        RentalAgreement agreement = Rental.checkout("CHNS", 5, 25, checkoutDate);
        assertAgreementEquals(agreement, checkoutDate, checkoutDate.plusDays(5), "CHNS","Stihl",
                "Chainsaw", 149,5,3,447, 25,
                112,335);
        agreement.print();
        assertEquals("""
                Tool code: CHNS
                Tool type: Chainsaw
                Tool brand: Stihl
                Rental days: 5
                Check out date: 07/02/15
                Due date: 07/07/15
                Daily rental charge: $1.49
                Charge days: 3
                Pre-discount charge: $4.47
                Discount percent: 25%
                Discount amount: $1.12
                Final charge: $3.35
                """, outStream.toString().replace("\r", ""));
    }

    @Test
    void checkout_Test4() throws Exception {
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        RentalAgreement agreement = Rental.checkout("JAKD", 6, 0, checkoutDate);
        assertAgreementEquals(agreement, checkoutDate, checkoutDate.plusDays(6), "JAKD","DeWalt",
                "Jackhammer", 299,6,3,897, 0,
                0,897);
        agreement.print();
        assertEquals("""
                Tool code: JAKD
                Tool type: Jackhammer
                Tool brand: DeWalt
                Rental days: 6
                Check out date: 09/03/15
                Due date: 09/09/15
                Daily rental charge: $2.99
                Charge days: 3
                Pre-discount charge: $8.97
                Discount percent: 0%
                Discount amount: $0.00
                Final charge: $8.97
                """, outStream.toString().replace("\r", ""));
    }

    @Test
    void checkout_Test5() throws Exception {
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        RentalAgreement agreement = Rental.checkout("JAKR", 9, 0, checkoutDate);
        assertAgreementEquals(agreement, checkoutDate, checkoutDate.plusDays(9), "JAKR","Ridgid",
                "Jackhammer", 299,9,5,1495, 0,
                0,1495);
        agreement.print();
        assertEquals("""
                Tool code: JAKR
                Tool type: Jackhammer
                Tool brand: Ridgid
                Rental days: 9
                Check out date: 07/02/15
                Due date: 07/11/15
                Daily rental charge: $2.99
                Charge days: 5
                Pre-discount charge: $14.95
                Discount percent: 0%
                Discount amount: $0.00
                Final charge: $14.95
                """, outStream.toString().replace("\r", ""));
    }

    @Test
    void checkout_Test6() throws Exception {
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        RentalAgreement agreement = Rental.checkout("JAKR", 4, 50, checkoutDate);
        assertAgreementEquals(agreement, checkoutDate, checkoutDate.plusDays(4), "JAKR","Ridgid",
                "Jackhammer", 299,4,1,299, 50,
                150,149);
        agreement.print();
        assertEquals("""
                Tool code: JAKR
                Tool type: Jackhammer
                Tool brand: Ridgid
                Rental days: 4
                Check out date: 07/02/20
                Due date: 07/06/20
                Daily rental charge: $2.99
                Charge days: 1
                Pre-discount charge: $2.99
                Discount percent: 50%
                Discount amount: $1.50
                Final charge: $1.49
                """, outStream.toString().replace("\r", ""));
    }

    private void assertAgreementEquals(RentalAgreement agreement, LocalDate checkoutDate, LocalDate dueDate,
                                       String code, String brand, String type, long dailyCharge,
                                       int numRentalDays, int numChargedDays, long preDiscountCharge,
                                       int discountPercent, long discountAmount, long finalCharge) {
        assertEquals(checkoutDate, agreement.getCheckoutDate());
        assertEquals(dueDate, agreement.getDueDate());
        assertEquals(code, agreement.getTool().code());
        assertEquals(brand, agreement.getTool().brand());
        assertEquals(type, agreement.getTool().type());
        assertEquals(numRentalDays, agreement.getNumRentalDays());
        assertEquals(numChargedDays, agreement.getNumChargeDays());
        assertEquals(dailyCharge, agreement.getChargePolicy().dailyChargeCents());
        assertEquals(preDiscountCharge, agreement.getPreDiscountCharge());
        assertEquals(discountPercent, agreement.getDiscountPercent());
        assertEquals(discountAmount, agreement.getDiscountAmount());
        assertEquals(finalCharge, agreement.getFinalCharge());
    }
}