package eseo.fr.robineau.backend.service.salary;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class SalaryId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 2814772787990443220L;
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SalaryId entity = (SalaryId) o;
        return Objects.equals(this.fromDate, entity.fromDate) &&
                Objects.equals(this.empNo, entity.empNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, empNo);
    }

}