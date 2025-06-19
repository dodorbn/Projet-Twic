package eseo.fr.robineau.backend.service.title;

import eseo.fr.robineau.backend.infrastructure.TitleRepository;
import eseo.fr.robineau.backend.service.employee.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleServiceImpl implements TitleService {
    private final TitleRepository titleRepository;

    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public List<Title> getTitlesByEmployee(Employee employee, Pageable pageable) {
        return titleRepository.findByEmployeesOrderByToDateDesc(employee, pageable);
    }

    @Override
    public Title saveTitle(Title title) {
        return titleRepository.save(title);
    }

    @Override
    public void deleteTitle(TitleId titleId) {
        titleRepository.deleteById(titleId);
    }
}
