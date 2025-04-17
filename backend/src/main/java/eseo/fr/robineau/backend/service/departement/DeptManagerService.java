package eseo.fr.robineau.backend.service.departement;

import java.util.List;

public interface DeptManagerService {
    List<DeptManager> getDeptManager(String deptNo);

    List<DeptManager> getCurrentDeptManager(String deptNo);
}
