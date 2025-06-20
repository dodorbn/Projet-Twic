package eseo.fr.robineau.backend.service.salary;

import eseo.fr.robineau.backend.infrastructure.SalaryRepository;
import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaryServiceImplTest {

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private SalaryServiceImpl salaryService;

    private Employee employee;
    private Salary salary;
    private SalaryId salaryId;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);

        salary = new Salary();
        salary.setEmployees(employee);
        salary.setSalary(50000);
        salary.setToDate(LocalDate.now());
        salary.setToDate(LocalDate.now().plusYears(1));

        salaryId = new SalaryId();
        salaryId.setEmpNo(employee.getId());
        salaryId.setFromDate(salary.getToDate());
        salary.setId(salaryId);
    }

    @Test
    void getSalariesByEmployeeId() {
        when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));
        when(salaryRepository.findByEmployeesOrderByToDateDesc(eq(employee), any(PageRequest.class)))
                .thenReturn(List.of(salary));

        List<Salary> result = salaryService.getSalariesByEmployeeId(1, 0, 10);

        assertEquals(1, result.size());
        verify(employeeService).getEmployeeById(1);
        verify(salaryRepository).findByEmployeesOrderByToDateDesc(eq(employee), any(PageRequest.class));
    }

    @Test
    void getSalariesByEmployeeId_employeeNotFound() {
        when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

        assertThrows(Error.class, () -> salaryService.getSalariesByEmployeeId(1, 0, 10));
    }

    @Test
    void getLatestSalaryByEmployeeId() {
        when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));
        when(salaryRepository.findFirstByEmployeesOrderByToDateDesc(employee))
                .thenReturn(Optional.of(salary));

        Salary result = salaryService.getLatestSalaryByEmployeeId(1);

        assertEquals(salary, result);
        verify(employeeService).getEmployeeById(1);
        verify(salaryRepository).findFirstByEmployeesOrderByToDateDesc(employee);
    }

    @Test
    void getLatestSalaryByEmployeeId_employeeNotFound() {
        when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

        assertThrows(Error.class, () -> salaryService.getLatestSalaryByEmployeeId(1));
    }

    @Test
    void getLatestSalaryByEmployeeId_salaryNotFound() {
        when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));
        when(salaryRepository.findFirstByEmployeesOrderByToDateDesc(employee))
                .thenReturn(Optional.empty());

        assertThrows(Error.class, () -> salaryService.getLatestSalaryByEmployeeId(1));
    }

    @Test
    void createSalary() {
        when(salaryRepository.save(salary)).thenReturn(salary);

        Salary result = salaryService.createSalary(salary);

        assertEquals(salary, result);
        verify(salaryRepository).save(salary);
    }

    @Test
    void deleteSalary() {
        when(salaryRepository.existsById(salaryId)).thenReturn(true);
        doNothing().when(salaryRepository).deleteById(salaryId);

        salaryService.deleteSalary(salaryId);

        verify(salaryRepository).existsById(salaryId);
        verify(salaryRepository).deleteById(salaryId);
    }

    @Test
    void deleteSalary_notFound() {
        when(salaryRepository.existsById(salaryId)).thenReturn(false);

        assertThrows(Error.class, () -> salaryService.deleteSalary(salaryId));
        verify(salaryRepository).existsById(salaryId);
        verify(salaryRepository, never()).deleteById(any());
    }
}