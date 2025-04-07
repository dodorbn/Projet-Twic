package eseo.fr.robineau.backend.infrastructure;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.title.TitleId;
import eseo.fr.robineau.backend.service.title.Title;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, TitleId> {
    List<Title> findByEmployeesOrderByToDateDesc(Employee employee, Pageable pageable);
}
