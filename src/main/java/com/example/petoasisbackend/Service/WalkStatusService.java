package com.example.petoasisbackend.Service;

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

    public WalkStatus addWalkStatus(WalkStatus status) throws WalkStatusAlreadyExistsException {
        if (walkStatusRepository.existsByStatus(status.getStatus())) {
            throw new WalkStatusAlreadyExistsException("Cannot add new status because '" + status.getStatus() + "' walk status already exists");
        }
        return walkStatusRepository.save(status);
    }

    public WalkStatus deleteWalkStatus(String statusName) throws WalkStatusDoesntExistException {
        if (!walkStatusRepository.existsByStatus(statusName)) {
            throw new WalkStatusDoesntExistException("Cannot delete status because '" + statusName + "'" + " walk status doesnt exist");
        }
        WalkStatus status = walkStatusRepository.getWalkStatusByStatus(statusName);
        walkStatusRepository.delete(status);
        return status;
    }

    public WalkStatus updateWalkStatusName(String name, String newName) throws WalkStatusDoesntExistException, WalkStatusUpdateCollisionException {
        if (!walkStatusRepository.existsByStatus(name)) {
            throw new WalkStatusDoesntExistException("Cannot update walk status '"  + name + "' because it doesnt exist");
        }
        if (walkStatusRepository.existsByStatus(newName)) {
            throw new WalkStatusUpdateCollisionException("Cannot update walk status '"  + name + "' to " + newName + " because " + newName + " already exists");
        }
        WalkStatus status = walkStatusRepository.getWalkStatusByStatus(name);
        status.setStatus(newName);
        walkStatusRepository.save(status);
        return status;
    }
}
