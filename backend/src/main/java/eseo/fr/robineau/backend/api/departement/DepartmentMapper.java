package eseo.fr.robineau.backend.api.departement;

import eseo.fr.robineau.backend.api.departement.DepartmentDto;
import eseo.fr.robineau.backend.service.departement.Department;
import java.util.List;

public interface DepartmentMapper {
    DepartmentDto toDto(Department department);
    List<DepartmentDto> toListDto(List<Department> departments);
}