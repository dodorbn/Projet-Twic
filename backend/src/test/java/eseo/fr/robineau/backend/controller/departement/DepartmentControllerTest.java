package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.Department;
import eseo.fr.robineau.backend.service.departement.DepartmentService;
import eseo.fr.robineau.backend.service.departement.DeptEmp;
import eseo.fr.robineau.backend.service.departement.DeptEmpService;
import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.title.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private DeptEmpService deptEmpService;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    void constructor_shouldInitialize() {
        DepartmentController controller = new DepartmentController(departmentService, departmentMapper, deptEmpService);
        assertNotNull(controller);
    }

    @Test
    void getDepartmentWithCurrentEmployees_shouldReturnEmployeesList() {
        DeptEmp deptEmp = new DeptEmp();
        Department dept = new Department();
        dept.setDeptNo("d001");

        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setHireDate(LocalDate.now());
        employee.setBirthDate(LocalDate.now());

        Title title = new Title();
        title.setTitle("Engineer");
        employee.setTitles(Set.of(title));

        Salary salary = new Salary();
        salary.setSalary(50000);
        employee.setSalaries(Set.of(salary));

        deptEmp.setDepartment(dept);
        deptEmp.setEmployees(employee);

        when(deptEmpService.getCurrentDeptEmp("d001", 0, 10)).thenReturn(List.of(deptEmp));

        ResponseEntity<List<EmployeeDto>> response =
                departmentController.getDepartmentWithCurrentEmployees("d001", 0, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<EmployeeDto> employees = response.getBody();
        assertNotNull(employees);
        assertEquals(1, employees.size());

        EmployeeDto dto = employees.get(0);
        assertEquals(1, dto.getId());
        assertEquals("Engineer", dto.getTitle());
        assertEquals(50000, dto.getSalary());
    }

    @Test
    void getDepartmentWithCurrentEmployees_shouldHandleEmptyTitlesAndSalaries() {
        DeptEmp deptEmp = new DeptEmp();
        Department dept = new Department();
        dept.setDeptNo("d001");

        Employee employee = new Employee();
        employee.setId(1);
        employee.setTitles(Set.of());
        employee.setSalaries(Set.of());

        deptEmp.setDepartment(dept);
        deptEmp.setEmployees(employee);

        when(deptEmpService.getCurrentDeptEmp("d001", 0, 10))
                .thenReturn(List.of(deptEmp));

        ResponseEntity<List<EmployeeDto>> response =
                departmentController.getDepartmentWithCurrentEmployees("d001", 0, 10);

        List<EmployeeDto> employees = response.getBody();
        assertNotNull(employees);
        EmployeeDto dto = employees.get(0);
        assertNull(dto.getTitle());
        assertNull(dto.getSalary());
    }

    @Test
    void getAllDepartments_shouldHandlePageNumber() {
        when(departmentService.getDepartments(4, 10)).thenReturn(List.of());
        departmentController.getAllDepartments(5, 10);
        verify(departmentService).getDepartments(4, 10);
    }

    @Test
    void getDepartment_shouldReturnDepartment() {
        Department department = new Department();
        department.setDeptNo("d001");
        when(departmentService.getDepartment("d001")).thenReturn(department);
        when(departmentMapper.toDto(department)).thenReturn(new DepartmentDto("d001", "Marketing", List.of()));

        DepartmentDto result = departmentController.getDepartment("d001");

        assertNotNull(result);
        verify(departmentService).getDepartment("d001");
        verify(departmentMapper).toDto(department);
    }

    @Test
    void getDepartment_shouldThrowNotFound() {
        when(departmentService.getDepartment("d001")).thenReturn(null);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> departmentController.getDepartment("d001")
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void createDepartment_shouldCreateAndReturnDepartment() {
        DepartmentRequestDto requestDto = new DepartmentRequestDto("d001", "Marketing");
        Department department = new Department();
        when(departmentService.createDepartment(any())).thenReturn(department);
        when(departmentMapper.toDto(department)).thenReturn(new DepartmentDto("d001", "Marketing", List.of()));

        DepartmentDto result = departmentController.createDepartment(requestDto);

        assertNotNull(result);
        verify(departmentService).createDepartment(any());
        verify(departmentMapper).toDto(department);
    }

    @Test
    void updateDepartment_shouldUpdateAndReturnDepartment() {
        DepartmentRequestDto requestDto = new DepartmentRequestDto("d001", "Marketing");
        Department department = new Department();
        when(departmentService.updateDepartment(eq("d001"), any())).thenReturn(department);
        when(departmentMapper.toDto(department)).thenReturn(new DepartmentDto("d001", "Marketing", List.of()));

        DepartmentDto result = departmentController.updateDepartment("d001", requestDto);

        assertNotNull(result);
        verify(departmentService).updateDepartment(eq("d001"), any());
        verify(departmentMapper).toDto(department);
    }

    @Test
    void updateDepartment_shouldThrowNotFoundWhenUpdatedIsNull() {
        DepartmentRequestDto requestDto = new DepartmentRequestDto("d001", "Marketing");
        when(departmentService.updateDepartment(eq("d001"), any())).thenReturn(null);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> departmentController.updateDepartment("d001", requestDto)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Department not found", exception.getReason());
    }

    @Test
    void deleteDepartment_shouldDeleteDepartment() {
        departmentController.deleteDepartment("d001");
        verify(departmentService).deleteDepartment("d001");
    }
}