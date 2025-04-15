package eseo.fr.robineau.backend.api.departement;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/departments")
@CrossOrigin(origins = "http://localhost:8081")
public class DepartmentController {

//  private final DepartmentService departmentService;
//  private final DepartmentMapper departmentMapper;
//
//  public DepartmentController(DepartmentService departmentService, DepartmentMapper departmentMapper) {
//    this.departmentService = departmentService;
//    this.departmentMapper = departmentMapper;
//  }
//
//  //http://localhost:8080/api/v1/departments?pageNo=1&pageSize=10
//  @GetMapping
//  public List<DepartmentDto> getDepartments(@RequestParam(defaultValue = "1") @Min(1) @NotNull Integer pageNo,
//                                            @RequestParam(defaultValue = "10") @Min(1) @NotNull Integer pageSize,
//                                            @RequestParam(required = false) String name) {
//    if (name != null) {
//      List<Department> department = departmentService.getDepartmentsFilterByName(name,pageNo - 1, pageSize);
//      return departmentMapper.toListDto(department);
//    } else {
//      List<Department> department = departmentService.getDepartments(pageNo - 1, pageSize);
//      return departmentMapper.toListDto(department);
//    }
//  }
//
//  @GetMapping("/{deptNo}")
//  public DepartmentDto getDepartment(@PathVariable  String deptNo) {
//    Department department = departmentService.getDepartment(deptNo);
//    return departmentMapper.toDto(department);
//  }
//
//  @PostMapping
//  public boolean createDepartment(@RequestBody DepartmentRequestDto departmentRequestDto) {
//    Department department = departmentMapper.toEntity(departmentRequestDto);
//    return departmentService.createDepartment(department);
//  }
//
//  @PutMapping("/{deptNo}")
//  public boolean updateDepartment(@PathVariable("deptNo") String deptNo, @RequestBody DepartmentRequestDto departmentRequestDto){
//    Department department = departmentMapper.toEntity(departmentRequestDto);
//    return departmentService.updateDepartment(deptNo,department);
//  }
//
//  @DeleteMapping("/{deptNo}")
//  public ResponseEntity<Void> deleteDepartment (@PathVariable("deptNo") String deptNo){
//    boolean deleteOk = departmentService.deleteDepartment(deptNo);
//    if (deleteOk){
//      return ResponseEntity.ok().build();
//    } else {
//      return ResponseEntity.noContent().build();
//    }
//  }

}
