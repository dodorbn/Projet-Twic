package eseo.fr.robineau.backend.service.salary;

import eseo.fr.robineau.backend.service.employee.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "salaries")
public class Salary {
    @EmbeddedId
    private SalaryId id;

    @MapsId("empNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employees;

    @NotNull
    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    public SalaryId getId() {
        return id;
    }

    public void setId(SalaryId id) {
        this.id = id;
    }

    public Employee getEmployees() {
        return employees;
    }

    public void setEmployees(Employee employee) {
        this.employees = employee;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

}