package dbad.labs.dbad_lab1.domain.services;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.daos.EmployeeDAO;
import dbad.labs.dbad_lab1.data.daos.PassportDAO;
import dbad.labs.dbad_lab1.data.daos.ShopDAO;
import dbad.labs.dbad_lab1.data.entities.EmployeeEntity;
import dbad.labs.dbad_lab1.data.entities.PassportEntity;
import dbad.labs.dbad_lab1.data.entities.ShopEntity;
import dbad.labs.dbad_lab1.domain.dtos.EmployeeDTO;
import dbad.labs.dbad_lab1.domain.mappers.EmployeeMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final ShopDAO shopDAO;
    private final PassportDAO passportDAO;

    @Inject
    public EmployeeService(EmployeeDAO employeeDAO, ShopDAO shopDAO, PassportDAO passportDAO) {
        this.employeeDAO = employeeDAO;
        this.shopDAO = shopDAO;
        this.passportDAO = passportDAO;
    }

    public List<EmployeeDTO> findAll() {
        return employeeDAO.findAll().stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> search(String keyword) {
        return employeeDAO.search(keyword).stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public void create(EmployeeDTO dto) {
        EmployeeEntity entity = EmployeeMapper.toEntity(dto);

        if (dto.getShopId() != 0) {
            ShopEntity shop = shopDAO.findById(dto.getShopId());
            entity.setShop(shop);
        }

        if (dto.getPassportId() != 0) {
            PassportEntity passport = passportDAO.findById(dto.getPassportId());
            entity.setPassport(passport);
        }

        employeeDAO.save(entity);
    }

    public void update(EmployeeDTO dto) {
        EmployeeEntity entity = employeeDAO.findById(dto.getId());
        if (entity == null) return;

        ShopEntity shop = (dto.getShopId() != 0) ? shopDAO.findById(dto.getShopId()) : null;
        PassportEntity passport = (dto.getPassportId() != 0) ? passportDAO.findById(dto.getPassportId()) : null;

        EmployeeMapper.updateEntity(entity, dto, shop, passport);
        employeeDAO.update(entity);
    }

    public void delete(EmployeeDTO dto) {
        EmployeeEntity entity = employeeDAO.findById(dto.getId());
        if (entity != null) {
            employeeDAO.delete(entity);
        }
    }
}