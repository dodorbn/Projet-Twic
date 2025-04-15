package eseo.fr.robineau.backend.infrastructure;

import eseo.fr.robineau.backend.service.employee.Employee;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByHireDate(LocalDate hireDate);

    List<Employee> findByBirthDate(LocalDate birthDate);

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);

    Employee findByFirstNameAndLastName(String firstName, String lastName);

    Employee findByFirstNameAndLastNameAndId(String firstName, String lastName, Integer id);

    List<Employee> findByLastNameContainingIgnoreCase(String lastName, PageRequest pageable);

    List<Employee> findByFirstNameContainingIgnoreCase(String firstName, PageRequest pageable);

    List<Employee> findByIdOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            Integer id, String firstName, String lastName
    );

    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName
    );
}
