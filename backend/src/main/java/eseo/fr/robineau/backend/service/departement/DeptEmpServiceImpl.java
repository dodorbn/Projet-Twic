package eseo.fr.robineau.backend.service.departement;

import eseo.fr.robineau.backend.infrastructure.departement.DeptEmpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeptEmpServiceImpl implements DeptEmpService {
    private final DeptEmpRepository deptEmpRepository;

    public DeptEmpServiceImpl(DeptEmpRepository deptEmpRepository) {
        this.deptEmpRepository = deptEmpRepository;
    }

    @Override
    public List<DeptEmp> getDeptEmp(String deptNo) {
        return deptEmpRepository.findByDepartmentDeptNo(deptNo);
    }

    @Override
    public List<DeptEmp> getCurrentDeptEmp(String deptNo) {
        return deptEmpRepository.findByDepartmentDeptNoAndToDateAfter(deptNo, LocalDate.now());
    }
}
