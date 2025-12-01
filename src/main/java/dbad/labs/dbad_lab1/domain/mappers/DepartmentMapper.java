package dbad.labs.dbad_lab1.domain.mappers;


import dbad.labs.dbad_lab1.data.entities.DepartmentEntity;
import dbad.labs.dbad_lab1.domain.dtos.DepartmentDTO;

public class DepartmentMapper {

    public static DepartmentDTO toDto(DepartmentEntity entity) {
        if (entity == null) return null;

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(entity.getId());
        dto.setDepartName(entity.getDepartName());
        return dto;
    }

    public static DepartmentEntity toEntity(DepartmentDTO dto) {
        if (dto == null) return null;

        DepartmentEntity entity = new DepartmentEntity();
        // ID обычно не устанавливается при создании новой сущности, но нужен при обновлении
        if (dto.getId() != 0) entity.setId(dto.getId());
        entity.setDepartName(dto.getDepartName());
        return entity;
    }

    public static void updateEntity(DepartmentEntity entity, DepartmentDTO dto) {
        entity.setDepartName(dto.getDepartName());
    }
}