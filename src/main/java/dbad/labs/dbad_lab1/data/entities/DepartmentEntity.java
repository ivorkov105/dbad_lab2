package dbad.labs.dbad_lab1.data.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @Column(name = "depart_name", nullable = false, length = 255)
    private String departName;

    @OneToMany(mappedBy = "department")
    private List<PassportEntity> passportEntities;

    public DepartmentEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public List<PassportEntity> getPassports() {
        return passportEntities;
    }

    public void setPassports(List<PassportEntity> passportEntities) {
        this.passportEntities = passportEntities;
    }
}