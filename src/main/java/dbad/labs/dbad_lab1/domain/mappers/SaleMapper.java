package dbad.labs.dbad_lab1.domain.mappers;


import dbad.labs.dbad_lab1.data.entities.EmployeeEntity;
import dbad.labs.dbad_lab1.data.entities.ProductEntity;
import dbad.labs.dbad_lab1.data.entities.SaleEntity;
import dbad.labs.dbad_lab1.data.entities.ShopEntity;
import dbad.labs.dbad_lab1.domain.dtos.SaleDTO;

public class SaleMapper {

    public static SaleDTO toDto(SaleEntity entity) {
        if (entity == null) return null;

        SaleDTO dto = new SaleDTO();
        dto.setId(entity.getId());
        dto.setSaleDateTimestamp(entity.getSaleDateTimestamp());
        dto.setQuantitySold(entity.getQuantitySold());

        if (entity.getShop() != null) {
            dto.setShopId(entity.getShop().getId());
            dto.setShopName(entity.getShop().getShopName());
        }

        if (entity.getProduct() != null) {
            dto.setProductId(entity.getProduct().getId());
            dto.setProductName(entity.getProduct().getProdName());
            dto.setProductUnit(entity.getProduct().getUnit());
        }

        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getId());
            dto.setEmployeeFullName(entity.getEmployee().getLastName() + " " + entity.getEmployee().getFirstName());
        }

        return dto;
    }

    public static void updateEntity(SaleEntity entity, SaleDTO dto, ShopEntity shop, ProductEntity product, EmployeeEntity employee) {
        entity.setSaleDateTimestamp(dto.getSaleDateTimestamp());
        entity.setQuantitySold(dto.getQuantitySold());

        if (shop != null) entity.setShop(shop);
        if (product != null) entity.setProduct(product);
        if (employee != null) entity.setEmployee(employee);
    }
}