package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.DeptManager;
import eseo.fr.robineau.backend.service.departement.DeptManagerService;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.title.Title;
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
                .map(deptEmp -> new EmployeeDto(
                        deptEmp.getEmployees().getId(),
                        deptEmp.getEmployees().getFirstName(),
                        deptEmp.getEmployees().getLastName(),
                        deptEmp.getEmployees().getHireDate(),
                        deptEmp.getEmployees().getBirthDate(),
                        deptEmp.getDepartment() != null ? deptEmp.getDepartment().getDeptNo() : null,
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
    }

    @GetMapping("/current")
    public List<DeptManager> getCurrentDepartmentManagers(@PathVariable String deptNo) {
        return deptManagerService.getCurrentDeptManager(deptNo);
    }
}