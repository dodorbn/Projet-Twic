package eseo.fr.robineau.backend.api.departement;

import eseo.fr.robineau.backend.api.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.DeptManager;
import eseo.fr.robineau.backend.service.departement.DeptManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/departments/{deptNo}/managers")
@CrossOrigin(origins = "http://localhost:8081")
public class DeptManagerController {

    private final DeptManagerService deptManagerService;

    public DeptManagerController(DeptManagerService deptManagerService) {
        this.deptManagerService = deptManagerService;
    }

    @GetMapping
    public List<EmployeeDto> getDepartmentManagers(@PathVariable String deptNo) {
        return deptManagerService.getDeptManager(deptNo).stream()
                .map(deptManager -> new EmployeeDto(
                        deptManager.getEmployees().getId(),
                        deptManager.getEmployees().getFirstName(),
                        deptManager.getEmployees().getLastName(),
                        deptManager.getEmployees().getHireDate()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/current")
    public List<DeptManager> getCurrentDepartmentManagers(@PathVariable String deptNo) {
        return deptManagerService.getCurrentDeptManager(deptNo);
    }
}