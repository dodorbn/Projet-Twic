package eseo.fr.robineau.backend.api.departement;

import eseo.fr.robineau.backend.api.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.DeptEmp;
import eseo.fr.robineau.backend.service.departement.DeptEmpService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/departments/{deptNo}/employees")
@CrossOrigin(origins = "http://localhost:8081")
public class DeptEmpController {

    private final DeptEmpService deptEmpService;

    public DeptEmpController(DeptEmpService deptEmpService) {
        this.deptEmpService = deptEmpService;
    }

    @GetMapping
    public List<DeptEmp> getDepartmentEmployees(@PathVariable String deptNo) {
        return deptEmpService.getDeptEmp(deptNo);
    }

    @GetMapping("/current/all")
    public List<EmployeeDto> getCurrentDepartmentEmployees(@PathVariable String deptNo,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size) {
        return deptEmpService.getCurrentDeptEmp(deptNo, page, size).stream()
                .map(deptEmp -> new EmployeeDto(
                        deptEmp.getEmployees().getId(),
                        deptEmp.getEmployees().getFirstName(),
                        deptEmp.getEmployees().getLastName(),
                        deptEmp.getEmployees().getHireDate()
                ))
                .collect(Collectors.toList());
    }
}