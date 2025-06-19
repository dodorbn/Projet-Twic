package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;

import java.util.List;

public record DepartmentDto(
        String deptNo,
        String deptName,
        List<EmployeeDto> managers
) {

    public DepartmentDto(String deptNo, String deptName, List<EmployeeDto> managers) {
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.managers = managers;
    }

    public DepartmentDto(DepartmentRequestDto requestDto, List<EmployeeDto> managers) {
        this(requestDto.getDeptNo(), requestDto.getDeptName(), managers);
    }
}