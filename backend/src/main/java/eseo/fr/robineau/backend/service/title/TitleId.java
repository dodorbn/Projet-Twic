package eseo.fr.robineau.backend.service.title;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class TitleId implements java.io.Serializable {
    private static final long serialVersionUID = 5225184780150651241L;
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TitleId entity = (TitleId) o;
        return Objects.equals(this.fromDate, entity.fromDate) &&
                Objects.equals(this.empNo, entity.empNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, empNo);
    }

}