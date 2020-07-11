package pl.pawelgonera.atmotermtask.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50, nullable = false)
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_emp_id")
    private ActiveEmployee activeEmployee;

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActiveEmployee getActiveEmployee() {
        return activeEmployee;
    }

    public void setActiveEmployee(ActiveEmployee activeEmployee) {
        this.activeEmployee = activeEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(activeEmployee, employee.activeEmployee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, activeEmployee);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activeEmployee=" + activeEmployee +
                '}';
    }
}
