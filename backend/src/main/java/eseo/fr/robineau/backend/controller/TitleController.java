package eseo.fr.robineau.backend.controller;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.employee.EmployeeService;
import eseo.fr.robineau.backend.service.title.Title;
import eseo.fr.robineau.backend.service.title.TitleId;
import eseo.fr.robineau.backend.service.title.TitleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/employees/{empNo}/titles")
@CrossOrigin(origins = "http://localhost:8081")
public class TitleController {
    private final TitleService titleService;
    private final EmployeeService employeeService;

    public TitleController(TitleService titleService, EmployeeService employeeService) {
        this.titleService = titleService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Title> getTitles(@PathVariable Integer empNo, @RequestParam int page, @RequestParam int size) {
        Employee employee = employeeService.getEmployeeById(empNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return titleService.getTitlesByEmployee(employee, PageRequest.of(page, size));
    }

    @PostMapping
    public Title createTitle(@PathVariable Integer empNo, @RequestBody Title title) {
        Employee employee = employeeService.getEmployeeById(empNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        title.setEmployees(employee);  // utiliser setEmployees au lieu de setEmpNo

        TitleId titleId = new TitleId();
        titleId.setEmpNo(empNo);
        titleId.setFromDate(title.getId().getFromDate());
        title.setId(titleId);

        return titleService.saveTitle(title);
    }

    @DeleteMapping("/{fromDate}")
    public void deleteTitle(@PathVariable Integer empNo, @PathVariable LocalDate fromDate) {
        TitleId titleId = new TitleId();
        titleId.setEmpNo(empNo);
        titleId.setFromDate(fromDate);

        titleService.deleteTitle(titleId);
    }
}
