package demo.rental.tool;

/**
 * The ToolLookup interface is used for getting tools.
 */
public interface ToolLookup {
    /**
     * Requests a Tool object based on a tool code
     *
     * @param toolCode  the unique code for the tool
     * @return a Tool with a matching tool code. May return null if no matching Tool is found
     */
    Tool getToolFromCode(String toolCode);
}
