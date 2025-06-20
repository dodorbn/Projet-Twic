package eseo.fr.robineau.backend.service.departement;

import eseo.fr.robineau.backend.infrastructure.departement.DeptEmpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeptEmpServiceImplTest {

    @Mock
    private DeptEmpRepository deptEmpRepository;

    @InjectMocks
    private DeptEmpServiceImpl deptEmpService;

    private DeptEmp deptEmp;
    private String deptNo;

    @BeforeEach
    void setUp() {
        deptNo = "D001";
        deptEmp = new DeptEmp();
        Department dept = new Department();
        dept.setDeptNo(deptNo);
        deptEmp.setDepartment(dept);
    }

    @Test
    void getDeptEmp() {
        when(deptEmpRepository.findByDepartmentDeptNo(deptNo))
                .thenReturn(List.of(deptEmp));

        List<DeptEmp> result = deptEmpService.getDeptEmp(deptNo);

        assertEquals(1, result.size());
        verify(deptEmpRepository).findByDepartmentDeptNo(deptNo);
    }

    @Test
    void getCurrentDeptEmp() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        when(deptEmpRepository.findByDepartmentDeptNoAndToDateAfter(deptNo, LocalDate.now(), pageRequest))
                .thenReturn(List.of(deptEmp));

        List<DeptEmp> result = deptEmpService.getCurrentDeptEmp(deptNo, page, size);

        assertEquals(1, result.size());
        verify(deptEmpRepository).findByDepartmentDeptNoAndToDateAfter(deptNo, LocalDate.now(), pageRequest);
    }
}