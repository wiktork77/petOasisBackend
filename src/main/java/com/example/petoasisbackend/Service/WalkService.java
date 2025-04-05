package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Exception.Walk.WalkDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusUpdateCollisionException;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Repository.WalkRepository;
import com.example.petoasisbackend.Repository.WalkStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalkService {
    @Autowired
    private WalkRepository walkRepository;
    private WalkStatusRepository walkStatusRepository;

    public List<Walk> getWalks() {
        return walkRepository.findAll();
    }


    public Walk addWalk(Walk walk) {
        return walkRepository.save(walk);
    }

    public Walk removeWalk(Long id) throws WalkDoesntExistException {
        if (!walkRepository.existsById(id)) {
            throw new WalkDoesntExistException("Cannot delete walk with id " + id + ". It doesn't exist.");
        }
        Walk walk = walkRepository.getReferenceById(id);
        walkRepository.delete(walk);
        return walk;
    }

    public Walk updateWalkStatus(Long walkId, String status) throws WalkStatusDoesntExistException, WalkDoesntExistException {
        if (!walkStatusRepository.existsByStatus(status)) {
            throw new WalkStatusDoesntExistException(
                    "Cannot update walk status of walk with id '" + walkId + "' because status '" +
                            status + "' doesn't exist"
            );
        }
        if (!walkRepository.existsById(walkId)) {
            throw new WalkDoesntExistException(
                    "Cannot update walk status of walk with id '" + walkId + "' because walk with this id doesn't exist"
            );
        }
        Walk walk = walkRepository.getReferenceById(walkId);
        WalkStatus walkStatus = walkStatusRepository.getWalkStatusByStatus(status);
        walk.setWalkStatus(walkStatus);
        walkRepository.save(walk);
        return walk;
    }

}
