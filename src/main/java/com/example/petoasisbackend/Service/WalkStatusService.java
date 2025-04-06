package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Descriptior.WalkStatusDTO;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusUpdateCollisionException;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Repository.WalkStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalkStatusService {
    @Autowired
    private WalkStatusRepository walkStatusRepository;

    public List<WalkStatus> getWalkStatuses() {
        return walkStatusRepository.findAll();
    }

    public WalkStatus getWalkStatusById(Integer id) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsById(id)) {
            throw new WalkStatusDoesntExistException("Cannot get status because walk status with id '" + id + "' doesnt exist");
        }
        return walkStatusRepository.findById(id).get();
    }

    public WalkStatus addWalkStatus(WalkStatusDTO status) throws WalkStatusAlreadyExistsException {
        if (walkStatusRepository.existsByStatus(status.getStatus())) {
            throw new WalkStatusAlreadyExistsException("Cannot add new status because '" + status.getStatus() + "' walk status already exists");
        }
        WalkStatus newStatus = new WalkStatus();
        newStatus.setStatus(status.getStatus());
        return walkStatusRepository.save(newStatus);
    }

    public WalkStatus deleteWalkStatus(Integer statusId) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsById(statusId)) {
            throw new WalkStatusDoesntExistException("Cannot delete status because walk status with id '" + statusId + "' doesnt exist");
        }
        WalkStatus status = walkStatusRepository.getReferenceById(statusId);
        walkStatusRepository.delete(status);
        return status;
    }

    public WalkStatus updateWalkStatusName(Integer id, WalkStatusDTO updated) throws WalkStatusDoesntExistException, WalkStatusUpdateCollisionException {
        if (!walkStatusRepository.existsById(id)) {
            throw new WalkStatusDoesntExistException("Cannot update walk status with id '"  + id + "' because it doesnt exist");
        }
        if (walkStatusRepository.existsByStatus(updated.getStatus())) {
            throw new WalkStatusUpdateCollisionException("Cannot update walk status with id '"  + id + "' to '" + updated.getStatus() + "' because '" + updated.getStatus() + "' already exists");
        }
        WalkStatus status = walkStatusRepository.findById(id).get();
        status.setStatus(updated.getStatus());
        walkStatusRepository.save(status);
        return status;
    }
}
