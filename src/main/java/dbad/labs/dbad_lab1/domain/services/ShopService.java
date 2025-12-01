package dbad.labs.dbad_lab1.domain.services;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.daos.ShopDAO;
import dbad.labs.dbad_lab1.data.entities.ShopEntity;
import dbad.labs.dbad_lab1.domain.dtos.ShopDTO;
import dbad.labs.dbad_lab1.domain.mappers.ShopMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ShopService {
    private final ShopDAO shopDAO;

    @Inject
    public ShopService(ShopDAO shopDAO) { this.shopDAO = shopDAO; }

    public List<ShopDTO> findAll() {
        return shopDAO.findAll().stream().map(ShopMapper::toDto).collect(Collectors.toList());
    }

    public List<ShopDTO> search(String keyword) {
        return shopDAO.search(keyword).stream().map(ShopMapper::toDto).collect(Collectors.toList());
    }

    public void create(ShopDTO dto) {
        shopDAO.save(ShopMapper.toEntity(dto));
    }

    public void update(ShopDTO dto) {
        ShopEntity entity = shopDAO.findById(dto.getId());
        if (entity != null) {
            ShopMapper.updateEntity(entity, dto);
            shopDAO.update(entity);
        }
    }

    public void delete(ShopDTO dto) {
        ShopEntity entity = shopDAO.findById(dto.getId());
        if (entity != null) shopDAO.delete(entity);
    }
}