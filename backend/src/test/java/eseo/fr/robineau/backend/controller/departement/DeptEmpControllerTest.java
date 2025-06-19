package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.Department;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeptEmpControllerTest {

    @Mock
    private DeptEmpService deptEmpService;

    @InjectMocks
    private DeptEmpController deptEmpController;

    @Test
    void getDepartmentEmployees_shouldReturnListOfDeptEmp() {
        DeptEmp deptEmp = createDeptEmp();
        when(deptEmpService.getDeptEmp("d001")).thenReturn(List.of(deptEmp));

        List<DeptEmp> result = deptEmpController.getDepartmentEmployees("d001");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(deptEmpService).getDeptEmp("d001");
    }

    @Test
    void getCurrentDepartmentEmployees_shouldReturnEmployeeDtoList() {
        DeptEmp deptEmp = createDeptEmp();
        when(deptEmpService.getCurrentDeptEmp(eq("d001"), anyInt(), anyInt()))
                .thenReturn(List.of(deptEmp));

        List<EmployeeDto> result = deptEmpController.getCurrentDepartmentEmployees("d001", 0, 20);

        assertNotNull(result);
        assertEquals(1, result.size());
        EmployeeDto dto = result.get(0);
        assertEquals(1, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("d001", dto.getDepartment());
        assertEquals("Engineer", dto.getTitle());
        assertEquals(50000, dto.getSalary());
        verify(deptEmpService).getCurrentDeptEmp("d001", 0, 20);
    }

    private DeptEmp createDeptEmp() {
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
        title.setTitle("Engineer");
        employee.setTitles(Set.of(title));

        Salary salary = new Salary();
        salary.setSalary(50000);
        employee.setSalaries(Set.of(salary));

        DeptEmp deptEmp = new DeptEmp();
        deptEmp.setDepartment(department);
        deptEmp.setEmployees(employee);

        return deptEmp;
    }
}