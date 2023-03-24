package thirdweek.orgstructure;

import thirdweek.trasnliterator.Transliterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Employee.
 * Simple org structure where one employee usually has boss (though, it's not the case if he is his own boss)
 * and subordinates (though, again, it's not the case if the person is on bottom of company hierarchy)
 *
 * @author Ylab
 * @version 1.0
 */
public class Employee {
    private Long id;
    private Long bossId;
    private String name;
    private String position;
    private Employee boss;
    private List<Employee> subordinate = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBossId() {
        return bossId;
    }

    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public List<Employee> getSubordinate() {
        return subordinate;
    }

    /**
     * Somewhat stripped down method to help with avoiding cyclic reference
     *
     * @see OrgStructureParserImpl
     */
    @Override
    public String toString() {
        return "id: " + id +
                ", name: '" + name + '\'' +
                ", position: '" + position + "";
    }
}