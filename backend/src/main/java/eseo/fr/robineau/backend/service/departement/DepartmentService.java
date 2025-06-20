package eseo.fr.robineau.backend.service.departement;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartments(Integer pageNo, Integer pageSize);

    Department getDepartment(String deptNo);

    Department getDepartmentByName(String deptName);

    Department createDepartment(Department department);

    Department updateDepartment(String deptNo, Department department);

    void deleteDepartment(String deptNo);

}