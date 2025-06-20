package eseo.fr.robineau.backend.service.departement;

import eseo.fr.robineau.backend.infrastructure.departement.DeptManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeptManagerServiceImplTest {

    @Mock
    private DeptManagerRepository deptManagerRepository;

    @InjectMocks
    private DeptManagerServiceImpl deptManagerService;

    private DeptManager deptManager;
    private String deptNo;

    @BeforeEach
    void setUp() {
        deptNo = "D001";
        deptManager = new DeptManager();
        Department dept = new Department();
        dept.setDeptNo(deptNo);
        deptManager.setDepartment(dept);
    }

    @Test
    void getDeptManager() {
        when(deptManagerRepository.findByDepartmentDeptNo(deptNo))
                .thenReturn(List.of(deptManager));

        List<DeptManager> result = deptManagerService.getDeptManager(deptNo);

        assertEquals(1, result.size());
        verify(deptManagerRepository).findByDepartmentDeptNo(deptNo);
    }

    @Test
    void getCurrentDeptManager() {
        when(deptManagerRepository.findByDepartmentDeptNoAndToDateAfter(deptNo, LocalDate.now()))
                .thenReturn(List.of(deptManager));

        List<DeptManager> result = deptManagerService.getCurrentDeptManager(deptNo);

        assertEquals(1, result.size());
        verify(deptManagerRepository).findByDepartmentDeptNoAndToDateAfter(deptNo, LocalDate.now());
    }
}