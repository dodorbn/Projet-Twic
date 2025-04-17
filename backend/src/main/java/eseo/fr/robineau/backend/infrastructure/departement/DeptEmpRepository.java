package eseo.fr.robineau.backend.infrastructure.departement;

import eseo.fr.robineau.backend.service.departement.DeptEmpId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eseo.fr.robineau.backend.service.departement.DeptEmp;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {

    public List<DeptEmp> findByDepartmentDeptNo(String deptNo);

    List<DeptEmp> findByDepartmentDeptNoAndToDateAfter(String deptNo, LocalDate now, Pageable pageable);

}
