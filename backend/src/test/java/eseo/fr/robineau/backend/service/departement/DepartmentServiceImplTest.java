package eseo.fr.robineau.backend.service.departement;

import eseo.fr.robineau.backend.infrastructure.departement.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void getDepartments() {
        Department dept = new Department();
        Page<Department> page = new PageImpl<>(List.of(dept));
        when(departmentRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<Department> result = departmentService.getDepartments(0, 10);

        assertEquals(1, result.size());
        verify(departmentRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    void getDepartment() {
        Department dept = new Department();
        String deptNo = "D001";
        when(departmentRepository.findById(deptNo)).thenReturn(Optional.of(dept));

        Department result = departmentService.getDepartment(deptNo);

        assertEquals(dept, result);
        verify(departmentRepository).findById(deptNo);
    }

    @Test
    void getDepartment_notFound() {
        String deptNo = "D001";
        when(departmentRepository.findById(deptNo)).thenReturn(Optional.empty());

        Department result = departmentService.getDepartment(deptNo);

        assertNull(result);
        verify(departmentRepository).findById(deptNo);
    }

    @Test
    void getDepartmentByName() {
        Department dept = new Department();
        String deptName = "HR";
        when(departmentRepository.findByDeptName(deptName)).thenReturn(dept);

        Department result = departmentService.getDepartmentByName(deptName);

        assertEquals(dept, result);
        verify(departmentRepository).findByDeptName(deptName);
    }

    @Test
    void createDepartment() {
        Department dept = new Department();
        when(departmentRepository.save(dept)).thenReturn(dept);

        Department result = departmentService.createDepartment(dept);

        assertEquals(dept, result);
        verify(departmentRepository).save(dept);
    }

    @Test
    void updateDepartment() {
        String deptNo = "D001";
        Department existingDept = new Department();
        Department updatedDept = new Department();
        updatedDept.setDeptName("NewName");

        when(departmentRepository.findById(deptNo)).thenReturn(Optional.of(existingDept));
        when(departmentRepository.save(existingDept)).thenReturn(existingDept);

        Department result = departmentService.updateDepartment(deptNo, updatedDept);

        assertEquals("NewName", result.getDeptName());
        verify(departmentRepository).findById(deptNo);
        verify(departmentRepository).save(existingDept);
    }

    @Test
    void updateDepartment_notFound() {
        String deptNo = "D001";
        Department dept = new Department();
        when(departmentRepository.findById(deptNo)).thenReturn(Optional.empty());

        Department result = departmentService.updateDepartment(deptNo, dept);

        assertNull(result);
        verify(departmentRepository).findById(deptNo);
    }

    @Test
    void deleteDepartment() {
        String deptNo = "D001";
        doNothing().when(departmentRepository).deleteById(deptNo);

        departmentService.deleteDepartment(deptNo);

        verify(departmentRepository).deleteById(deptNo);
    }
}