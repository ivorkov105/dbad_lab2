package dbad.labs.dbad_lab1.domain.mappers;


import dbad.labs.dbad_lab1.data.entities.InventoryEntity;
import dbad.labs.dbad_lab1.data.entities.ProductEntity;
import dbad.labs.dbad_lab1.data.entities.ShopEntity;
import dbad.labs.dbad_lab1.domain.dtos.InventoryDTO;

public class InventoryMapper {

    public static InventoryDTO toDto(InventoryEntity entity) {
        if (entity == null) return null;

        InventoryDTO dto = new InventoryDTO();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());

        if (entity.getShop() != null) {
            dto.setShopId(entity.getShop().getId());
            dto.setShopName(entity.getShop().getShopName());
        }

        if (entity.getProduct() != null) {
            dto.setProductId(entity.getProduct().getId());
            dto.setProductName(entity.getProduct().getProdName());
            dto.setProductUnit(entity.getProduct().getUnit());
        }

        return dto;
    }

    public static void updateEntity(InventoryEntity entity, InventoryDTO dto, ShopEntity shop, ProductEntity product) {
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());

        if (shop != null) entity.setShop(shop);
        if (product != null) entity.setProduct(product);
    }
}