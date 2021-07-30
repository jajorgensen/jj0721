package demo.rental.charge;

/**
 * An implementation of ChargePolicyLookup which uses a switch to determine which ChargePolicy to return
 * Is deprecated now that I have a more extensible way to get ChargePolicies
 */
public class ChargePolicyLookupSwitch implements ChargePolicyLookup {

    public ChargePolicy getChargePolicyFromToolType(String type) {
        return switch (type) {
            case "Ladder" -> new ChargePolicy(199, true, true, false);
            case "Chainsaw" -> new ChargePolicy(149, true, false, true);
            case "Jackhammer" -> new ChargePolicy(299, true, false, false);
            default -> throw new IllegalArgumentException("Tool type not found");
        };
    }
}
