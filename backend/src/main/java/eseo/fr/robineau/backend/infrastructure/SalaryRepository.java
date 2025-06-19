package eseo.fr.robineau.backend.infrastructure;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.salary.SalaryId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
    List<Salary> findByEmployeesOrderByToDateDesc(Employee employees, Pageable pageable);
    Optional<Salary> findFirstByEmployeesOrderByToDateDesc(Employee employees);
}