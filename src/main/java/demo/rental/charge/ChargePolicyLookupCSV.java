package demo.rental.charge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Am implementation of ChargePolicyLookup which generates ChargePolicies from a given .csv file
 */
public class ChargePolicyLookupCSV implements ChargePolicyLookup {
    private final Map<String, ChargePolicy> policies = new HashMap<>();

    /**
     * Constructs a ChargePolicyLookupCSV objects and loads ChargePolicy data from the given .csv file
     *
     * @param filePath  the path to a .csv file containing ChargePolicy data
     */
    public ChargePolicyLookupCSV(String filePath) {
        loadPoliciesFromCSV(filePath);
    }

    private void loadPoliciesFromCSV(String filePath) {
        try {
            Scanner scan = new Scanner(new File(filePath));
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                // Expect lines to be of the form "ToolType,dailyChargeCents,[yes/no],[yes/no],[yes/no]"
                String[] lineSplit = line.split(",", 5);
                ChargePolicy policy = new ChargePolicy(Long.parseLong(lineSplit[1]), isYes(lineSplit[2]),
                        isYes(lineSplit[3]), isYes(lineSplit[4]));
                policies.put(lineSplit[0], policy);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException.getMessage());
        }
    }

    private static boolean isYes(String s) {
        return s.equalsIgnoreCase("yes");
    }

    /**
     * Returns a ChargePolicy matching the tool type
     *
     * @param type A string identifying the tool type
     * @return a ChargePolicy matching the given tool type, or null if a matching ChargePolicy couldn't be found
     */
    @Override
    public ChargePolicy getChargePolicyFromToolType(String type) {
        return policies.get(type);
    }
}
