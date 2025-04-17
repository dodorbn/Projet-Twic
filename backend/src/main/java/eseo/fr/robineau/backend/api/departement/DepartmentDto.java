package eseo.fr.robineau.backend.api.departement;

import eseo.fr.robineau.backend.api.employee.EmployeeDto;

import java.util.List;

public record DepartmentDto(
        String deptNo,
        String deptName,
        List<EmployeeDto> managers
) {}
