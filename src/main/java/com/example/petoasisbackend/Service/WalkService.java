package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Activity.Walk.WalkDisplayMinimumDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.DataDetail.DataDetailLevelDoesntExistException;
import com.example.petoasisbackend.Exception.Walk.WalkCannotBeDeletedException;
import com.example.petoasisbackend.Exception.Walk.WalkDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Repository.AnimalRepository;
import com.example.petoasisbackend.Repository.WalkRepository;
import com.example.petoasisbackend.Repository.WalkStatusRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Tools.Time.Period;
import com.example.petoasisbackend.Tools.Time.TimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalkService {
    @Autowired
    private WalkRepository walkRepository;
    private WalkStatusRepository walkStatusRepository;

    private AnimalRepository animalRepository;

    private List<Walk> getWalks() {
        List<Walk> data = walkRepository.findAll();
        return data;
    }

    public Object getWalks(DataDetailLevel level) throws DataDetailLevelDoesntExistException {

        if (!Arrays.asList(DataDetailLevel.values()).contains(level)) {
            throw new DataDetailLevelDoesntExistException("Cannot parse request, data detail level '" + level.name() + "' doesn't exist");
        }

        List<Walk> data = walkRepository.findAll();

        switch (level) {
            case VERBOSE -> {
                return data;
            }
            case MEDIUM -> {
                System.out.println("m");
            }
            case CONCISE -> {
                System.out.println("c");
            }
            case MINIMUM -> {
                System.out.println("h");
            }
        }

        return data;

    }


    public Walk addWalk(Walk walk) {
        return walkRepository.save(walk);
    }

    public Walk removeWalk(Long id) throws WalkDoesntExistException, WalkCannotBeDeletedException {
        List<String> allowedStatuses = List.of("Finished", "Cancelled");
        if (!walkRepository.existsById(id)) {
            throw new WalkDoesntExistException("Cannot delete walk with id '" + id + "'. It doesn't exist.");
        }

        Walk walk = walkRepository.getReferenceById(id);

        if (!allowedStatuses.contains(walk.getWalkStatus().getStatus())) {
            throw new WalkCannotBeDeletedException("Cannot delete walk with id '" + id + "'. Can only delete walks with statuses 'Finished' and 'Cancelled'. Consider changing the status first");
        }

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

    public Boolean checkIfAnimalIsAvailableForWalk(Long animalId, LocalDateTime start, LocalDateTime end) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(animalId)) {
            throw new AnimalDoesntExistException(
                    "Cannot check animal walk availability, because animal with id '" + animalId + "' doesn't exist"
            );
        }
        Animal animal = animalRepository.findById(animalId).get();

        TimeParser parser = new TimeParser();
        Period wantedWalkPeriod = new Period(start, end);

        List<Walk> walks = getWalks();

        for (Walk w : walks) {
            if (w.getPupil().getAnimalId().equals(animalId)) {
                Period walkPeriod = new Period(w.getStartTime(), w.getEndTime());
                if (parser.doPeriodsIntersect(wantedWalkPeriod, walkPeriod)) {
                    return true;
                }
            }
        }

        return false;
    }

}
