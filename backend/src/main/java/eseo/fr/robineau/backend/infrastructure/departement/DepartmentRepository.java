package eseo.fr.robineau.backend.infrastructure.departement;

import eseo.fr.robineau.backend.service.departement.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
  //Page<Department> findDepartmentsByDeptNameContainingIgnoreCase(String name, PageRequest pageable);
}
