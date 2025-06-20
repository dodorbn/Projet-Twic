package eseo.fr.robineau.backend.service.employee;

import eseo.fr.robineau.backend.infrastructure.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        employee.setHireDate(LocalDate.of(2020, 1, 1));
    }

    @Test
    void getAllEmployees() {
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(employeeRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<Employee> result = employeeService.getAllEmployees(0, 10);

        assertEquals(1, result.size());
        verify(employeeRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    void getEmployeeById() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeById(1);

        assertTrue(result.isPresent());
        assertEquals(employee, result.get());
    }

    @Test
    void getEmployeesByFirstName() {
        when(employeeRepository.findByFirstNameContainingIgnoreCase(anyString(), any(PageRequest.class)))
                .thenReturn(List.of(employee));

        List<Employee> result = employeeService.getEmployeesByFirstName("John", 0, 10);

        assertEquals(1, result.size());
        verify(employeeRepository).findByFirstNameContainingIgnoreCase("John", PageRequest.of(0, 10));
    }

    @Test
    void getEmployeesByLastName() {
        when(employeeRepository.findByLastNameContainingIgnoreCase(anyString(), any(PageRequest.class)))
                .thenReturn(List.of(employee));

        List<Employee> result = employeeService.getEmployeesByLastName("Doe", 0, 10);

        assertEquals(1, result.size());
        verify(employeeRepository).findByLastNameContainingIgnoreCase("Doe", PageRequest.of(0, 10));
    }

    @Test
    void createEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        assertEquals(employee, result);
        verify(employeeRepository).save(employee);
    }

    @Test
    void updateEmployee() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Jane");
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.updateEmployee(1, updatedEmployee);

        assertEquals("Jane", result.getFirstName());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void updateEmployee_notFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            employeeService.updateEmployee(1, new Employee())
        );
    }

    @Test
    void deleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1);

        employeeService.deleteEmployee(1);

        verify(employeeRepository).deleteById(1);
    }

    @Test
    void searchEmployees_withNumericQuery() {
        when(employeeRepository.findByIdOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(1, "1", "1"))
                .thenReturn(List.of(employee));

        List<Employee> result = employeeService.searchEmployees("1");

        assertEquals(1, result.size());
    }

    @Test
    void searchEmployees_withTextQuery() {
        when(employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("John", "John"))
                .thenReturn(List.of(employee));

        List<Employee> result = employeeService.searchEmployees("John");

        assertEquals(1, result.size());
    }
}