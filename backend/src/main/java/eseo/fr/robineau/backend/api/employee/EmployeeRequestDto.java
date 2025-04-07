package eseo.fr.robineau.backend.api.employee;

import java.time.LocalDate;

public class EmployeeRequestDto {
    LocalDate birth_date;
    String first_name;
    String last_name;
    LocalDate hire_date;

    public EmployeeRequestDto (LocalDate birth_date, String first_name, String last_name, LocalDate hire_date){
        this.birth_date = birth_date;
        this.first_name = first_name;
        this.last_name = last_name;
        this.hire_date = hire_date;
    }
    public LocalDate getBirth_date(){
        return birth_date;
    }

    public String getFirst_name(){
        return first_name;
    }

    public String getLast_name(){
        return last_name;
    }

    public LocalDate getHire_date(){
        return hire_date;
    }

}
