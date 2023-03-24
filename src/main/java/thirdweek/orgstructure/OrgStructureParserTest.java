package thirdweek.orgstructure;

import java.io.File;
import java.io.IOException;

import static thirdweek.orgstructure.OrgStructureParserImpl.printAllOrgStructure;

public class OrgStructureParserTest {
    public static void main(String[] args) throws IOException {
        OrgStructureParser orgStructureParser = new OrgStructureParserImpl();
        Employee employee = orgStructureParser
                .parseStructure(new File("src/main/java/thirdweek/orgstructure/orgstructure.csv"));
        System.out.println(employee);

        System.out.println();

        printAllOrgStructure();
    }
}
