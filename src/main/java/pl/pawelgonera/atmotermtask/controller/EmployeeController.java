package pl.pawelgonera.atmotermtask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pawelgonera.atmotermtask.entity.ActiveEmployee;
import pl.pawelgonera.atmotermtask.entity.Employee;
import pl.pawelgonera.atmotermtask.entity.EmployeeXml;
import pl.pawelgonera.atmotermtask.report.PdfReportFactory;
import pl.pawelgonera.atmotermtask.report.ReportFactory;
import pl.pawelgonera.atmotermtask.report.ReportGenerator;
import pl.pawelgonera.atmotermtask.service.EmployeeService;

import java.net.URI;
import java.util.*;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(@RequestParam Boolean onlyActive){

        return employeeService.getAllEmployees(onlyActive);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){

        Employee newEmployee = employeeService.addEmployee(employee);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{id}")
                        .buildAndExpand(newEmployee.getId()).toUri();

        return ResponseEntity.created(location).body(newEmployee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable Long id){

        Employee updatedEmployee = employeeService.updateEmployee(employee, id);

        return ResponseEntity.ok(updatedEmployee);

    }

    @PutMapping("/employees/active/{id}")
    public ResponseEntity<?> updateActiveEmployee(@RequestBody ActiveEmployee activeEmployee, @PathVariable Long id){

        ActiveEmployee updatedActiveEmployee = employeeService.updateActiveEmployee(activeEmployee, id);

        return ResponseEntity.ok(updatedActiveEmployee);

    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){

        String message = employeeService.deleteEmployee(id);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/employees/report")
    public ResponseEntity<String> generateReport(){

        List<Employee> employees = employeeService.getAllEmployees(false);

        if(employees.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
        }

        EmployeeXml employeeXml = new EmployeeXml();
        employeeXml.setEmployees(employees);

        ReportFactory reportFactory = new PdfReportFactory();
        ReportGenerator pdfReportGenerator = reportFactory.newReportGenerator();
        String message = pdfReportGenerator.generateReport(employeeXml);

        return ResponseEntity.ok(message);
    }

}
