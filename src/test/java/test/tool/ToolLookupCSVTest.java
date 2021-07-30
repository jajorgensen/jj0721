package test.tool;

import demo.rental.tool.Tool;
import demo.rental.tool.ToolLookupCSV;
import demo.rental.tool.ToolLookup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToolLookupCSVTest {
    private static final String csvPath = "src/test/java/test/tool/ToolLookupCSVTest.csv";
    private static final ToolLookup toolLookup = new ToolLookupCSV(csvPath);

    @Test
    public void loadTest_LADW() {
        Tool tool = toolLookup.getToolFromCode("LADW");
        assertToolNotNullAndEquals(tool, "Ladder", "Werner", "LADW");
    }

    @Test
    public void loadTest_CHNS() {
        Tool tool = toolLookup.getToolFromCode("CHNS");
        assertToolNotNullAndEquals(tool, "Chainsaw", "Stihl", "CHNS");
    }

    @Test
    public void loadTest_JAKR() {
        Tool tool = toolLookup.getToolFromCode("JAKR");
        assertToolNotNullAndEquals(tool, "Jackhammer", "Ridgid", "JAKR");
    }

    @Test
    public void loadTest_JAKD() {
        Tool tool = toolLookup.getToolFromCode("JAKD");
        assertToolNotNullAndEquals(tool, "Jackhammer", "DeWalt", "JAKD");
    }

    private void assertToolNotNullAndEquals(Tool tool, String ladder, String werner, String ladw) {
        assertNotNull(tool);
        assertEquals(ladder, tool.type());
        assertEquals(werner, tool.brand());
        assertEquals(ladw, tool.code());
    }
}