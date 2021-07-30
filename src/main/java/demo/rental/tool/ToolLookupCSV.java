package demo.rental.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The ToolLookupCSV class is an implementation of the ToolLookup interface.
 *
 * It generates Tool objects based on data in a given .csv file
 */
public class ToolLookupCSV implements ToolLookup {
    private final Map<String, Tool> tools = new HashMap<>();

    /**
     * Constructs a ToolLookupCSV instance and loads Tools based on the give .csv file
     *
     * @param filePath  the path to a .csv file containing Tool data
     */
    public ToolLookupCSV(String filePath) {
        loadToolsFromCSV(filePath);
    }

    /**
     * @param toolCode the unique code for the tool
     * @return a Tool matching the given tool code, or null if tool code not found
     */
    @Override
    public Tool getToolFromCode(String toolCode) {
        return tools.get(toolCode);
    }

    private void loadToolsFromCSV(String filePath) {
        try {
            Scanner scan = new Scanner(new File(filePath));
            while(scan.hasNextLine()) {
                // Expect each line to be in the form "Type,Brand,Code"
                String line = scan.nextLine();
                String[] lineSplit = line.split(",", 3);
                // After split lineSplit should look like ["Type", "Brand", "Code"]
                Tool tool = new Tool(lineSplit[0], lineSplit[1], lineSplit[2]);
                tools.put(tool.code(), tool);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException.getMessage());
        }
    }
}
