package com.example.petoasisbackend.Service;


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
    private WalkStatusRepository walkStatusRepository;
    @Autowired
    private WalkRepository walkRepository;

    public List<WalkStatus> getWalkStatuses() {
        return walkStatusRepository.findAll();
    }

    public List<Walk> getWalks() {
        return walkRepository.findAll();
    }


    public WalkStatus addWalkStatus(WalkStatus status) {
        if (walkStatusRepository.existsByStatus(status.getStatus())) {
            throw new IllegalArgumentException(status + " walk status already exists!");
        }
        return walkStatusRepository.save(status);
    }


    public Walk addWalk(Walk walk) {
        return walkRepository.save(walk);
    }

    public Walk removeWalk(Long id) {
        Walk walk = walkRepository.getReferenceById(id);
        walkRepository.delete(walk);
        return walk;
    }

    public Walk updateWalkStatus(Long walkId, String status) {
        if (!walkStatusRepository.existsByStatus(status)) {
            throw new IllegalArgumentException(status + " walk status doesnt exist!");
        }
        Walk walk = walkRepository.getReferenceById(walkId);
        WalkStatus walkStatus = walkStatusRepository.getWalkStatusByStatus(status);
        walk.setWalkStatus(walkStatus);
        walkRepository.save(walk);
        return walk;
    }

    public WalkStatus deleteWalkStatus(String statusName) {
        if (!walkStatusRepository.existsByStatus(statusName)) {
            throw new IllegalArgumentException(statusName + " walk status doesnt exist!");
        }
        WalkStatus status = walkStatusRepository.getWalkStatusByStatus(statusName);
        walkStatusRepository.delete(status);
        return status;
    }

    public WalkStatus updateWalkStatus(String name, String newName) {
        if (!walkStatusRepository.existsByStatus(name)) {
            throw new IllegalArgumentException(name + " walk status doesnt exist!");
        }
        if (!walkStatusRepository.existsByStatus(newName)) {
            throw new IllegalArgumentException(name + " walk status already exists!");
        }
        WalkStatus status = walkStatusRepository.getWalkStatusByStatus(name);
        status.setStatus(newName);
        walkStatusRepository.save(status);
        return status;
    }

}
