package eseo.fr.robineau.backend.infrastructure;

import eseo.fr.robineau.backend.service.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
  Page<Department> findDepartmentsByDeptNameContainingIgnoreCase(String name, PageRequest pageable);
}
