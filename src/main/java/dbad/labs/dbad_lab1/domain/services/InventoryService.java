package dbad.labs.dbad_lab1.domain.services;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.daos.InventoryDAO;
import dbad.labs.dbad_lab1.data.daos.ProductDAO;
import dbad.labs.dbad_lab1.data.daos.ShopDAO;
import dbad.labs.dbad_lab1.data.entities.InventoryEntity;
import dbad.labs.dbad_lab1.data.entities.ProductEntity;
import dbad.labs.dbad_lab1.data.entities.ShopEntity;
import dbad.labs.dbad_lab1.domain.dtos.InventoryDTO;
import dbad.labs.dbad_lab1.domain.mappers.InventoryMapper;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {

    private final InventoryDAO inventoryDAO;
    private final ShopDAO shopDAO;
    private final ProductDAO productDAO;

    @Inject
    public InventoryService(InventoryDAO inventoryDAO, ShopDAO shopDAO, ProductDAO productDAO) {
        this.inventoryDAO = inventoryDAO;
        this.shopDAO = shopDAO;
        this.productDAO = productDAO;
    }

    public List<InventoryDTO> findAll() {
        return inventoryDAO.findAll().stream()
                .map(InventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<InventoryDTO> search(String keyword) {
        return inventoryDAO.search(keyword).stream()
                .map(InventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public void create(InventoryDTO dto) {
        InventoryEntity entity = new InventoryEntity();
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());

        if (dto.getShopId() != 0) entity.setShop(shopDAO.findById(dto.getShopId()));
        if (dto.getProductId() != 0) entity.setProduct(productDAO.findById(dto.getProductId()));

        inventoryDAO.save(entity);
    }

    public void update(InventoryDTO dto) {
        InventoryEntity entity = inventoryDAO.findById(dto.getId());
        if (entity == null) return;

        ShopEntity shop = (dto.getShopId() != 0) ? shopDAO.findById(dto.getShopId()) : null;
        ProductEntity product = (dto.getProductId() != 0) ? productDAO.findById(dto.getProductId()) : null;

        InventoryMapper.updateEntity(entity, dto, shop, product);
        inventoryDAO.update(entity);
    }

    public void delete(InventoryDTO dto) {
        InventoryEntity entity = inventoryDAO.findById(dto.getId());
        if (entity != null) inventoryDAO.delete(entity);
    }
}