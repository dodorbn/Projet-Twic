package eseo.fr.robineau.backend.service.departement;

import eseo.fr.robineau.backend.infrastructure.departement.DeptManagerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeptManagerServiceImpl implements DeptManagerService {
    private final DeptManagerRepository deptManagerRepository;

    public DeptManagerServiceImpl(DeptManagerRepository deptManagerRepository) {
        this.deptManagerRepository = deptManagerRepository;
    }

    @Override
    public List<DeptManager> getDeptManager(String deptNo) {
        return deptManagerRepository.findByDepartmentDeptNo(deptNo);
    }

    @Override
    public List<DeptManager> getCurrentDeptManager(String deptNo) {
        return deptManagerRepository.findByDepartmentDeptNoAndToDateAfter(deptNo, LocalDate.now());
    }
}