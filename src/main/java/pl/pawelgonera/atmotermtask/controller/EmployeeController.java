package pl.pawelgonera.atmotermtask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pawelgonera.atmotermtask.entity.ActiveEmployee;
import pl.pawelgonera.atmotermtask.entity.Employee;
import pl.pawelgonera.atmotermtask.service.EmployeeService;

import java.util.List;

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

        return ResponseEntity.ok(newEmployee);
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

}
