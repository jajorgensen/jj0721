package demo.rental.tool;

/**
 * The ToolLookupSwitch class is a ToolLookup implementation which uses a simple switch statement to correlate Tools
 * with tool codes. It exists only for its simplicity and is deprecated now that I have a more extensible solution
 */
public class ToolLookupSwitch implements ToolLookup {
    /**
     * @param toolCode the unique code for the tool
     * @return a Tool matching the given tool code, or null if the code is not found
     */
    public Tool getToolFromCode(String toolCode) {
        return switch (toolCode) {
            case "LADW" -> new Tool("Ladder", "Werner", toolCode);
            case "CHNS" -> new Tool("Chainsaw", "Stihl", toolCode);
            case "JAKR" -> new Tool("Jackhammer", "Ridgid", toolCode);
            case "JAKD" -> new Tool("Jackhammer", "DeWalt", toolCode);
            default -> null;
        };
    }
}
