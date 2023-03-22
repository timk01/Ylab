package thirdweek.orgstructure;

import java.io.File;
import java.io.IOException;

/**
 * Represents an OrgStructureParser.
 * Given the File (can be csv or whatever is parseable) parsing it into object
 * (here it's Employee, but different methods can be added for better usability)
 *
 * @author Ylab
 * @version 1.0
 */
public interface OrgStructureParser {

    /**
     * Original method for parsing
     *
     * @param csvFile of File type
     * @return filled Employee
     * @apiNote Employee here should be filled with full data in matryoshka way
     */
    public Employee parseStructure(File csvFile) throws IOException;
}