package pl.pawelgonera.atmotermtask.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Employee")
public class EmployeeXml {

    private List<Employee> employees;

    public EmployeeXml () {
    }

    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee")
    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees (List<Employee> employees) {
        this.employees = employees;
    }

    @XmlElementWrapper(name = "activeEmployees")
    @XmlElement(name = "activeEmployee")
    public List<ActiveEmployee> getActiveEmployees() {

        List<ActiveEmployee> activeEmployeeList = new ArrayList<>();
        for(Employee employee : employees){
            activeEmployeeList.add(employee.getActiveEmployee());
        }


        return activeEmployeeList;
    }

}
