package eseo.fr.robineau.backend.api;

import eseo.fr.robineau.backend.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees/{empNo}/salaries")
@CrossOrigin(origins = "http://localhost:8081")
public class SalaryController {
//    private SalaryService salaryService;
//    private EmployeeService employeeService;
//
//    public SalaryController(SalaryService salaryService) {
//        this.salaryService = salaryService;
//    }
//
//    @GetMapping
//    public Salary getSalary(@PathVariable SalaryId id) {
//        return salaryService.getSalary(id);
//    }

   // @GetMapping("/last")
//    public Salary getLastSalary(@PathVariable Integer empNo) {
//        return salaryService.getSalaryByEmployee(empNo, PageRequest.of(0, 1)).get(0);
//    }

//    @PostMapping
//    public Salary createSalary(@PathVariable Integer empNo, @RequestBody Salary salary) {
//        Employee employee = employeeService.getEmployeeById(empNo);
//        salary.setEmpNo(employee);
//        return salaryService.saveSalary(salary);
//    }
//
//    @DeleteMapping
//    public boolean deleteSalary(@PathVariable String empNo, @RequestBody Salary salary) {
//        return salaryService.deleteSalary(empNo);
//    }
}
