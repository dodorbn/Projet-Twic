package eseo.fr.robineau.backend.api.departement;

public class DepartmentRequestDto {
  private final String deptNo;
  private final String deptName;

  public DepartmentRequestDto(String deptNo, String deptName) {
    this.deptNo = deptNo;
    this.deptName = deptName;
  }

  public String getDeptNo() {
    return deptNo;
  }

  public String getDeptName() {
    return deptName;
  }

}
