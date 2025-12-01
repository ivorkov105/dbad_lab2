package dbad.labs.dbad_lab1.domain.services;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.daos.ProductDAO;
import dbad.labs.dbad_lab1.data.entities.ProductEntity;
import dbad.labs.dbad_lab1.domain.dtos.ProductDTO;
import dbad.labs.dbad_lab1.domain.mappers.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductDAO productDAO;

    @Inject
    public ProductService(ProductDAO productDAO) { this.productDAO = productDAO; }

    public List<ProductDTO> findAll() {
        return productDAO.findAll().stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    public List<ProductDTO> search(String keyword) {
        return productDAO.search(keyword).stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    public void create(ProductDTO dto) {
        productDAO.save(ProductMapper.toEntity(dto));
    }

    public void update(ProductDTO dto) {
        ProductEntity entity = productDAO.findById(dto.getId());
        if (entity != null) {
            ProductMapper.updateEntity(entity, dto);
            productDAO.update(entity);
        }
    }

    public void delete(ProductDTO dto) {
        ProductEntity entity = productDAO.findById(dto.getId());
        if (entity != null) productDAO.delete(entity);
    }
}