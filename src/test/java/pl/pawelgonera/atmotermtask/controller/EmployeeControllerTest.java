package pl.pawelgonera.atmotermtask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.pawelgonera.atmotermtask.entity.ActiveEmployee;
import pl.pawelgonera.atmotermtask.entity.Employee;
import pl.pawelgonera.atmotermtask.exception.ResourceNotFoundException;
import pl.pawelgonera.atmotermtask.repository.ActiveEmployeeRepository;
import pl.pawelgonera.atmotermtask.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ActiveEmployeeRepository activeEmployeeRepository;

    private List<Employee> employees = setEmployeesList();

    @Rule
    public TestName testName = new TestName();

    @Nested
    class ManipulationMethodsTest{

        @BeforeEach
        public void init() {
            when(employeeRepository.findById(1L)).thenReturn(Optional.of(employees.get(0)));
        }

        @Test
        void shouldUpdateEmployeeWithStatus200() throws Exception{

            final ActiveEmployee activeEmployee = new ActiveEmployee();
            activeEmployee.setId(1L);
            activeEmployee.setSalary(3000.0);
            activeEmployee.setEmploymentDate(LocalDate.parse("2011-01-15"));
            final Employee updatedEmployee = new Employee();
            updatedEmployee.setId(1L);
            updatedEmployee.setName("Marek");
            updatedEmployee.setActiveEmployee(activeEmployee);

            when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

            mockMvc.perform(put("/employees/1")
                    .content(mapper.writeValueAsString(updatedEmployee)).contentType(MediaType.APPLICATION_JSON))
                    .andDo(result -> displayResultAsJson(updatedEmployee))
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Marek"))
                    .andExpect(jsonPath("$.activeEmployee.id").value(1))
                    .andExpect(jsonPath("$.activeEmployee.salary").value(3000.0))
                    .andExpect(jsonPath("$.activeEmployee.employmentDate").value("2011-01-15"));

            verify(employeeRepository, times(1)).save(updatedEmployee);

        }

        @Test
        void shouldReturnStatus404AfterUpdateWrongEmployee() throws Exception{

            when(employeeRepository.findById(3L)).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(put("/employees/3")
                    .content(mapper.writeValueAsString(employees.get(0))).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404));
        }

        @Test
        void shouldUpdateActiveEmployeeWithStatus200() throws Exception{

            final ActiveEmployee updatedActiveEmployee = new ActiveEmployee();
            updatedActiveEmployee.setId(1L);
            updatedActiveEmployee.setSalary(3000.0);
            updatedActiveEmployee.setEmploymentDate(LocalDate.parse("2011-01-15"));

            when(activeEmployeeRepository.findById(1L)).thenReturn(Optional.of(employees.get(0).getActiveEmployee()));
            when(activeEmployeeRepository.save(any(ActiveEmployee.class))).thenReturn(updatedActiveEmployee);

            mockMvc.perform(put("/employees/active/1")
                    .content(mapper.writeValueAsString(updatedActiveEmployee)).contentType(MediaType.APPLICATION_JSON))
                    .andDo(result -> displayResultAsJson(updatedActiveEmployee))
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.salary").value(3000.0))
                    .andExpect(jsonPath("$.employmentDate").value("2011-01-15"));

            verify(activeEmployeeRepository, times(1)).save(updatedActiveEmployee);

        }

        @Test
        void shouldReturnStatus404AfterUpdateWrongActiveEmployee() throws Exception{

            when(employeeRepository.findById(4L)).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(put("/employees/active/4")
                    .content(mapper.writeValueAsString(employees.get(0))).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404));
        }

        @Test
        void shouldDeleteEmployeeWithStatus200() throws Exception{

            doNothing().when(employeeRepository).deleteById(1L);

            mockMvc.perform(delete("/employees/1"))
                    .andExpect(status().is(200))
                    .andReturn().getResponse().getContentAsString();

            verify(employeeRepository, times(1)).delete(employees.get(0));

        }

        @Test
        void shouldReturnStatus404AfterDeleteWrongEmployee() throws Exception{

            when(employeeRepository.findById(5L)).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(delete("/employees/5")
                    .content(mapper.writeValueAsString(employees.get(0))).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404));
        }

    }

    @Test
    public void shouldGetAllEmployeesWithStatus200() throws Exception {

        when(employeeRepository.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employees").param("onlyActive", "false"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> displayResultAsJson(employees))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Marek"))
                .andExpect(jsonPath("$[0].activeEmployee.id").value(1))
                .andExpect(jsonPath("$[0].activeEmployee.salary").value(2800.0))
                .andExpect(jsonPath("$[0].activeEmployee.employmentDate").value("2010-06-02"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Marzena"))
                .andExpect(jsonPath("$[1].activeEmployee.id").value(2))
                .andExpect(jsonPath("$[1].activeEmployee.salary").value(2400.0))
                .andExpect(jsonPath("$[1].activeEmployee.employmentDate").value("2013-02-27"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Karol"))
                .andExpect(jsonPath("$[2].activeEmployee.id").value(3))
                .andExpect(jsonPath("$[2].activeEmployee.salary").value(0.0))
                .andExpect(jsonPath("$[2].activeEmployee.employmentDate").value("9999-01-01"));

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void shouldGetAllOnlyActiveEmployeesWithStatus200() throws Exception {

        when(employeeRepository.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employees").param("onlyActive", "true"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> displayResultAsJson(employees))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Marek"))
                .andExpect(jsonPath("$[0].activeEmployee.id").value(1))
                .andExpect(jsonPath("$[0].activeEmployee.salary").value(2800.0))
                .andExpect(jsonPath("$[0].activeEmployee.employmentDate").value("2010-06-02"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Marzena"))
                .andExpect(jsonPath("$[1].activeEmployee.id").value(2))
                .andExpect(jsonPath("$[1].activeEmployee.salary").value(2400.0))
                .andExpect(jsonPath("$[1].activeEmployee.employmentDate").value("2013-02-27"));

        verify(employeeRepository, times(1)).findAll();
    }


    @Test
    void shouldCreateEmployeeWithStatus201() throws Exception{

        final Employee employee = employees.get(0);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                .content(mapper.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> displayResultAsJson(employee))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Marek"))
                .andExpect(jsonPath("$.activeEmployee.id").value(1))
                .andExpect(jsonPath("$.activeEmployee.salary").value(2800.0))
                .andExpect(jsonPath("$.activeEmployee.employmentDate").value("2010-06-02"));

        verify(employeeRepository, times(1)).save(employee);
    }

    private List<Employee> setEmployeesList(){
        final ActiveEmployee activeEmployee1 = new ActiveEmployee();
        activeEmployee1.setId(1L);
        activeEmployee1.setSalary(2800.0);
        activeEmployee1.setEmploymentDate(LocalDate.parse("2010-06-02"));
        final ActiveEmployee activeEmployee2 = new ActiveEmployee();
        activeEmployee2.setId(2L);
        activeEmployee2.setSalary(2400.0);
        activeEmployee2.setEmploymentDate(LocalDate.parse("2013-02-27"));
        final ActiveEmployee activeEmployee3 = new ActiveEmployee();
        activeEmployee3.setId(3L);
        activeEmployee3.setSalary(0.0);
        activeEmployee3.setEmploymentDate(LocalDate.parse("9999-01-01"));

        final Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Marek");
        employee1.setActiveEmployee(activeEmployee1);
        final Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Marzena");
        employee2.setActiveEmployee(activeEmployee2);
        final Employee employee3 = new Employee();
        employee3.setId(3L);
        employee3.setName("Karol");
        employee3.setActiveEmployee(activeEmployee3);

        return employees = Arrays.asList(employee1, employee2, employee3);
    }

    private static void displayResultAsJson(Object object) {
        String output;
        try {
            output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(output);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}