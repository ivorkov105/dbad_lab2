package dbad.labs.dbad_lab1.domain.mappers;


import dbad.labs.dbad_lab1.data.entities.DepartmentEntity;
import dbad.labs.dbad_lab1.data.entities.PassportEntity;
import dbad.labs.dbad_lab1.domain.dtos.PassportDTO;

public class PassportMapper {

    public static PassportDTO toDto(PassportEntity entity) {
        if (entity == null) return null;

        PassportDTO dto = new PassportDTO();
        dto.setId(entity.getId());
        dto.setPassportNum(entity.getPassportNum());
        dto.setIssueDate(entity.getIssueDate());

        if (entity.getDepartment() != null) {
            dto.setDepartmentId(entity.getDepartment().getId());
            dto.setDepartmentName(entity.getDepartment().getDepartName());
        }

        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getId());
            dto.setEmployeeFullName(entity.getEmployee().getLastName() + " " + entity.getEmployee().getFirstName());
        }

        return dto;
    }

    public static void updateEntity(PassportEntity entity, PassportDTO dto, DepartmentEntity department) {
        entity.setPassportNum(dto.getPassportNum());
        entity.setIssueDate(dto.getIssueDate());

        if (department != null) {
            entity.setDepartment(department);
        }
    }
}