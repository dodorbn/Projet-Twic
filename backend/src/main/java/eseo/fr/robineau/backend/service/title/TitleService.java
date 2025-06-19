package eseo.fr.robineau.backend.service.title;

import eseo.fr.robineau.backend.service.employee.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TitleService {
    List<Title> getTitlesByEmployee(Employee employee, Pageable pageable);
    Title saveTitle(Title title);
    void deleteTitle(TitleId titleId);
}
