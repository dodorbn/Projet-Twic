package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.Department;
import eseo.fr.robineau.backend.service.departement.DepartmentService;
import eseo.fr.robineau.backend.service.departement.DeptEmp;
import eseo.fr.robineau.backend.service.departement.DeptEmpService;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.title.Title;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/departments")
@CrossOrigin(origins = "http://localhost:8081")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final DeptEmpService deptEmpService;

    public DepartmentController(DepartmentService departmentService,
                                DepartmentMapper departmentMapper,
                                DeptEmpService deptEmpService) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
        this.deptEmpService = deptEmpService;
    }

    @GetMapping
    public List<DepartmentDto> getAllDepartments(
            @RequestParam(defaultValue = "1") @Min(1) @NotNull Integer pageNo,
            @RequestParam(defaultValue = "10") @Min(1) @NotNull Integer pageSize) {
        List<Department> departments = departmentService.getDepartments(pageNo - 1, pageSize);
        return departmentMapper.toListDto(departments);
    }

    @GetMapping("{deptNo}")
    public DepartmentDto getDepartment(@PathVariable String deptNo) {
        Department department = departmentService.getDepartment(deptNo);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }
        return departmentMapper.toDto(department);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@RequestBody DepartmentRequestDto requestDto) {
        Department department = new Department();
        department.setDeptNo(requestDto.deptNo());
        department.setDeptName(requestDto.deptName());
        Department created = departmentService.createDepartment(department);
        return departmentMapper.toDto(created);
    }

    @PutMapping("/{deptNo}")
    public DepartmentDto updateDepartment(@PathVariable String deptNo, @RequestBody DepartmentRequestDto requestDto) {
        Department department = new Department();
        department.setDeptNo(requestDto.deptNo());
        department.setDeptName(requestDto.deptName());
        Department updated = departmentService.updateDepartment(deptNo, department);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }
        return departmentMapper.toDto(updated);
    }

    @DeleteMapping("/{deptNo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable String deptNo) {
        departmentService.deleteDepartment(deptNo);
    }

    @GetMapping("/{deptNo}/employees/current")
    public ResponseEntity<List<EmployeeDto>> getDepartmentWithCurrentEmployees(@PathVariable String deptNo,
                                                                             @RequestParam(defaultValue = "0")Integer page,
                                                                             @RequestParam(defaultValue = "10") Integer size) {
        List<DeptEmp> currentEmployees = deptEmpService.getCurrentDeptEmp(deptNo, page, size);

        List<EmployeeDto> employeeDtos = currentEmployees.stream()
                .map(deptEmp -> new EmployeeDto(
                        deptEmp.getEmployees().getId(),
                        deptEmp.getEmployees().getFirstName(),
                        deptEmp.getEmployees().getLastName(),
                        deptEmp.getEmployees().getHireDate(),
                        deptEmp.getEmployees().getBirthDate(),
                        deptEmp.getDepartment().getDeptNo(), // Ajout du département
                        deptEmp.getEmployees().getTitles().stream()
                                .findFirst()
                                .map(Title::getTitle)
                                .orElse(null), // Ajout du titre
                        deptEmp.getEmployees().getSalaries().stream()
                                .findFirst()
                                .map(Salary::getSalary)
                                .orElse(null) // Ajout du salaire
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(employeeDtos);
    }
}