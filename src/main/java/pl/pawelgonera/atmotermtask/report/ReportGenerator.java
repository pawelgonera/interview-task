package pl.pawelgonera.atmotermtask.report;

import pl.pawelgonera.atmotermtask.entity.EmployeeXml;

public interface ReportGenerator {

    String generateReport(EmployeeXml employeeData);

}
