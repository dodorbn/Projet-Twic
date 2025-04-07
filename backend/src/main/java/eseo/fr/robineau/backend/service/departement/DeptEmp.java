package eseo.fr.robineau.backend.service.departement;

import eseo.fr.robineau.backend.service.employee.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "dept_emp")
public class DeptEmp {
    @EmbeddedId
    private DeptEmpId id;

    @MapsId("empNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employees;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @NotNull
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @MapsId("deptNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no")
    private Department department;

    public DeptEmpId getId() {
        return id;
    }

    public void setId(DeptEmpId id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}