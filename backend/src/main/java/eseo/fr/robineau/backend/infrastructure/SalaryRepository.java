package eseo.fr.robineau.backend.infrastructure;

import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.salary.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
//    List<Salary> findByEmployeeId(String id);

    //List<Salary> findByEmpNoOrderByToDateDesc(Employee employee, Pageable pageable);
}
