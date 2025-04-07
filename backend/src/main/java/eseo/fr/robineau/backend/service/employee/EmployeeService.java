package eseo.fr.robineau.backend.service.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAllEmployees(Integer pageNo, Integer pageSize);

    Optional<Employee> getEmployeeById(Integer id);

    List<Employee> getEmployeesByLastName(String lastName, Integer pageNo, Integer pageSize);

    List<Employee> getEmployeesByFirstName(String firstName, Integer pageNo, Integer pageSize);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Integer id, Employee employee);

    void deleteEmployee(Integer id);

    List<Employee> searchEmployees(String query);
}
