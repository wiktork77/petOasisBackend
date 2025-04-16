package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Activity.Walk.WalkMinimumDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkWithStatusDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkWithTimeDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DataInitializer.WalkStatusInitializer;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Exception.Walk.WalkDoesntExistException;
import com.example.petoasisbackend.Exception.Walk.WalkInvalidStatusChangeException;
import com.example.petoasisbackend.Exception.Walk.WalkTimeIntersectException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Mapper.Activity.Walk.WalkMapper;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Repository.*;
import com.example.petoasisbackend.Request.Activity.Walk.WalkAddRequest;
import com.example.petoasisbackend.Request.Activity.Walk.WalkTimeUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.Status.WalkStatus.WalkStatusUpdateRequest;
import com.example.petoasisbackend.Tools.Time.TimeInterval;
import com.example.petoasisbackend.Tools.Time.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalkService {
    @Autowired
    private WalkRepository walkRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ShelterRepository shelterRepository;


    @Autowired
    private WalkMapper walkMapper;
    @Autowired
    private WalkStatusRepository walkStatusRepository;


    public List<ModelDTO<Walk>> get(DataDetailLevel level) {
        List<Walk> walks = walkRepository.findAll();
        var mapper = walkMapper.getMapper(level);

        return walks.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<Walk> getById(Long id, DataDetailLevel level) throws WalkDoesntExistException {
        if (!walkRepository.existsById(id)) {
            throw new WalkDoesntExistException("Cannot get walk with id '" + id + "' because it doesn't exist");
        }

        Walk walk = walkRepository.findById(id).get();
        var mapper = walkMapper.getMapper(level);

        return mapper.apply(walk);
    }

    public WalkMinimumDTO add(WalkAddRequest request) throws AnimalDoesntExistException, PersonDoesntExistException, ShelterDoesntExistException, WalkTimeIntersectException {
        if (!animalRepository.existsById(request.getAnimalId())) {
            throw new AnimalDoesntExistException("Cannot get animal with id '" + request.getAnimalId() + "' because it doesn't exist");
        }

        if (!personRepository.existsById(request.getPersonId())) {
            throw new PersonDoesntExistException("Cannot get person with id '" + request.getPersonId() + "' because it doesn't exist!");
        }

        if (!shelterRepository.existsByShelterId(request.getShelterId())) {
            throw new ShelterDoesntExistException("Cannot get shelter with id '" + request.getShelterId() + "' because it doesn't exist");
        }
        TimeInterval walkInterval = new TimeInterval(request.getStartTime(), request.getEndTime());

        if (!isPersonCapableOfWalk(request.getPersonId(), walkInterval)) {
            throw new WalkTimeIntersectException("Cannot add walk because the person has another activity that intersects with given time period");
        }

        if (!isAnimalCapableOfWalk(request.getAnimalId(), walkInterval)) {
            throw new WalkTimeIntersectException("Cannot add walk because the animal has another activity that intersects with given time period");
        }

        Animal pupil = animalRepository.findById(request.getAnimalId()).get();
        Person caretaker = personRepository.findById(request.getPersonId()).get();
        Shelter supervisor = shelterRepository.findById(request.getShelterId()).get();

        Walk walk = new Walk(
                pupil,
                caretaker,
                supervisor,
                request.getStartTime(),
                request.getEndTime(),
                walkStatusRepository.getWalkStatusByStatus("Pending")
        );

        Walk savedWalk = walkRepository.save(walk);

        return WalkMinimumDTO.fromWalk(savedWalk);
    }

    public WalkWithStatusDTO updateStatus(Long walkId, WalkStatusUpdateRequest request) throws WalkDoesntExistException, WalkStatusDoesntExistException, WalkInvalidStatusChangeException {
        if (!walkRepository.existsById(walkId)) {
            throw new WalkDoesntExistException("Cannot update walk status of walk with id '" + walkId + "' because walk with this id doesn't exist");
        }

        if (!walkStatusRepository.existsByStatus(request.getStatus())) {
            throw new WalkStatusDoesntExistException("Cannot update walk status of walk with id '" + walkId + "' because status '" + request.getStatus() + "' doesn't exist");
        }

        if (!isStatusChangeValid(walkId, request)) {
            throw new WalkInvalidStatusChangeException("Invalid walk status change");
        }

        WalkStatus status = walkStatusRepository.getWalkStatusByStatus(request.getStatus());

        Walk walk = walkRepository.findById(walkId).get();
        walk.setWalkStatus(status);
        walkRepository.save(walk);

        return WalkWithStatusDTO.fromWalk(walk);
    }

    public WalkWithTimeDTO updateTime(Long walkId, WalkTimeUpdateRequest request) throws WalkTimeIntersectException, WalkDoesntExistException {
        if (walkRepository.existsById(walkId)) {
            throw new WalkDoesntExistException("Cannot update period of walk with id '" + walkId + "' because walk with this id doesn't exist");
        }

        Walk walk = walkRepository.findById(walkId).get();

        TimeInterval interval = new TimeInterval(request.getStartTime(), request.getEndTime());

        if (!isPersonCapableOfWalk(walk.getCaretaker().getPersonId(), interval)) {
            throw new WalkTimeIntersectException("Cannot update walk time because the caretaker has another activity that intersects with given time period");
        }

        if (!isAnimalCapableOfWalk(walk.getPupil().getAnimalId(), interval)) {
            throw new WalkTimeIntersectException("Cannot update walk time because the animal has another activity that intersects with given time period");
        }


        return null;
    }

    public void deleteWalk(Long walkId) throws WalkDoesntExistException {
        if (!walkRepository.existsById(walkId)) {
            throw new WalkDoesntExistException("Cannot get walk with id '" + walkId + "' because it doesn't exist");
        }
    }


    private boolean isAnimalCapableOfWalk(Long animalId, TimeInterval requestedInterval) {
        List<Walk> walks = walkRepository.getWalksByPupil_AnimalId(animalId);

        return isWalkPossible(requestedInterval, walks);
    }

    private boolean isPersonCapableOfWalk(Long personId, TimeInterval requestedInterval) {
        List<Walk> walks = walkRepository.getWalksByCaretaker_PersonId(personId);

        return isWalkPossible(requestedInterval, walks);
    }

    private boolean isWalkPossible(TimeInterval requestedInterval, List<Walk> walks) {
        for (Walk w : walks) {
            if (isWalkActiveOrPlanned(w)) {
                TimeInterval walkInterval = new TimeInterval(w.getStartTime(), w.getEndTime());
                if (TimeUtils.doTimeIntervalsIntersect(walkInterval, requestedInterval)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWalkActiveOrPlanned(Walk w) {
        return !w.getWalkStatus().getStatus().equals("Cancelled")
                && !w.getWalkStatus().getStatus().equals("Finished");
    }

    private boolean isStatusChangeValid(Long walkId, WalkStatusUpdateRequest request) {
        HashMap<String, List<String>> possibleChanges = new HashMap<>();
        List<String> coreStatuses = WalkStatusInitializer.coreStatuses;

        possibleChanges.put(coreStatuses.get(0), List.of(coreStatuses.get(1), coreStatuses.get(3)));
        possibleChanges.put(coreStatuses.get(1), List.of(coreStatuses.get(2)));
        possibleChanges.put(coreStatuses.get(2), Collections.emptyList());
        possibleChanges.put(coreStatuses.get(3), Collections.emptyList());

        Walk walk = walkRepository.findById(walkId).get();

        if (!possibleChanges.containsKey(walk.getWalkStatus().getStatus())) {
            return true;
        }

        return possibleChanges.get(walk.getWalkStatus().getStatus()).contains(request.getStatus());
    }
}
