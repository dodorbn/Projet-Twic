package eseo.fr.robineau.backend.service.departement;

import eseo.fr.robineau.backend.service.employee.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "dept_manager")
public class DeptManager {
    @EmbeddedId
    private DeptManagerId id;

    @MapsId("empNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employees;

    @MapsId("deptNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no")
    private Department department;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @NotNull
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    public DeptManagerId getId() {
        return id;
    }

    public void setId(DeptManagerId id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}