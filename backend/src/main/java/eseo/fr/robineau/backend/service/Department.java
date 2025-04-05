package eseo.fr.robineau.backend.service;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "dept_no", nullable = false, length = 4)
    private String deptNo;

    @Column(name = "dept_name", nullable = false, length = 40)
    private String deptName;

    @OneToMany(mappedBy = "department")
    private Set<DeptEmp> deptEmps = new LinkedHashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<DeptManager> deptManagers = new LinkedHashSet<>();

    public Set<DeptManager> getDeptManagers() {
        return deptManagers;
    }

    public void setDeptManagers(Set<DeptManager> deptManagers) {
        this.deptManagers = deptManagers;
    }

    public Set<DeptEmp> getDeptEmps() {
        return deptEmps;
    }

    public void setDeptEmps(Set<DeptEmp> deptEmps) {
        this.deptEmps = deptEmps;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
}