package eseo.fr.robineau.backend.controller.employee;

import eseo.fr.robineau.backend.service.departement.Department;
import eseo.fr.robineau.backend.service.departement.DeptEmp;
import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.salary.Salary;
import eseo.fr.robineau.backend.service.title.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperImplTest {

    @InjectMocks
    private EmployeeMapperImpl employeeMapper;

    @Test
    void toDto_withNullEmployee_shouldReturnNull() {
        assertNull(employeeMapper.toDto(null));
    }

    @Test
    void toDto_withValidEmployee_shouldMapAllFields() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setHireDate(LocalDate.of(2024, 1, 1));
        employee.setBirthDate(LocalDate.of(1990, 1, 1));

        DeptEmp deptEmp = new DeptEmp();
        Department dept = new Department();
        dept.setDeptNo("D001");
        deptEmp.setDepartment(dept);
        employee.setDeptEmps(Set.of(deptEmp));

        Title title = new Title();
        title.setTitle("Engineer");
        employee.setTitles(Set.of(title));

        Salary salary = new Salary();
        salary.setSalary(50000);
        employee.setSalaries(Set.of(salary));

        EmployeeDto result = employeeMapper.toDto(employee);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(LocalDate.of(2024, 1, 1), result.getHireDate());
        assertEquals(LocalDate.of(1990, 1, 1), result.getBirthDate());
        assertEquals("D001", result.getDepartment());
        assertEquals("Engineer", result.getTitle());
        assertEquals(50000, result.getSalary());
    }

    @Test
    void toDto_withEmptyEmployee_shouldReturnDtoWithNullFields() {
        Employee employee = new Employee();
        employee.setDeptEmps(Set.of());
        employee.setTitles(Set.of());
        employee.setSalaries(Set.of());

        EmployeeDto result = employeeMapper.toDto(employee);

        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getTitle());
        assertNull(result.getSalary());
    }

    @Test
    void toListDto_withNullList_shouldReturnNull() {
        assertNull(employeeMapper.toListDto(null));
    }

    @Test
    void toListDto_withEmptyList_shouldReturnEmptyList() {
        List<EmployeeDto> result = employeeMapper.toListDto(List.of());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toListDto_withValidEmployees_shouldMapAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setFirstName("John");
        employee1.setDeptEmps(Set.of());
        employee1.setTitles(Set.of());
        employee1.setSalaries(Set.of());

        Employee employee2 = new Employee();
        employee2.setId(2);
        employee2.setFirstName("Jane");
        employee2.setDeptEmps(Set.of());
        employee2.setTitles(Set.of());
        employee2.setSalaries(Set.of());

        List<EmployeeDto> result = employeeMapper.toListDto(List.of(employee1, employee2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals(2, result.get(1).getId());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    void toDto_withNullDepartmentInDeptEmp_shouldReturnDtoWithNullDepartment() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        DeptEmp deptEmpWithNullDept = new DeptEmp();
        deptEmpWithNullDept.setDepartment(null);
        employee.setDeptEmps(Set.of(deptEmpWithNullDept));

        employee.setTitles(Set.of());
        employee.setSalaries(Set.of());

        EmployeeDto result = employeeMapper.toDto(employee);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertNull(result.getDepartment());
    }
}