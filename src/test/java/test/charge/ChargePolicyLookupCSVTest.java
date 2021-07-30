package test.charge;

import demo.rental.charge.ChargePolicy;
import demo.rental.charge.ChargePolicyLookupCSV;
import demo.rental.charge.ChargePolicyLookup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargePolicyLookupCSVTest {
    private static final String csvPath = "src/test/java/test/charge/ChargePolicyLookupCSVTest.csv";
    private static final ChargePolicyLookup policyLookup = new ChargePolicyLookupCSV(csvPath);

    @Test
    public void loadTest_Ladder() {
        ChargePolicy policy = policyLookup.getChargePolicyFromToolType("Ladder");
        assertPolicyNotNullAndEquals(policy,199, true, false);
    }

    @Test
    public void loadTest_Chainsaw() {
        ChargePolicy policy = policyLookup.getChargePolicyFromToolType("Chainsaw");
        assertPolicyNotNullAndEquals(policy,149, false, true);
    }

    @Test
    public void loadTest_Jackhammer() {
        ChargePolicy policy = policyLookup.getChargePolicyFromToolType("Jackhammer");
        assertPolicyNotNullAndEquals(policy,299, false, false);
    }

    private void assertPolicyNotNullAndEquals(ChargePolicy policy, long dailyChargeCents, boolean isChargedWeekends, boolean isChargedHolidays) {
        assertNotNull(policy);
        assertEquals(dailyChargeCents, policy.dailyChargeCents());
        assertTrue(policy.isChargedWeekdays());
        assertEquals(isChargedWeekends, policy.isChargedWeekends());
        assertEquals(isChargedHolidays, policy.isChargedHolidays());
    }

}