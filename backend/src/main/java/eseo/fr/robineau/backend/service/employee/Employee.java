package eseo.fr.robineau.backend.service.employee;

import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.title.Title;
import eseo.fr.robineau.backend.service.departement.DeptEmp;
import eseo.fr.robineau.backend.service.departement.DeptManager;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "emp_no", nullable = false)
    private Integer id;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "first_name", nullable = false, length = 14)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 16)
    private String lastName;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employees")
    private Set<DeptEmp> deptEmps = new LinkedHashSet<>();

    @OneToMany(mappedBy = "employees")
    private Set<DeptManager> deptManagers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "employees")
    private Set<Salary> salaries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "employees")
    private Set<Title> titles = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Set<Title> getTitles() {
        return titles;
    }

    public void setTitles(Set<Title> titles) {
        this.titles = titles;
    }

    public Set<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(Set<Salary> salaries) {
        this.salaries = salaries;
    }

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
}