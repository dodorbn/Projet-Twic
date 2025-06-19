package eseo.fr.robineau.backend.controller;

import eseo.fr.robineau.backend.service.employee.Employee;
import eseo.fr.robineau.backend.service.employee.EmployeeService;
import eseo.fr.robineau.backend.service.title.Title;
import eseo.fr.robineau.backend.service.title.TitleId;
import eseo.fr.robineau.backend.service.title.TitleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TitleControllerTest {

    @Mock
    private TitleService titleService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private TitleController titleController;

    @Test
    void getTitles_WithValidEmployee_ShouldReturnTitlesList() {
        // Arrange
        Integer empNo = 1;
        Employee employee = new Employee();
        employee.setId(empNo);

        Title title1 = new Title();
        Title title2 = new Title();
        List<Title> expectedTitles = Arrays.asList(title1, title2);

        when(employeeService.getEmployeeById(empNo)).thenReturn(Optional.of(employee));
        when(titleService.getTitlesByEmployee(eq(employee), any(PageRequest.class)))
                .thenReturn(expectedTitles);

        // Act
        List<Title> result = titleController.getTitles(empNo, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeService).getEmployeeById(empNo);
        verify(titleService).getTitlesByEmployee(eq(employee), any(PageRequest.class));
    }

    @Test
    void getTitles_WithInvalidEmployee_ShouldThrowException() {
        // Arrange
        Integer empNo = 999;
        when(employeeService.getEmployeeById(empNo)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () ->
            titleController.getTitles(empNo, 0, 10)
        );
        verify(employeeService).getEmployeeById(empNo);
        verify(titleService, never()).getTitlesByEmployee(any(), any());
    }

    @Test
    void createTitle_WithValidEmployee_ShouldCreateTitle() {
        // Arrange
        Integer empNo = 1;
        Employee employee = new Employee();
        employee.setId(empNo);

        Title newTitle = new Title();
        TitleId titleId = new TitleId();
        titleId.setEmpNo(empNo);
        titleId.setFromDate(LocalDate.now());
        newTitle.setId(titleId);
        newTitle.setTitle("Senior Engineer");

        when(employeeService.getEmployeeById(empNo)).thenReturn(Optional.of(employee));
        when(titleService.saveTitle(any(Title.class))).thenReturn(newTitle);

        // Act
        Title result = titleController.createTitle(empNo, newTitle);

        // Assert
        assertNotNull(result);
        assertEquals("Senior Engineer", result.getTitle());
        verify(employeeService).getEmployeeById(empNo);
        verify(titleService).saveTitle(any(Title.class));
    }

    @Test
    void createTitle_WithInvalidEmployee_ShouldThrowException() {
        // Arrange
        Integer empNo = 999;
        Title newTitle = new Title();
        when(employeeService.getEmployeeById(empNo)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () ->
            titleController.createTitle(empNo, newTitle)
        );
        verify(employeeService).getEmployeeById(empNo);
        verify(titleService, never()).saveTitle(any());
    }

    @Test
    void deleteTitle_ShouldDeleteSuccessfully() {
        // Arrange
        Integer empNo = 1;
        LocalDate fromDate = LocalDate.now();
        doNothing().when(titleService).deleteTitle(any(TitleId.class));

        // Act & Assert
        assertDoesNotThrow(() ->
            titleController.deleteTitle(empNo, fromDate)
        );
        verify(titleService).deleteTitle(any(TitleId.class));
    }
}