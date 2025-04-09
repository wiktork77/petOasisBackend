package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Descriptior.WalkStatus.WalkStatusDisplayConciseDTO;
import com.example.petoasisbackend.DTO.Descriptior.WalkStatus.WalkStatusDisplayMinimumDTO;
import com.example.petoasisbackend.DataInitializers.WalkStatusInitializer;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusUpdateCollisionException;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Repository.WalkStatusRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                return data.stream().map(WalkStatusDisplayMinimumDTO::fromWalkStatus);
            }
            default -> {
                return data.stream().map(WalkStatusDisplayConciseDTO::fromWalkStatus);
            }
        }
    }

    public WalkStatus getWalkStatusById(Integer id) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsById(id)) {
            throw new WalkStatusDoesntExistException("Cannot get status because walk status with id '" + id + "' doesnt exist");
        }
        return walkStatusRepository.findById(id).get();
    }

    public WalkStatus getWalkStatusByName(String name) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsByStatus(name)) {
            throw new WalkStatusDoesntExistException("Cannot get status because walk status with name '" + name + "' doesnt exist");
        }
        return walkStatusRepository.getWalkStatusByStatus(name);
    }

    public WalkStatus addWalkStatus(WalkStatusDisplayConciseDTO status) throws WalkStatusAlreadyExistsException {
        if (walkStatusRepository.existsByStatus(status.getStatus())) {
            throw new WalkStatusAlreadyExistsException("Cannot add new status because '" + status.getStatus() + "' walk status already exists");
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

    public WalkStatus updateWalkStatusName(Integer id, WalkStatusDisplayConciseDTO updated) throws WalkStatusDoesntExistException, WalkStatusUpdateCollisionException, WalkStatusCannotBeModifiedException {
        if (!walkStatusRepository.existsById(id)) {
            throw new WalkStatusDoesntExistException("Cannot update walk status with id '"  + id + "' because it doesnt exist");
        }
        if (walkStatusRepository.existsByStatus(updated.getStatus())) {
            throw new WalkStatusUpdateCollisionException("Cannot update walk status with id '"  + id + "' to '" + updated.getStatus() + "' because '" + updated.getStatus() + "' already exists");
        }

        WalkStatus status = walkStatusRepository.findById(id).get();

        if (WalkStatusInitializer.coreStatuses.contains(status.getStatus())) {
            throw new WalkStatusCannotBeModifiedException("Cannot modify core walk status");
        }

        status.setStatus(updated.getStatus());
        walkStatusRepository.save(status);
        return status;
    }
}
