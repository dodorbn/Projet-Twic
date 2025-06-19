package eseo.fr.robineau.backend.controller.employee;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.title.Title;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDto toDto(Employee employees) {
        if (employees == null) {
            return null;
        }
        String department = employees.getDeptEmps().stream()
                .filter(deptEmp -> deptEmp.getDepartment() != null)
                .findFirst()
                .map(deptEmp -> deptEmp.getDepartment().getDeptNo())
                .orElse(null);

        String title = employees.getTitles().stream()
                .findFirst()
                .map(Title::getTitle)
                .orElse(null);

        Integer salary = employees.getSalaries().stream()
                .findFirst()
                .map(Salary::getSalary)
                .orElse(null);

        return new EmployeeDto(
                employees.getId(),
                employees.getFirstName(),
                employees.getLastName(),
                employees.getHireDate(),
                employees.getBirthDate(),
                department,
                title,
                salary
        );
    }

    @Override
    public List<EmployeeDto> toListDto(List<Employee> employeesList) {
        if (employeesList == null){
            return null;
        }
        List<EmployeeDto> result = new ArrayList<>();
        for (Employee employees : employeesList){
            result.add(toDto(employees));
        }
        return result;
    }
}
