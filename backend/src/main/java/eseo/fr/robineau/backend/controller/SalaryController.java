package eseo.fr.robineau.backend.controller;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.salary.SalaryId;
import eseo.fr.robineau.backend.service.salary.SalaryService;
import eseo.fr.robineau.backend.service.employee.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees/{employeeId}/salaries")
@CrossOrigin(origins = "http://localhost:8081")
public class SalaryController {
    private final SalaryService salaryService;
    private final EmployeeService employeeService;

    public SalaryController(SalaryService salaryService, EmployeeService employeeService) {
        this.salaryService = salaryService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Salary> getEmployeeSalaries(
            @PathVariable Integer employeeId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return salaryService.getSalariesByEmployeeId(employeeId, pageNo, pageSize);
    }

    @GetMapping("/latest")
    public Salary getLatestSalary(@PathVariable Integer employeeId) {
        return salaryService.getLatestSalaryByEmployeeId(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createSalary(@PathVariable Integer employeeId, @Valid @RequestBody Salary salary) {
        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        if (salary.getId() == null) {
            SalaryId salaryId = new SalaryId();
            salaryId.setEmpNo(employeeId);
            salaryId.setFromDate(LocalDate.now());
            salary.setId(salaryId);
        }

        salary.setEmployees(employee);
        return salaryService.createSalary(salary);
    }



    @DeleteMapping("/{fromDate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSalary(@PathVariable Integer employeeId, @PathVariable LocalDate fromDate) {
        SalaryId id = new SalaryId(employeeId, fromDate);
        salaryService.deleteSalary(id);
    }
}