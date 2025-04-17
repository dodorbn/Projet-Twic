package eseo.fr.robineau.backend.service.departement;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> getDepartments(Integer pageNo, Integer pageSize);

    Department getDepartment(String deptNo);

    Department getDepartmentByName(String deptName);

//    List<Department> searchDepartments(String query);

    Department createDepartment(Department department);

    Department updateDepartment(String deptNo, Department department);

    void deleteDepartment(String deptNo);

}