package dbad.labs.dbad_lab1.domain.mappers;


import dbad.labs.dbad_lab1.data.entities.ShopEntity;
import dbad.labs.dbad_lab1.domain.dtos.ShopDTO;

public class ShopMapper {

    public static ShopDTO toDto(ShopEntity entity) {
        if (entity == null) return null;

        ShopDTO dto = new ShopDTO();
        dto.setId(entity.getId());
        dto.setShopName(entity.getShopName());
        dto.setAddress(entity.getAddress());
        return dto;
    }

    public static ShopEntity toEntity(ShopDTO dto) {
        if (dto == null) return null;

        ShopEntity entity = new ShopEntity();
        if (dto.getId() != 0) entity.setId(dto.getId());
        entity.setShopName(dto.getShopName());
        entity.setAddress(dto.getAddress());
        return entity;
    }

    public static void updateEntity(ShopEntity entity, ShopDTO dto) {
        entity.setShopName(dto.getShopName());
        entity.setAddress(dto.getAddress());
    }
}