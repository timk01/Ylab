package thirdweek.orgstructure;

import thirdweek.trasnliterator.Transliterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Represents an OrgStructureParser interface implementation.
 *
 * @author Khasmamedov T.
 * @version 1.2
 */
public class OrgStructureParserImpl implements OrgStructureParser {
    private static final String CHAR_TO_SPLIT = ";";
    /**
     * Simple Hashmap structure, for convenience
     * Key = Long (represents unique ID), Value = Employee
     * Both are used further to go through collection, fill it, iterate and manage data
     *
     */
    private static final Map<Long, Employee> EMPLOYEE_MAP = new HashMap<>();

    /**
     * Original method for parsing
     * implementation of OrgStructureParser interface.
     * <p>
     * Traverses through initial csv file that holds simple but strict structure of
     * <i>id;boss_id;name;position</i>.
     * After skipping initial string (which should be in file even if it's not empty),
     * it splits every following string via delimiter and a) fill employee and map with basic data in loop.
     * Later on it uses already filled map: b) traverses through it and fills advanced data:
     * boss and employees (if both exists)
     * Finally, c) returns a boss
     * <p>
     * For a, b, c see the helper methods.
     *
     * @param csvFile of type File (should be not corrupted)
     * @return Employee
     * @implNote here represents org structure, see also printAllOrgStructure for better udnerstanding_
     * @see Transliterator
     */
    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Employee employee;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String aLineToParse;
            int lineCounter = 0;
            while ((aLineToParse = reader.readLine()) != null) {
                lineCounter++;
                if (lineCounter == 1) {
                    continue;
                }

                String[] splittedString = aLineToParse.split(CHAR_TO_SPLIT);

                employee = new Employee();
                fillEmployeeWithPrimaryData(splittedString, employee);
                EMPLOYEE_MAP.put(employee.getId(), employee);
            }
            fillEmployeeMapWithMainData();
        }

        return getBossFromMap();
    }

    private void fillEmployeeWithPrimaryData(String[] arrayToFill, Employee employeeToFill) {
        employeeToFill.setId((long) Integer.parseInt(arrayToFill[0]));
        if (!arrayToFill[1].isEmpty()) {
            employeeToFill.setBossId((long) Integer.parseInt(arrayToFill[1]));
        }
        employeeToFill.setName(arrayToFill[2]);
        employeeToFill.setPosition(arrayToFill[3]);
    }

    private void fillEmployeeMapWithMainData() {
        for (Map.Entry<Long, Employee> previousEmployeeEntry : EMPLOYEE_MAP.entrySet()) {
            for (Map.Entry<Long, Employee> nextEmployeeEntry : EMPLOYEE_MAP.entrySet()) {
                if (Objects.equals(nextEmployeeEntry.getValue().getBossId(), previousEmployeeEntry.getKey())) {
                    previousEmployeeEntry.getValue().getSubordinate().add(nextEmployeeEntry.getValue());
                    nextEmployeeEntry.getValue().setBoss(previousEmployeeEntry.getValue());
                }
            }
        }
    }

    private Employee getBossFromMap() {
        for (Employee employeeValue : EMPLOYEE_MAP.values()) {
            if (employeeValue.getBossId() == null) {
                return employeeValue;
            }
        }
        return null;
    }

    /**
     * Original method for printing whole structure.
     * Traverse through filled and adds necessary info for better visualisation.
     */
    public static void printAllOrgStructure() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, Employee> longEmployeeEntry : EMPLOYEE_MAP.entrySet()) {
            sb.append(" id: ").append(longEmployeeEntry.getKey())
                    .append(", boss_id: ").append(longEmployeeEntry.getValue().getBossId())
                    .append(", name: ").append(longEmployeeEntry.getValue().getName())
                    .append(", position: ").append(longEmployeeEntry.getValue().getPosition())
                    .append("; boss: ").append(longEmployeeEntry.getValue().getBoss())
                    .append("; subordinates: ").append(System.lineSeparator())
                    .append(longEmployeeEntry.getValue().getSubordinate())
                    .append(System.lineSeparator());
        }
        System.out.println(sb);
    }
}
