package dbad.labs.dbad_lab1.data.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passports")
public class PassportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private Long id;

    @Column(name = "passport_num", nullable = false, unique = true, length = 50)
    private String passportNum;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @OneToOne(mappedBy = "passport")
    private EmployeeEntity employee;

    public PassportEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity departmentEntity) {
        this.department = departmentEntity;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employeeEntity) {
        this.employee = employeeEntity;
    }
}
