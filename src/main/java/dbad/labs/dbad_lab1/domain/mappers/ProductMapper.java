package dbad.labs.dbad_lab1.domain.mappers;


import dbad.labs.dbad_lab1.data.entities.ProductEntity;
import dbad.labs.dbad_lab1.domain.dtos.ProductDTO;

public class ProductMapper {

    public static ProductDTO toDto(ProductEntity entity) {
        if (entity == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setProdName(entity.getProdName());
        dto.setUnit(entity.getUnit());
        return dto;
    }

    public static ProductEntity toEntity(ProductDTO dto) {
        if (dto == null) return null;

        ProductEntity entity = new ProductEntity();
        if (dto.getId() != 0) entity.setId(dto.getId());
        entity.setProdName(dto.getProdName());
        entity.setUnit(dto.getUnit());
        return entity;
    }

    public static void updateEntity(ProductEntity entity, ProductDTO dto) {
        entity.setProdName(dto.getProdName());
        entity.setUnit(dto.getUnit());
    }
}