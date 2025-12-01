package dbad.labs.dbad_lab1.domain.dtos;

public class DepartmentDTO {
    private long id;
    private String departName;

    public DepartmentDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}