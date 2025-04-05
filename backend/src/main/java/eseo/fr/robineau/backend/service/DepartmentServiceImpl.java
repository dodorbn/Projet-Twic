package eseo.fr.robineau.backend.service;

import eseo.fr.robineau.backend.infrastructure.DepartmentRepository;
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
  public List<Department> getDepartmentsFilterByName(String name, Integer pageNo, Integer pageSize) {
    return departmentRepository.findDepartmentsByDeptNameContainingIgnoreCase(name, PageRequest.of(pageNo, pageSize)).getContent();
  }

  @Override
  public boolean createDepartment(Department department) {
    if(!departmentRepository.existsById(department.getDeptNo())) {
      departmentRepository.save(department);
      return true;
    } else {
      return false;
    }

  }

  @Override
  public boolean updateDepartment(String deptNo, Department department) {
    Department oldDepartment = departmentRepository.findById(deptNo).orElse(null);
    if (oldDepartment != null){
      oldDepartment.setDeptNo(department.getDeptNo());
      oldDepartment.setDeptName(department.getDeptName());
      departmentRepository.save(oldDepartment);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean deleteDepartment(String deptNo) {
    if (departmentRepository.existsById(deptNo)) {
      departmentRepository.deleteById(deptNo);
      return true;
    }
    else {
      return false;
    }
  }

}

