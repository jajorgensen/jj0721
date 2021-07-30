package demo.rental.dates;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An implementation of HolidayLookup which gets holidays out of a give .csv file
 */
public class HolidayLookupCSV implements HolidayLookup {
    private final List<Holiday> holidays = new ArrayList<>();

    /**
     * Constructs a HolidayLookupCSV object and loads holiday data from the given .csv file
     *
     * @param filePath  the path to a .csv file containing holiday data
     */
    public HolidayLookupCSV(String filePath) {
        loadHolidaysFromCSV(filePath);
    }

    private void loadHolidaysFromCSV(String filePath) {
        try {
            Scanner scan = new Scanner(new File(filePath));
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                /*
                  Expect line to look like one of the following:
                  "Fixed,[Month],[Day]" ex Christmas -> "Fixed,12,25"
                  "ClosestWeekday,[Month],[Day]" ex July 4th -> "ClosestWeekday,7,4"
                  "Relative,[weekOfMonth],[dayOfWeek],[Month]" ex Labor Day -> "Relative,1,1,9"
                 */
                String[] lineSplit = line.split(",", 4);
                Holiday holiday = switch (lineSplit[0]) {
                    case "Fixed" -> new FixedHoliday(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]));
                    case "ClosestWeekday" -> new ClosestWeekdayHoliday(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]));
                    case "Relative" -> new RelativeHoliday(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]));
                    default -> throw new Exception("Unknown Holiday type: " + lineSplit[0]);
                };
                holidays.add(holiday);
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Gets all the holidays from the .csv file
     *
     * @return A List of all Holiday generated from the .csv file
     */
    @Override
    public List<Holiday> getAllHolidays() {
        return holidays;
    }
}
