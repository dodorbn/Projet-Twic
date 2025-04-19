package eseo.fr.robineau.backend.api.employee;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate hireDate;
    private LocalDate birthDate;
    private String department;
    private String title;
    private Integer salary;

    public EmployeeDto(Integer id, String firstName,
                       String lastName, LocalDate hireDate,
                       LocalDate birthDate, String department,
                       String title, Integer salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.birthDate = birthDate;
        this.department = department;
        this.title = title;
        this.salary = salary;
    }
}
