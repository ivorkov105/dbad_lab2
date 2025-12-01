package dbad.labs.dbad_lab1.domain.services;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.daos.DepartmentDAO;
import dbad.labs.dbad_lab1.data.daos.PassportDAO;
import dbad.labs.dbad_lab1.data.entities.DepartmentEntity;
import dbad.labs.dbad_lab1.data.entities.PassportEntity;
import dbad.labs.dbad_lab1.domain.dtos.PassportDTO;
import dbad.labs.dbad_lab1.domain.mappers.PassportMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PassportService {

    private final PassportDAO passportDAO;
    private final DepartmentDAO departmentDAO;

    @Inject
    public PassportService(PassportDAO passportDAO, DepartmentDAO departmentDAO) {
        this.passportDAO = passportDAO;
        this.departmentDAO = departmentDAO;
    }

    public List<PassportDTO> findAll() {
        return passportDAO.findAll().stream()
                .map(PassportMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PassportDTO> search(String keyword) {
        return passportDAO.search(keyword).stream()
                .map(PassportMapper::toDto)
                .collect(Collectors.toList());
    }

    public void create(PassportDTO dto) {
        PassportEntity entity = new PassportEntity();
        entity.setPassportNum(dto.getPassportNum());
        entity.setIssueDate(dto.getIssueDate());

        if (dto.getDepartmentId() != 0) {
            entity.setDepartment(departmentDAO.findById(dto.getDepartmentId()));
        }
        passportDAO.save(entity);
    }

    public void update(PassportDTO dto) {
        PassportEntity entity = passportDAO.findById(dto.getId());
        if (entity == null) return;

        DepartmentEntity department = (dto.getDepartmentId() != 0) ? departmentDAO.findById(dto.getDepartmentId()) : null;

        PassportMapper.updateEntity(entity, dto, department);
        passportDAO.update(entity);
    }

    public void delete(PassportDTO dto) {
        PassportEntity entity = passportDAO.findById(dto.getId());
        if (entity != null) passportDAO.delete(entity);
    }
}