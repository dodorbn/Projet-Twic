package eseo.fr.robineau.backend.api.departement;

import java.time.LocalDate;

public record DeptEmpDto(
        Integer empNo,
        String deptNo,
//        String firstName,
//        String lastName,
        LocalDate fromDate,
        LocalDate toDate
) {}
