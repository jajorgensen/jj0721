package demo.rental.charge;

/**
 * An interface for getting ChargePolicies
 */
public interface ChargePolicyLookup {
    /**
     * Gets a ChargePolicy corresponding to the given tool type
     *
     * @param type  A string identifying the tool type
     * @return a charge policy matching the given tool type, may return null if no match is found
     */
    ChargePolicy getChargePolicyFromToolType(String type);
}
