package eseo.fr.robineau.backend.api.departement;

import org.springframework.stereotype.Service;

@Service
public class DepartmentMapperImpl implements DepartmentMapper {

//  @Override
//  public DepartmentDto toDto(Department department) {
//    if(department == null) {
//      return null;
//    }
//    final String deptNo = department.getDeptNo();
//    final String deptName = department.getDeptName();
//    return new DepartmentDto(deptNo, deptName);
//  }
//
//  @Override
//  public List<DepartmentDto> toListDto(List<Department> listDepartement) {
//    if (listDepartement == null) {
//      return null;
//    }
//    List<DepartmentDto> result = new ArrayList<>();
//    for (Department departement : listDepartement) {
//      result.add(toDto(departement));
//    }
//    return result;
//  }
//
//  @Override
//  public Department toEntity(DepartmentRequestDto departmentRequestDto) {
//    Department department = new Department();
//    department.setDeptNo(departmentRequestDto.getDeptNo());
//    department.setDeptName(departmentRequestDto.getDeptName());
//    return department;
//  }
}
