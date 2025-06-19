package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;

import java.util.List;

public record DepartmentDto(
        String deptNo,
        String deptName,
        List<EmployeeDto> managers
){}