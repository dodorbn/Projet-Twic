package eseo.fr.robineau.backend.controller.employee;

import eseo.fr.robineau.backend.service.employee.Employee;

import java.util.List;

public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    List<EmployeeDto> toListDto(List<Employee> employeeList);
}
