package pl.pawelgonera.atmotermtask.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import pl.pawelgonera.atmotermtask.util.LocalDateAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "active_employees")
public class ActiveEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double salary;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate employmentDate;

    public ActiveEmployee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveEmployee that = (ActiveEmployee) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(salary, that.salary) &&
                Objects.equals(employmentDate, that.employmentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salary, employmentDate);
    }

    @Override
    public String toString() {
        return "ActiveEmployee{" +
                "salary=" + salary +
                ", employmentDate=" + employmentDate +
                '}';
    }
}
