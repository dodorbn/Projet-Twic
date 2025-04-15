package eseo.fr.robineau.backend.api;

import eseo.fr.robineau.backend.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees/{id}/titles")
@CrossOrigin(origins = "http://localhost:8081")
public class TitleController {
//    private final TitleService titleService;
//    private final EmployeeService employeeService;
//
//    public TitleController(TitleService titleService, EmployeeService employeeService) {
//        this.titleService = titleService;
//        this.employeeService = employeeService;
//    }
//
//    @GetMapping
//    public List<Title> getTitles(@PathVariable Integer empNo, @RequestParam int page, @RequestParam int size) {
//        Employee employee = employeeService.getEmployeeById(empNo);
//        return titleService.getTitlesByEmployee(employee, PageRequest.of(page, size));
//    }
//
//    @PostMapping
//    public Title createTitle(@PathVariable Integer empNo, @RequestBody Title title) {
//        Employee employee = employeeService.getEmployeeById(empNo);
//        title.setEmpNo(employee);
//        return titleService.saveTitle(title);
//    }
//
//    @DeleteMapping("/{fromDate}")
//    public void deleteTitle(@PathVariable Title id) {
//        titleService.deleteTitle(id);
//    }
}
