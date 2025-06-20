package eseo.fr.robineau.backend.controller;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.employee.EmployeeService;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.salary.SalaryId;
import eseo.fr.robineau.backend.service.salary.SalaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaryControllerTest {

    @Mock
    private SalaryService salaryService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private SalaryController salaryController;

    @Test
    void getEmployeeSalaries_shouldReturnSalaryList() {
        List<Salary> expectedSalaries = List.of(new Salary(), new Salary());
        when(salaryService.getSalariesByEmployeeId(1, 0, 10)).thenReturn(expectedSalaries);

        List<Salary> result = salaryController.getEmployeeSalaries(1, 0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(salaryService).getSalariesByEmployeeId(1, 0, 10);
    }

    @Test
    void getLatestSalary_shouldReturnLatestSalary() {
        Salary expectedSalary = new Salary();
        expectedSalary.setSalary(50000);
        when(salaryService.getLatestSalaryByEmployeeId(1)).thenReturn(expectedSalary);

        Salary result = salaryController.getLatestSalary(1);

        assertNotNull(result);
        assertEquals(50000, result.getSalary());
        verify(salaryService).getLatestSalaryByEmployeeId(1);
    }

    @Test
    void createSalary_withExistingEmployee_shouldCreateSalary() {
        Employee employee = new Employee();
        employee.setId(1);
        Salary newSalary = new Salary();
        newSalary.setSalary(60000);

        when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));
        when(salaryService.createSalary(any(Salary.class))).thenReturn(newSalary);

        Salary result = salaryController.createSalary(1, newSalary);

        assertNotNull(result);
        assertEquals(60000, result.getSalary());
        verify(employeeService).getEmployeeById(1);
        verify(salaryService).createSalary(any(Salary.class));
    }

    @Test
    void createSalary_withNonExistingEmployee_shouldThrowException() {
        when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
            salaryController.createSalary(1, new Salary())
        );

        verify(employeeService).getEmployeeById(1);
        verify(salaryService, never()).createSalary(any());
    }

    @Test
    void createSalary_withNullSalaryId_shouldSetDefaultId() {
        Integer empNo = 1;
        Employee employee = new Employee();
        employee.setId(empNo);

        Salary newSalary = new Salary();
        newSalary.setSalary(60000);
        newSalary.setId(null);

        when(employeeService.getEmployeeById(empNo)).thenReturn(Optional.of(employee));
        when(salaryService.createSalary(any())).thenReturn(newSalary);

        salaryController.createSalary(empNo, newSalary);

        verify(salaryService).createSalary(argThat(salary ->
                salary.getId() != null &&
                        salary.getId().getEmpNo().equals(empNo) &&
                        salary.getId().getFromDate() != null &&
                        salary.getEmployees().equals(employee)
        ));
    }

    @Test
    void deleteSalary_shouldDeleteSalary() {
        LocalDate fromDate = LocalDate.now();
        SalaryId salaryId = new SalaryId(1, fromDate);

        doNothing().when(salaryService).deleteSalary(salaryId);

        assertDoesNotThrow(() -> salaryController.deleteSalary(1, fromDate));
        verify(salaryService).deleteSalary(salaryId);
    }
}