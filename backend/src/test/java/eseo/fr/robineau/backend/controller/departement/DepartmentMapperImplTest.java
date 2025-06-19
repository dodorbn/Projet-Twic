package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;
import eseo.fr.robineau.backend.controller.employee.EmployeeMapper;
import eseo.fr.robineau.backend.service.departement.Department;
import eseo.fr.robineau.backend.service.departement.DeptManager;
import eseo.fr.robineau.backend.service.employee.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentMapperImplTest {

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private DepartmentMapperImpl departmentMapper;

    @Test
    void toDto_shouldMapDepartmentToDto() {
        Department department = new Department();
        department.setDeptNo("d001");
        department.setDeptName("Marketing");

        Employee manager = new Employee();
        manager.setId(1);  // Changé de setEmpNo à setId
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setBirthDate(LocalDate.now());
        manager.setHireDate(LocalDate.now());

        DeptManager deptManager = new DeptManager();
        deptManager.setEmployees(manager);

        department.setDeptManagers(new LinkedHashSet<>(List.of(deptManager)));  // Utilisation de LinkedHashSet

        EmployeeDto managerDto = new EmployeeDto(1, "John", "Doe", LocalDate.now(), LocalDate.now(), null, null, null);
        when(employeeMapper.toDto(manager)).thenReturn(managerDto);

        DepartmentDto result = departmentMapper.toDto(department);

        assertNotNull(result);
        assertEquals("d001", result.deptNo());
        assertEquals("Marketing", result.deptName());
        assertEquals(1, result.managers().size());
        assertEquals(managerDto, result.managers().get(0));
    }

    @Test
    void toListDto_shouldMapDepartmentListToDtoList() {
        Department dept1 = new Department();
        dept1.setDeptNo("d001");
        dept1.setDeptName("Marketing");
        dept1.setDeptManagers(new LinkedHashSet<>());

        Department dept2 = new Department();
        dept2.setDeptNo("d002");
        dept2.setDeptName("Finance");
        dept2.setDeptManagers(new LinkedHashSet<>());

        List<Department> departments = List.of(dept1, dept2);

        List<DepartmentDto> result = departmentMapper.toListDto(departments);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("d001", result.get(0).deptNo());
        assertEquals("Marketing", result.get(0).deptName());
        assertEquals("d002", result.get(1).deptNo());
        assertEquals("Finance", result.get(1).deptName());
    }

    @Test
    void toDto_shouldHandleEmptyManagersList() {
        Department department = new Department();
        department.setDeptNo("d001");
        department.setDeptName("Marketing");
        department.setDeptManagers(new LinkedHashSet<>()); // Pas de managers

        DepartmentDto result = departmentMapper.toDto(department);

        assertNotNull(result);
        assertEquals("d001", result.deptNo());
        assertEquals("Marketing", result.deptName());
        assertTrue(result.managers().isEmpty());
    }
}