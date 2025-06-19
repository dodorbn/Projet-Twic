package eseo.fr.robineau.backend.controller.departement;

import lombok.Getter;

@Getter
public class DepartmentRequestDto {
  private final String deptNo;
  private final String deptName;

  public DepartmentRequestDto(String deptNo, String deptName) {
    this.deptNo = deptNo;
    this.deptName = deptName;
  }

}
