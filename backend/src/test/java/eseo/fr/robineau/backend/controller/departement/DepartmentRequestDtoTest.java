package eseo.fr.robineau.backend.controller.departement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentRequestDtoTest {

    @Test
    void constructor_shouldCreateValidDto() {
        DepartmentRequestDto dto = new DepartmentRequestDto("d001", "Marketing");

        assertNotNull(dto);
        assertEquals("d001", dto.deptNo());
        assertEquals("Marketing", dto.deptName());
    }

    @Test
    void equals_shouldReturnTrueForSameValues() {
        DepartmentRequestDto dto1 = new DepartmentRequestDto("d001", "Marketing");
        DepartmentRequestDto dto2 = new DepartmentRequestDto("d001", "Marketing");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void toString_shouldReturnFormattedString() {
        DepartmentRequestDto dto = new DepartmentRequestDto("d001", "Marketing");

        String expected = "DepartmentRequestDto[deptNo=d001, deptName=Marketing]";
        assertEquals(expected, dto.toString());
    }

    @Test
    void accessors_shouldReturnCorrectValues() {
        DepartmentRequestDto dto = new DepartmentRequestDto("d001", "Marketing");

        assertEquals("d001", dto.deptNo());
        assertEquals("Marketing", dto.deptName());
    }
}