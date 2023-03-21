package thirdweek.orgstructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OrgStructureParserImpl implements OrgStructureParser {
    private static final String CHAR_TO_SPLIT = ";";
    private static final Map<Long, Employee> EMPLOYEE_MAP = new HashMap<>();

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
