package pl.pawelgonera.atmotermtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pawelgonera.atmotermtask.entity.ActiveEmployee;
import pl.pawelgonera.atmotermtask.entity.Employee;
import pl.pawelgonera.atmotermtask.entity.EmployeeXml;
import pl.pawelgonera.atmotermtask.report.PdfReportFactory;
import pl.pawelgonera.atmotermtask.report.ReportFactory;
import pl.pawelgonera.atmotermtask.report.ReportGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AtmotermTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmotermTaskApplication.class, args);

		List<Employee> employees = new ArrayList<>();
		List<ActiveEmployee> activeEmployees = new ArrayList<>();

		ActiveEmployee activeEmployee1 = new ActiveEmployee();
		activeEmployee1.setId(1L);
		activeEmployee1.setSalary(3000.0);
		activeEmployee1.setEmploymentDate(LocalDate.parse("2010-04-23"));
		activeEmployees.add(activeEmployee1);

		ActiveEmployee activeEmployee2 = new ActiveEmployee();
		activeEmployee2.setId(2L);
		activeEmployee2.setSalary(3200.0);
		activeEmployee2.setEmploymentDate(LocalDate.parse("2008-02-12"));
		activeEmployees.add(activeEmployee2);

		ActiveEmployee activeEmployee3 = new ActiveEmployee();
		activeEmployee3.setId(3L);
		activeEmployee3.setSalary(2700.0);
		activeEmployee3.setEmploymentDate(LocalDate.parse("2015-06-03"));
		activeEmployees.add(activeEmployee3);


		Employee employee1= new Employee();
		employee1.setId(1L);
		employee1.setName("Paweł");
		employee1.setActiveEmployee(activeEmployee1);
		employees.add(employee1);

		Employee employee2= new Employee();
		employee2.setId(2L);
		employee2.setName("Marek");
		employee2.setActiveEmployee(activeEmployee2);
		employees.add(employee2);

		Employee employee3= new Employee();
		employee3.setId(3L);
		employee3.setName("Marzena");
		employee3.setActiveEmployee(activeEmployee3);
		employees.add(employee3);

		EmployeeXml data = new EmployeeXml();
		data.setEmployees(employees);


		ReportFactory reportFactory = new PdfReportFactory();
		ReportGenerator pdfReportGenerator = reportFactory.newReportGenerator();
		pdfReportGenerator.generateReport(data);

	}

}
