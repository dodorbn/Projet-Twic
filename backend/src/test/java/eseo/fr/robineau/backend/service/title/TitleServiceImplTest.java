package eseo.fr.robineau.backend.service.title;

import eseo.fr.robineau.backend.infrastructure.TitleRepository;
import eseo.fr.robineau.backend.service.employee.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TitleServiceImplTest {

    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private TitleServiceImpl titleService;

    private Employee employee;
    private Title title;
    private TitleId titleId;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);

        titleId = new TitleId();
        titleId.setEmpNo(1);
        titleId.setFromDate(LocalDate.now());

        title = new Title();
        title.setId(titleId);
        title.setTitle("Engineer");
    }

    @Test
    void getTitlesByEmployee() {
        Pageable pageable = PageRequest.of(0, 10);
        when(titleRepository.findByEmployeesOrderByToDateDesc(employee, pageable))
                .thenReturn(List.of(title));

        List<Title> result = titleService.getTitlesByEmployee(employee, pageable);

        assertEquals(1, result.size());
        verify(titleRepository).findByEmployeesOrderByToDateDesc(employee, pageable);
    }

    @Test
    void saveTitle() {
        when(titleRepository.save(title)).thenReturn(title);

        Title result = titleService.saveTitle(title);

        assertEquals(title, result);
        verify(titleRepository).save(title);
    }

    @Test
    void deleteTitle() {
        doNothing().when(titleRepository).deleteById(any(TitleId.class));

        titleService.deleteTitle(titleId);

        verify(titleRepository).deleteById(titleId);
    }
}