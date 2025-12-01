package dbad.labs.dbad_lab1.domain.services;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.daos.DepartmentDAO;
import dbad.labs.dbad_lab1.data.entities.DepartmentEntity;
import dbad.labs.dbad_lab1.domain.dtos.DepartmentDTO;
import dbad.labs.dbad_lab1.domain.mappers.DepartmentMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentService {
    private final DepartmentDAO departmentDAO;

    @Inject
    public DepartmentService(DepartmentDAO departmentDAO) { this.departmentDAO = departmentDAO; }

    public List<DepartmentDTO> findAll() {
        return departmentDAO.findAll().stream().map(DepartmentMapper::toDto).collect(Collectors.toList());
    }

    public List<DepartmentDTO> search(String keyword) {
        return departmentDAO.search(keyword).stream().map(DepartmentMapper::toDto).collect(Collectors.toList());
    }

    public void create(DepartmentDTO dto) {
        departmentDAO.save(DepartmentMapper.toEntity(dto));
    }

    public void update(DepartmentDTO dto) {
        DepartmentEntity entity = departmentDAO.findById(dto.getId());
        if (entity != null) {
            DepartmentMapper.updateEntity(entity, dto);
            departmentDAO.update(entity);
        }
    }

    public void delete(DepartmentDTO dto) {
        DepartmentEntity entity = departmentDAO.findById(dto.getId());
        if (entity != null) departmentDAO.delete(entity);
    }
}