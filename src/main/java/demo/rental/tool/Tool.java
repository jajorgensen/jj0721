package demo.rental.tool;

/**
 * Tool is an immutable record for storing information about tools.
 *
 * Each Tool has:
 * type - The type of the tool, ex "Ladder"
 * brand - The brand of the tool, ex "Werner"
 * code - An identifier for the particular type and brand of tool, ex "LADW"
 */
public record Tool(String type, String brand, String code) {}
