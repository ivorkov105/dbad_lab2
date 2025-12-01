package dbad.labs.dbad_lab1.domain.mappers;


import dbad.labs.dbad_lab1.data.entities.EmployeeEntity;
import dbad.labs.dbad_lab1.data.entities.PassportEntity;
import dbad.labs.dbad_lab1.data.entities.ShopEntity;
import dbad.labs.dbad_lab1.domain.dtos.EmployeeDTO;

public class EmployeeMapper {

    public static EmployeeDTO toDto(EmployeeEntity entity) {
        if (entity == null) return null;

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setAddress(entity.getAddress());
        dto.setBirthDate(entity.getBirthDate());

        if (entity.getPassport() != null) {
            dto.setPassportId(entity.getPassport().getId());
            dto.setPassportNum(entity.getPassport().getPassportNum());
        }

        if (entity.getShop() != null) {
            dto.setShopId(entity.getShop().getId());
            dto.setShopName(entity.getShop().getShopName());
        }

        return dto;
    }

    // Для создания сущности
    public static EmployeeEntity toEntity(EmployeeDTO dto) {
        if (dto == null) return null;
        EmployeeEntity entity = new EmployeeEntity();
        // Базовые поля
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setAddress(dto.getAddress());
        entity.setBirthDate(dto.getBirthDate());
        return entity;
    }

    // Обновление полей и связей
    public static void updateEntity(EmployeeEntity entity, EmployeeDTO dto, ShopEntity shop, PassportEntity passport) {
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setAddress(dto.getAddress());
        entity.setBirthDate(dto.getBirthDate());

        if (shop != null) {
            entity.setShop(shop);
        }

        if (passport != null) {
            entity.setPassport(passport);
        }
    }
}