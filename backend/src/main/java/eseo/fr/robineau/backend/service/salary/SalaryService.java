package eseo.fr.robineau.backend.service.salary;

import java.util.List;

public interface SalaryService {
    List<Salary> getSalariesByEmployeeId(Integer empNo, Integer pageNo, Integer pageSize);
    Salary getLatestSalaryByEmployeeId(Integer empNo);
    Salary createSalary(Salary salary);
    void deleteSalary(SalaryId id);
}