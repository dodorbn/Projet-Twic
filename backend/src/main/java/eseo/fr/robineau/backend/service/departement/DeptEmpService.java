package eseo.fr.robineau.backend.service.departement;

import java.util.List;

public interface DeptEmpService {
    List<DeptEmp> getDeptEmp(String deptNo);

    List<DeptEmp> getCurrentDeptEmp(String deptNo,int page, int size);
}
