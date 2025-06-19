package eseo.fr.robineau.backend.service.salary;

import eseo.fr.robineau.backend.infrastructure.SalaryRepository;
import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.employee.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;
    private final EmployeeService employeeService;

    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeService employeeService) {
        this.salaryRepository = salaryRepository;
        this.employeeService = employeeService;
    }

    @Override
    public List<Salary> getSalariesByEmployeeId(Integer empNo, Integer pageNo, Integer pageSize) {
        Employee employees = employeeService.getEmployeeById(empNo)
                .orElseThrow(() -> new Error("Employee not found"));
        return salaryRepository.findByEmployeesOrderByToDateDesc(
                employees,
                PageRequest.of(pageNo, pageSize));
    }

    @Override
    public Salary getLatestSalaryByEmployeeId(Integer empNo) {
        Employee employees = employeeService.getEmployeeById(empNo)
                .orElseThrow(() -> new Error("Employee not found"));
        return salaryRepository.findFirstByEmployeesOrderByToDateDesc(employees)
                .orElseThrow(() -> new Error("Salary not found"));
    }

    @Override
    public Salary createSalary(Salary salary) {
        return salaryRepository.save(salary);
    }

    @Override
    public void deleteSalary(SalaryId id) {
        if (!salaryRepository.existsById(id)) {
            throw new Error("Salary not found");
        }
        salaryRepository.deleteById(id);
    }
}