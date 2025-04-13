package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Descriptor.WalkStatus.WalkStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Descriptor.WalkStatus.WalkStatusVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DataInitializer.WalkStatusInitializer;
import com.example.petoasisbackend.Exception.WalkStatus.*;
import com.example.petoasisbackend.Mapper.WalkStatusMapper;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Repository.WalkStatusRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.WalkStatus.WalkStatusAddRequest;
import com.example.petoasisbackend.Request.WalkStatus.WalkStatusUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WalkStatusService {
    @Autowired
    private WalkStatusRepository walkStatusRepository;
    @Autowired
    private WalkStatusMapper walkStatusMapper;

    public List<ModelDTO<WalkStatus>> getWalkStatuses(DataDetailLevel level) {
        List<WalkStatus> data = walkStatusRepository.findAll();
        var mapper = walkStatusMapper.getMapper(level);

        return data.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<WalkStatus> getWalkStatusById(Integer id, DataDetailLevel level) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsById(id)) {
            throw new WalkStatusDoesntExistException("Cannot get status because walk status with id '" + id + "' doesnt exist");
        }

        WalkStatus status = walkStatusRepository.findById(id).get();
        var mapper = walkStatusMapper.getMapper(level);

        return mapper.apply(status);
    }

    public WalkStatusVerboseDTO getWalkStatusByName(String name) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsByStatus(name)) {
            throw new WalkStatusDoesntExistException("Cannot get status because walk status with name '" + name + "' doesnt exist");
        }
        WalkStatus status = walkStatusRepository.getWalkStatusByStatus(name);
        return WalkStatusVerboseDTO.fromWalkStatus(status);
    }

    public WalkStatus getWalkStatusReferenceByName(String name) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsByStatus(name)) {
            throw new WalkStatusDoesntExistException("Cannot get status because walk status with name '" + name + "' doesnt exist");
        }
        WalkStatus status = walkStatusRepository.getWalkStatusByStatus(name);
        return status;
    }

    public WalkStatusMinimumDTO addWalkStatus(WalkStatusAddRequest status) throws WalkStatusAlreadyExistsException {
        if (walkStatusRepository.existsByStatus(status.getStatus())) {
            throw new WalkStatusAlreadyExistsException("Cannot add new status because '" + status.getStatus() + "' walk status already exists");
        }

        WalkStatus newStatus = new WalkStatus(status.getStatus());
        WalkStatus savedStatus = walkStatusRepository.save(newStatus);

        return WalkStatusMinimumDTO.fromWalkStatus(savedStatus);
    }

    public void deleteWalkStatus(Integer statusId) throws WalkStatusDoesntExistException, WalkStatusCannotBeModifiedException {
        if (!walkStatusRepository.existsById(statusId)) {
            throw new WalkStatusDoesntExistException("Cannot delete status because walk status with id '" + statusId + "' doesnt exist");
        }
        WalkStatus status = walkStatusRepository.getReferenceById(statusId);
        if (WalkStatusInitializer.coreStatuses.contains(status.getStatus())) {
            throw new WalkStatusCannotBeModifiedException("Cannot delete core walk status");
        }
        walkStatusRepository.delete(status);
    }

    public WalkStatusVerboseDTO updateWalkStatusName(Integer id, WalkStatusUpdateRequest updated) throws WalkStatusDoesntExistException, WalkStatusUpdateCollisionException, WalkStatusCannotBeModifiedException {
        if (!walkStatusRepository.existsById(id)) {
            throw new WalkStatusDoesntExistException("Cannot update walk status with id '" + id + "' because it doesnt exist");
        }
        if (walkStatusRepository.existsByStatus(updated.getStatus())) {
            throw new WalkStatusUpdateCollisionException("Cannot update walk status with id '" + id + "' to '" + updated.getStatus() + "' because '" + updated.getStatus() + "' already exists");
        }

        WalkStatus status = walkStatusRepository.findById(id).get();

        if (WalkStatusInitializer.coreStatuses.contains(status.getStatus())) {
            throw new WalkStatusCannotBeModifiedException("Cannot modify core walk status");
        }

        status.setStatus(updated.getStatus());
        WalkStatus savedStatus = walkStatusRepository.save(status);

        return WalkStatusVerboseDTO.fromWalkStatus(savedStatus);
    }
}
