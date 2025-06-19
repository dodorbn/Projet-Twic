package eseo.fr.robineau.backend.controller.employee;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeDtoTest {

    @Test
    void constructor_shouldSetAllFields() {
        LocalDate hireDate = LocalDate.now();
        LocalDate birthDate = LocalDate.now().minusYears(30);

        EmployeeDto dto = new EmployeeDto(1, "John", "Doe", hireDate, birthDate, "D001", "Engineer", 50000);

        assertEquals(1, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals(hireDate, dto.getHireDate());
        assertEquals(birthDate, dto.getBirthDate());
        assertEquals("D001", dto.getDepartment());
        assertEquals("Engineer", dto.getTitle());
        assertEquals(50000, dto.getSalary());
    }

    @Test
    void setters_shouldUpdateAllFields() {
        EmployeeDto dto = new EmployeeDto(1, "John", "Doe", LocalDate.now(), LocalDate.now(), "D001", "Engineer", 50000);
        LocalDate newHireDate = LocalDate.now().minusYears(1);
        LocalDate newBirthDate = LocalDate.now().minusYears(25);

        dto.setId(2);
        dto.setFirstName("Jane");
        dto.setLastName("Smith");
        dto.setHireDate(newHireDate);
        dto.setBirthDate(newBirthDate);
        dto.setDepartment("D002");
        dto.setTitle("Manager");
        dto.setSalary(60000);

        assertEquals(2, dto.getId());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals(newHireDate, dto.getHireDate());
        assertEquals(newBirthDate, dto.getBirthDate());
        assertEquals("D002", dto.getDepartment());
        assertEquals("Manager", dto.getTitle());
        assertEquals(60000, dto.getSalary());
    }
}