package eseo.fr.robineau.backend.controller.employee;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.employee.EmployeeService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@CrossOrigin(origins = "http://localhost:8081")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(@Autowired EmployeeService employeeService,@Autowired EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees (@RequestParam(defaultValue = "1") @Min(1) @NotNull Integer pageNo,
                                              @RequestParam(defaultValue = "10") @Min(1) @NotNull Integer pageSize,
                                              @RequestParam(required = false) String lastName){
        if (lastName != null) {
            List<Employee> employee = employeeService.getEmployeesByLastName(lastName, pageNo - 1, pageSize);
            return employeeMapper.toListDto(employee);
        } else {
            List<Employee> employee = employeeService.getAllEmployees(pageNo - 1, pageSize);
            return employeeMapper.toListDto(employee);
        }
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/search")
    public List<EmployeeDto> searchEmployees(@RequestParam String query) {
        List<Employee> employee = employeeService.searchEmployees(query);
        return employeeMapper.toListDto(employee);
    }
}
