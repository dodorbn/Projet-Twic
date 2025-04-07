package eseo.fr.robineau.backend.service.employee;

import eseo.fr.robineau.backend.infrastructure.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees(Integer pageNo, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageNo, pageSize)).getContent();
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getEmployeesByFirstName(String firstName, Integer pageNo, Integer pageSize) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(firstName, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public List<Employee> getEmployeesByLastName(String lastName, Integer pageNo, Integer pageSize) {
        return employeeRepository.findByLastNameContainingIgnoreCase(lastName, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(updatedEmployee.getFirstName());
                    existing.setLastName(updatedEmployee.getLastName());
                    existing.setBirthDate(updatedEmployee.getBirthDate());
                    existing.setHireDate(updatedEmployee.getHireDate());
                    return employeeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> searchEmployees(String query) {
        Integer empNo = null;
        try {
            empNo = Integer.parseInt(query);
        } catch (NumberFormatException ignored) {
        }

        return employeeRepository.findByIdOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                empNo, query, query
        );
    }
}
