package dbad.labs.dbad_lab1.domain.services;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.daos.EmployeeDAO;
import dbad.labs.dbad_lab1.data.daos.ProductDAO;
import dbad.labs.dbad_lab1.data.daos.SaleDAO;
import dbad.labs.dbad_lab1.data.daos.ShopDAO;
import dbad.labs.dbad_lab1.data.entities.SaleEntity;
import dbad.labs.dbad_lab1.domain.dtos.SaleDTO;
import dbad.labs.dbad_lab1.domain.mappers.SaleMapper;

import java.util.List;
import java.util.stream.Collectors;

public class SaleService {

    private final SaleDAO saleDAO;
    private final ShopDAO shopDAO;
    private final ProductDAO productDAO;
    private final EmployeeDAO employeeDAO;

    @Inject
    public SaleService(SaleDAO saleDAO, ShopDAO shopDAO, ProductDAO productDAO, EmployeeDAO employeeDAO) {
        this.saleDAO = saleDAO;
        this.shopDAO = shopDAO;
        this.productDAO = productDAO;
        this.employeeDAO = employeeDAO;
    }

    public List<SaleDTO> findAll() {
        return saleDAO.findAll().stream()
                .map(SaleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SaleDTO> search(String keyword) {
        return saleDAO.search(keyword).stream()
                .map(SaleMapper::toDto)
                .collect(Collectors.toList());
    }

    public void create(SaleDTO dto) {
        SaleEntity entity = new SaleEntity();
        // Маппим базовые поля (можно вынести в toEntity в маппере, если удобно)
        entity.setSaleDateTimestamp(dto.getSaleDateTimestamp());
        entity.setQuantitySold(dto.getQuantitySold());

        updateRelationships(entity, dto);
        saleDAO.save(entity);
    }

    public void update(SaleDTO dto) {
        SaleEntity entity = saleDAO.findById(dto.getId());
        if (entity != null) {
            updateRelationships(entity, dto);
            SaleMapper.updateEntity(entity, dto, entity.getShop(), entity.getProduct(), entity.getEmployee());
            saleDAO.update(entity);
        }
    }

    public void delete(SaleDTO dto) {
        SaleEntity entity = saleDAO.findById(dto.getId());
        if (entity != null) {
            saleDAO.delete(entity);
        }
    }

    private void updateRelationships(SaleEntity entity, SaleDTO dto) {
        if (dto.getShopId() != 0) {
            entity.setShop(shopDAO.findById(dto.getShopId()));
        }
        if (dto.getProductId() != 0) {
            entity.setProduct(productDAO.findById(dto.getProductId()));
        }
        if (dto.getEmployeeId() != 0) {
            entity.setEmployee(employeeDAO.findById(dto.getEmployeeId()));
        }
    }
}