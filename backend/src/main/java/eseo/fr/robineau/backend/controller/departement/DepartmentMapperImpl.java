package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.Department;
import eseo.fr.robineau.backend.service.departement.DeptManager;
import eseo.fr.robineau.backend.controller.employee.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentMapperImpl implements DepartmentMapper {

    private final EmployeeMapper employeeMapper;

    public DepartmentMapperImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public DepartmentDto toDto(Department department) {
        List<EmployeeDto> managerDtos = department.getDeptManagers().stream()
                .map(DeptManager::getEmployees)
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());

        return new DepartmentDto(
                department.getDeptNo(),
                department.getDeptName(),
                managerDtos // Ajout des managers
        );
    }

    @Override
    public List<DepartmentDto> toListDto(List<Department> departments) {
        return departments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}