package eseo.fr.robineau.backend.controller.departement;

import eseo.fr.robineau.backend.controller.employee.EmployeeDto;
import eseo.fr.robineau.backend.service.departement.Department;
import eseo.fr.robineau.backend.service.departement.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    void getAllDepartments_shouldReturnDepartmentsList() {
        // Setup
        Department dept = new Department();
        dept.setDeptNo("d001");
        dept.setDeptName("Marketing");
        List<Department> departments = List.of(dept);
        List<EmployeeDto> employees = List.of();
        DepartmentDto deptDto = new DepartmentDto("d001", "Marketing", employees);

        when(departmentService.getDepartments(anyInt(), anyInt())).thenReturn(departments);
        when(departmentMapper.toListDto(departments)).thenReturn(List.of(deptDto));

        // Execution
        List<DepartmentDto> result = departmentController.getAllDepartments(1, 10);

        // Verification
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(departmentService).getDepartments(0, 10);
        verify(departmentMapper).toListDto(departments);
    }

    @Test
    void getDepartment_shouldReturnDepartment() {
        // Setup
        Department dept = new Department();
        dept.setDeptNo("d001");
        dept.setDeptName("Marketing");
        List<EmployeeDto> employees = List.of();
        DepartmentDto deptDto = new DepartmentDto("d001", "Marketing", employees);

        when(departmentService.getDepartment("d001")).thenReturn(dept);
        when(departmentMapper.toDto(dept)).thenReturn(deptDto);

        // Execution
        DepartmentDto result = departmentController.getDepartment("d001");

        // Verification
        assertNotNull(result);
        assertEquals("d001", result.deptNo());
        verify(departmentService).getDepartment("d001");
    }

    @Test
    void getDepartment_shouldThrowNotFound() {
        // Setup
        when(departmentService.getDepartment("invalid")).thenReturn(null);

        // Execution & Verification
        assertThrows(ResponseStatusException.class, () ->
            departmentController.getDepartment("invalid")
        );
        verify(departmentService).getDepartment("invalid");
    }

    @Test
    void createDepartment_shouldReturnCreatedDepartment() {
        // Setup
        DepartmentRequestDto requestDto = new DepartmentRequestDto("d001", "Marketing");
        Department dept = new Department();
        dept.setDeptNo("d001");
        dept.setDeptName("Marketing");
        List<EmployeeDto> employees = List.of();
        DepartmentDto deptDto = new DepartmentDto("d001", "Marketing", employees);

        when(departmentService.createDepartment(any())).thenReturn(dept);
        when(departmentMapper.toDto(dept)).thenReturn(deptDto);

        // Execution
        DepartmentDto result = departmentController.createDepartment(requestDto);

        // Verification
        assertNotNull(result);
        assertEquals("d001", result.deptNo());
        verify(departmentService).createDepartment(any());
    }

    @Test
    void deleteDepartment_shouldCallService() {
        // Execution
        departmentController.deleteDepartment("d001");

        // Verification
        verify(departmentService).deleteDepartment("d001");
    }

    @Test
    void updateDepartment_shouldReturnUpdatedDepartment() {
        // Setup
        DepartmentRequestDto requestDto = new DepartmentRequestDto("d001", "Marketing Updated");
        Department dept = new Department();
        dept.setDeptNo("d001");
        dept.setDeptName("Marketing Updated");
        List<EmployeeDto> employees = List.of();
        DepartmentDto deptDto = new DepartmentDto("d001", "Marketing Updated", employees);

        when(departmentService.updateDepartment(eq("d001"), any())).thenReturn(dept);
        when(departmentMapper.toDto(dept)).thenReturn(deptDto);

        // Execution
        DepartmentDto result = departmentController.updateDepartment("d001", requestDto);

        // Verification
        assertNotNull(result);
        assertEquals("Marketing Updated", result.deptName());
        verify(departmentService).updateDepartment(eq("d001"), any());
    }
}