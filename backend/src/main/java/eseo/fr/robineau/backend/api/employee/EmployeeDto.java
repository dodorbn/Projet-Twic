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

    public EmployeeDto(Integer id, String firstName, String lastName, LocalDate hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
    }
}
