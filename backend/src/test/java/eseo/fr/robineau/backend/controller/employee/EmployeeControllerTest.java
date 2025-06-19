package eseo.fr.robineau.backend.controller.employee;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void getAllEmployees_withoutLastName_shouldReturnAllEmployees() {
        List<Employee> employees = List.of(new Employee());
        List<EmployeeDto> expectedDtos = List.of(
                new EmployeeDto(1, "John", "Doe",
                        LocalDate.now(),
                        LocalDate.now().minusYears(30),
                        "IT", "Developer", 50000)
        );

        when(employeeService.getAllEmployees(0, 10)).thenReturn(employees);
        when(employeeMapper.toListDto(employees)).thenReturn(expectedDtos);

        List<EmployeeDto> result = employeeController.getAllEmployees(1, 10, null);

        assertEquals(expectedDtos, result);
        verify(employeeService).getAllEmployees(0, 10);
        verify(employeeMapper).toListDto(employees);
    }

    @Test
    void getAllEmployees_withLastName_shouldReturnFilteredEmployees() {
        List<Employee> employees = List.of(new Employee());
        List<EmployeeDto> expectedDtos = List.of(
                new EmployeeDto(1, "John", "Doe",
                        LocalDate.now(),
                        LocalDate.now().minusYears(30),
                        "IT", "Developer", 50000)
        );

        when(employeeService.getEmployeesByLastName("Doe", 0, 10)).thenReturn(employees);
        when(employeeMapper.toListDto(employees)).thenReturn(expectedDtos);

        List<EmployeeDto> result = employeeController.getAllEmployees(1, 10, "Doe");

        assertEquals(expectedDtos, result);
        verify(employeeService).getEmployeesByLastName("Doe", 0, 10);
        verify(employeeMapper).toListDto(employees);
    }

    @Test
    void getEmployeeById_whenEmployeeExists_shouldReturnEmployee() {
        Employee employee = new Employee();
        EmployeeDto expectedDto = new EmployeeDto(1, "John", "Doe",
                LocalDate.now(),
                LocalDate.now().minusYears(30),
                "IT", "Developer", 50000);

        when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(expectedDto);

        EmployeeDto result = employeeController.getEmployeeById(1);

        assertEquals(expectedDto, result);
        verify(employeeService).getEmployeeById(1);
        verify(employeeMapper).toDto(employee);
    }

    @Test
    void getEmployeeById_whenEmployeeNotFound_shouldThrowNotFoundException() {
        when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> employeeController.getEmployeeById(1)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Employee not found", exception.getReason());
    }

    @Test
    void createEmployee_shouldCreateAndReturnEmployee() {
        Employee employee = new Employee();
        when(employeeService.createEmployee(employee)).thenReturn(employee);

        Employee result = employeeController.createEmployee(employee);

        assertEquals(employee, result);
        verify(employeeService).createEmployee(employee);
    }

    @Test
    void updateEmployee_shouldUpdateAndReturnEmployee() {
        Employee employee = new Employee();
        when(employeeService.updateEmployee(1, employee)).thenReturn(employee);

        Employee result = employeeController.updateEmployee(1, employee);

        assertEquals(employee, result);
        verify(employeeService).updateEmployee(1, employee);
    }

    @Test
    void deleteEmployee_shouldCallService() {
        doNothing().when(employeeService).deleteEmployee(1);

        employeeController.deleteEmployee(1);

        verify(employeeService).deleteEmployee(1);
    }

    @Test
    void searchEmployees_shouldReturnMatchingEmployees() {
        List<Employee> employees = List.of(new Employee());
        List<EmployeeDto> expectedDtos = List.of(
                new EmployeeDto(1, "John", "Doe",
                        LocalDate.now(),
                        LocalDate.now().minusYears(30),
                        "IT", "Developer", 50000)
        );

        when(employeeService.searchEmployees("John")).thenReturn(employees);
        when(employeeMapper.toListDto(employees)).thenReturn(expectedDtos);

        List<EmployeeDto> result = employeeController.searchEmployees("John");

        assertEquals(expectedDtos, result);
        verify(employeeService).searchEmployees("John");
        verify(employeeMapper).toListDto(employees);
    }
}