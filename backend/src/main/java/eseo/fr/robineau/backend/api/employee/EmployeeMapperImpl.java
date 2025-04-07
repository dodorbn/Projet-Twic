package eseo.fr.robineau.backend.api.employee;

import eseo.fr.robineau.backend.service.departement.DeptEmp;
import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.title.Title;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDto toDto(Employee employees) {
        if (employees == null){
            return null;
        }
        final Integer emp_no = employees.getId();
        final LocalDate birth_date = employees.getBirthDate();
        final String first_name = employees.getFirstName();
        final String last_name = employees.getLastName();
        final LocalDate hire_date = employees.getHireDate();
        DeptEmp deptEmp = employees.getDeptEmps().stream().findFirst().orElse(null);
        String deptNo = (deptEmp != null) ? deptEmp.getDepartment().getDeptNo() : null;
        String deptName = (deptEmp != null) ? deptEmp.getDepartment().getDeptName() : null;
        Salary salary = employees.getSalaries().stream().findFirst().orElse(null);
        Integer salaryValue = (salary != null) ? salary.getSalary() : null;
        Title title = employees.getTitles().stream().findFirst().orElse(null);
        String titleName = (title != null) ? title.getTitle() : null;
        return new EmployeeDto(emp_no,birth_date,first_name,last_name,hire_date,deptNo,deptName, titleName, salaryValue);
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

    @Override
    public Employee toEntity(EmployeeRequestDto employeesRequestDto) {
        Employee employees = new Employee();
        employees.setBirthDate(employeesRequestDto.getBirth_date());
        employees.setFirstName(employeesRequestDto.getFirst_name());
        employees.setLastName(employeesRequestDto.getLast_name());
        employees.setHireDate(employeesRequestDto.getHire_date());
        return employees;
    }
}
