package eseo.fr.robineau.backend.service.departement;

import eseo.fr.robineau.backend.infrastructure.departement.DepartmentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getDepartments(Integer pageNo, Integer pageSize) {
        return departmentRepository.findAll(PageRequest.of(pageNo, pageSize)).getContent();
    }

    @Override
    public Department getDepartment(String deptNo) {
        return departmentRepository.findById(deptNo).orElse(null);
    }

    @Override
    public Department getDepartmentByName(String deptName) {
        return departmentRepository.findByDeptName(deptName);
    }

//    @Override
//    public List<Department> searchDepartments(String query) {
//        return departmentRepository.findByDeptNameContainingIgnoreCase(query);
//    }

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(String deptNo, Department department) {
        return departmentRepository.findById(deptNo)
            .map(existing -> {
                existing.setDeptName(department.getDeptName());
                return departmentRepository.save(existing);
            })
            .orElse(null);
    }

    @Override
    public void deleteDepartment(String deptNo) {
        departmentRepository.deleteById(deptNo);
    }
}