package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.Department;
import eseo.fr.robineau.backend.service.departement.DeptManager;
import eseo.fr.robineau.backend.service.departement.DeptManagerService;
import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.title.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeptManagerControllerTest {

    @Mock
    private DeptManagerService deptManagerService;

    @InjectMocks
    private DeptManagerController deptManagerController;

    private DeptManager createDeptManager() {
        Department department = new Department();
        department.setDeptNo("d001");
        department.setDeptName("Marketing");

        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setBirthDate(LocalDate.now().minusYears(30));
        employee.setHireDate(LocalDate.now().minusYears(5));

        Title title = new Title();
        title.setTitle("Manager");
        employee.setTitles(Set.of(title));

        Salary salary = new Salary();
        salary.setSalary(80000);
        employee.setSalaries(Set.of(salary));

        DeptManager deptManager = new DeptManager();
        deptManager.setDepartment(department);
        deptManager.setEmployees(employee);

        return deptManager;
    }

    @Test
    void getDepartmentManagers_shouldReturnListOfEmployeeDto() {
        DeptManager deptManager = createDeptManager();
        when(deptManagerService.getDeptManager("d001")).thenReturn(List.of(deptManager));

        List<EmployeeDto> result = deptManagerController.getDepartmentManagers("d001");

        assertNotNull(result);
        assertEquals(1, result.size());

        EmployeeDto dto = result.get(0);
        assertEquals(1, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("d001", dto.getDepartment());
        assertEquals("Manager", dto.getTitle());
        assertEquals(80000, dto.getSalary());
        assertNotNull(dto.getHireDate());
        assertNotNull(dto.getBirthDate());

        verify(deptManagerService).getDeptManager("d001");
    }

    @Test
    void getDepartmentManagers_shouldHandleNullDepartment() {
        DeptManager deptManager = new DeptManager();
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setTitles(Set.of());
        employee.setSalaries(Set.of());
        employee.setBirthDate(LocalDate.now().minusYears(30));
        employee.setHireDate(LocalDate.now().minusYears(5));

        deptManager.setDepartment(null);
        deptManager.setEmployees(employee);

        when(deptManagerService.getDeptManager("d001")).thenReturn(List.of(deptManager));

        List<EmployeeDto> result = deptManagerController.getDepartmentManagers("d001");

        assertNotNull(result);
        assertEquals(1, result.size());

        EmployeeDto dto = result.get(0);
        assertNotNull(dto);
        assertNull(dto.getDepartment());
        assertEquals(1, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertNull(dto.getTitle());
        assertNull(dto.getSalary());
        assertNotNull(dto.getHireDate());
        assertNotNull(dto.getBirthDate());
    }

    @Test
    void getCurrentDepartmentManagers_shouldReturnListOfDeptManager() {
        DeptManager deptManager = createDeptManager();
        when(deptManagerService.getCurrentDeptManager("d001")).thenReturn(List.of(deptManager));

        List<DeptManager> result = deptManagerController.getCurrentDepartmentManagers("d001");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("d001", result.get(0).getDepartment().getDeptNo());
        assertEquals(1, result.get(0).getEmployees().getId());

        verify(deptManagerService).getCurrentDeptManager("d001");
    }

    @Test
    void getDepartmentManagers_shouldReturnEmptyList() {
        when(deptManagerService.getDeptManager("d001")).thenReturn(List.of());

        List<EmployeeDto> result = deptManagerController.getDepartmentManagers("d001");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(deptManagerService).getDeptManager("d001");
    }

    @Test
    void getCurrentDepartmentManagers_shouldReturnEmptyList() {
        when(deptManagerService.getCurrentDeptManager("d001")).thenReturn(List.of());

        List<DeptManager> result = deptManagerController.getCurrentDepartmentManagers("d001");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(deptManagerService).getCurrentDeptManager("d001");
    }
}