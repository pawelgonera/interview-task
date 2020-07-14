package pl.pawelgonera.atmotermtask.service;

import org.springframework.stereotype.Service;
import pl.pawelgonera.atmotermtask.entity.ActiveEmployee;
import pl.pawelgonera.atmotermtask.entity.Employee;
import pl.pawelgonera.atmotermtask.exception.ResourceNotFoundException;
import pl.pawelgonera.atmotermtask.repository.ActiveEmployeeRepository;
import pl.pawelgonera.atmotermtask.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ActiveEmployeeRepository activeEmployeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository, ActiveEmployeeRepository activeEmployeeRepository) {
        this.employeeRepository = employeeRepository;
        this.activeEmployeeRepository = activeEmployeeRepository;
    }

    public List<Employee> getAllEmployees(Boolean onlyActive){

        List<Employee> employees = employeeRepository.findAll();

        if(onlyActive){
            return employees.stream()
                            .filter(e -> !e.getActiveEmployee().getEmploymentDate().isEqual(LocalDate.parse("9999-01-01")))
                            .collect(Collectors.toList());
        }

        return employees;
    }

    public Employee addEmployee(Employee employee){

        if(employee.getActiveEmployee() == null){
            ActiveEmployee activeEmployee = new ActiveEmployee();
            activeEmployee.setSalary(0.0);
            activeEmployee.setEmploymentDate(LocalDate.parse("9999-01-01"));
            employee.setActiveEmployee(activeEmployee);
        }else if(employee.getActiveEmployee().getEmploymentDate() == null || employee.getActiveEmployee().getSalary() == null){
            employee.getActiveEmployee().setEmploymentDate(LocalDate.parse("9999-01-01"));
            employee.getActiveEmployee().setSalary(0.0);
        }

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee, Long id){

        Employee employeeToUpdate = getEmployeeById(id);
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.getActiveEmployee().setSalary(employee.getActiveEmployee().getSalary());
        employeeToUpdate.getActiveEmployee().setEmploymentDate(employee.getActiveEmployee().getEmploymentDate());

        return employeeRepository.save(employeeToUpdate);
    }

    public ActiveEmployee updateActiveEmployee(ActiveEmployee activeEmployee, Long id){

        ActiveEmployee activeEmployeeToUpdate = getActiveEmployeeById(id);
        activeEmployeeToUpdate.setSalary(activeEmployee.getSalary());
        activeEmployeeToUpdate.setEmploymentDate(activeEmployee.getEmploymentDate());

        return activeEmployeeRepository.save(activeEmployeeToUpdate);
    }

    public String deleteEmployee(Long id){

        Employee employeeToDelete = getEmployeeById(id);

        employeeRepository.delete(employeeToDelete);

        return "Employee deleted";
    }

    private Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id: " + id));
    }

    private ActiveEmployee getActiveEmployeeById(Long id){
        return activeEmployeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ActiveEmployee not found for this id: " + id));
    }
}
