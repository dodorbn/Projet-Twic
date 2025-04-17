package eseo.fr.robineau.backend.infrastructure.departement;

import eseo.fr.robineau.backend.service.departement.DeptManager;
import eseo.fr.robineau.backend.service.departement.DeptManagerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeptManagerRepository extends JpaRepository<DeptManager, DeptManagerId> {

    List<DeptManager> findByDepartmentDeptNo(String deptNo);

    List<DeptManager> findByDepartmentDeptNoAndToDateAfter(String deptNo, LocalDate now);
}