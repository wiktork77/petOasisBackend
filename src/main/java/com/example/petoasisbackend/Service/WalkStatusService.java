package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Descriptor.WalkStatus.WalkStatusNameDTO;
import com.example.petoasisbackend.DTO.Descriptor.WalkStatus.WalkStatusMinimumDTO;
import com.example.petoasisbackend.DataInitializers.WalkStatusInitializer;
import com.example.petoasisbackend.Exception.WalkStatus.*;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Repository.WalkStatusRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalkStatusService {
    @Autowired
    private WalkStatusRepository walkStatusRepository;

    public Object getWalkStatuses(DataDetailLevel level) {
        List<WalkStatus> data = walkStatusRepository.findAll();
        System.out.println(level);
        switch (level) {
            case VERBOSE -> {return data;}
            case MINIMUM -> {
                return data.stream().map(WalkStatusMinimumDTO::fromWalkStatus).collect(Collectors.toList());
            }
            default -> {
                return data.stream().map(WalkStatusNameDTO::fromWalkStatus).collect(Collectors.toList());
            }
        }
    }

    public Object getWalkStatusById(Integer id, DataDetailLevel level) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsById(id)) {
            throw new WalkStatusDoesntExistException("Cannot get status because walk status with id '" + id + "' doesnt exist");
        }
        WalkStatus status = walkStatusRepository.findById(id).get();
        return dataDetailTransformStatus(status, level);
    }

    public WalkStatus getWalkStatusByName(String name) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsByStatus(name)) {
            throw new WalkStatusDoesntExistException("Cannot get status because walk status with name '" + name + "' doesnt exist");
        }
        return walkStatusRepository.getWalkStatusByStatus(name);
    }

    public WalkStatus addWalkStatus(WalkStatusNameDTO status) throws WalkStatusAlreadyExistsException, WalkStatusInvalidRequestException {
        if (walkStatusRepository.existsByStatus(status.getStatus())) {
            throw new WalkStatusAlreadyExistsException("Cannot add new status because '" + status.getStatus() + "' walk status already exists");
        }
        if (status.getStatus() == null || status.getStatus().trim().isEmpty()) {
            throw new WalkStatusInvalidRequestException("Cannot add new status because the request is not valid");
        }
        WalkStatus newStatus = new WalkStatus();
        newStatus.setStatus(status.getStatus());
        return walkStatusRepository.save(newStatus);
    }

    public WalkStatus deleteWalkStatus(Integer statusId) throws WalkStatusDoesntExistException, WalkStatusCannotBeModifiedException {
        if (!walkStatusRepository.existsById(statusId)) {
            throw new WalkStatusDoesntExistException("Cannot delete status because walk status with id '" + statusId + "' doesnt exist");
        }
        WalkStatus status = walkStatusRepository.getReferenceById(statusId);
        if (WalkStatusInitializer.coreStatuses.contains(status.getStatus())) {
            throw new WalkStatusCannotBeModifiedException("Cannot delete core walk status");
        }
        walkStatusRepository.delete(status);
        return status;
    }

    public WalkStatus updateWalkStatusName(Integer id, WalkStatusNameDTO updated) throws WalkStatusDoesntExistException, WalkStatusUpdateCollisionException, WalkStatusCannotBeModifiedException, WalkStatusInvalidRequestException {
        if (!walkStatusRepository.existsById(id)) {
            throw new WalkStatusDoesntExistException("Cannot update walk status with id '"  + id + "' because it doesnt exist");
        }
        if (walkStatusRepository.existsByStatus(updated.getStatus())) {
            throw new WalkStatusUpdateCollisionException("Cannot update walk status with id '"  + id + "' to '" + updated.getStatus() + "' because '" + updated.getStatus() + "' already exists");
        }
        if (updated.getStatus() == null || updated.getStatus().trim().isEmpty()) {
            throw new WalkStatusInvalidRequestException("Cannot update walk status with id '" + id + "' because the request is invalid");
        }


        WalkStatus status = walkStatusRepository.findById(id).get();

        if (WalkStatusInitializer.coreStatuses.contains(status.getStatus())) {
            throw new WalkStatusCannotBeModifiedException("Cannot modify core walk status");
        }

        status.setStatus(updated.getStatus());
        walkStatusRepository.save(status);
        return status;
    }

    private Object dataDetailTransformStatus(WalkStatus status, DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {return status;}
            case MINIMUM -> {return WalkStatusMinimumDTO.fromWalkStatus(status);}
            default -> {return WalkStatusNameDTO.fromWalkStatus(status);}
        }
    }
}
