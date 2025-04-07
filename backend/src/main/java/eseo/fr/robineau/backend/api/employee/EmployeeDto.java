package eseo.fr.robineau.backend.api.employee;

import java.time.LocalDate;

public record EmployeeDto(int id,LocalDate birth_date, String first_name, String last_name, LocalDate hire_date, String DeptNo, String DeptName, String title, Integer salary) {

}
