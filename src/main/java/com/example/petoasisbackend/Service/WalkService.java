package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Activity.Walk.WalkMinimumDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkChangeStatusDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkWithTimeDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DataInitializer.WalkStatusInitializer;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.Animal.AnimalNotAvailableException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Exception.Walk.*;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Mapper.Activity.Walk.WalkFilter;
import com.example.petoasisbackend.Mapper.Activity.Walk.WalkFilterType;
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

import java.time.LocalDateTime;
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
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;


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

    public List<ModelDTO<Walk>> getByAnimal(Long animalId, DataDetailLevel level, WalkFilterType filterType) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(animalId)) {
            throw new AnimalDoesntExistException("Cannot get walks of animal with id '" + animalId + "' because it doesn't exist");
        }

        List<Walk> walks = walkRepository.getWalksByPupil_AnimalId(animalId);
        var mapper = walkMapper.getMapper(level);

        List<Walk> filtered = walks.stream().filter(walk -> WalkFilter.filter(walk, filterType)).toList();

        return filtered.stream().map(mapper).collect(Collectors.toList());
    }

    public List<ModelDTO<Walk>> getByPerson(Long personId, DataDetailLevel level, WalkFilterType filterType) throws PersonDoesntExistException {
        if (!personRepository.existsById(personId)) {
            throw new PersonDoesntExistException("Cannot get walks of person with id '" + personId + "' because it doesn't exist");
        }

        List<Walk> walks = walkRepository.getWalksByCaretaker_PersonId(personId);
        var mapper = walkMapper.getMapper(level);

        List<Walk> filtered = walks.stream().filter(walk -> WalkFilter.filter(walk, filterType)).toList();

        return filtered.stream().map(mapper).collect(Collectors.toList());
    }


    public WalkMinimumDTO add(WalkAddRequest request) throws AnimalDoesntExistException, PersonDoesntExistException, ShelterDoesntExistException, WalkTimeIntersectException, AnimalNotAvailableException {
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

        if (!isPersonCapableOfWalkAdd(request.getPersonId(), walkInterval)) {
            throw new WalkTimeIntersectException("Cannot add walk because the person has another activity that intersects with given time period");
        }

        if (!isAnimalCapableOfWalkAdd(request.getAnimalId(), walkInterval)) {
            throw new WalkTimeIntersectException("Cannot add walk because the animal has another activity that intersects with given time period");
        }

        Animal pupil = animalRepository.findById(request.getAnimalId()).get();

        if (!pupil.getAvailabilityStatus().getAvailability().equals("Available")) {
            throw new AnimalNotAvailableException("Cannot start the walk because the animal is not available");
        }

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

    public WalkChangeStatusDTO updateStatus(Long id, WalkStatusUpdateRequest request) throws WalkDoesntExistException, WalkStatusDoesntExistException, WalkInvalidStatusChangeException {
        if (!walkRepository.existsById(id)) {
            throw new WalkDoesntExistException("Cannot update walk status of walk with id '" + id + "' because walk with this id doesn't exist");
        }

        if (!walkStatusRepository.existsByStatus(request.getStatus())) {
            throw new WalkStatusDoesntExistException("Cannot update walk status of walk with id '" + id + "' because status '" + request.getStatus() + "' doesn't exist");
        }

        if (!isStatusChangeValid(id, request)) {
            throw new WalkInvalidStatusChangeException("Status change request not allowed. Possible core changes: Pending->In progress or Cancelled, In Progress -> Finished");
        }

        WalkStatus status = walkStatusRepository.getWalkStatusByStatus(request.getStatus());

        Walk walk = walkRepository.findById(id).get();
        walk.setWalkStatus(status);
        walkRepository.save(walk);

        Animal animal = walk.getPupil();

        if (status.getStatus().equals("In progress")) {
            animal.setAvailabilityStatus(availabilityStatusRepository.findByAvailability("On a walk"));
            walk.setActualStartTime(LocalDateTime.now());
        } else if (status.getStatus().equals("Finished")) {
            animal.setAvailabilityStatus(availabilityStatusRepository.findByAvailability("Available"));
            walk.setActualEndTime(LocalDateTime.now());
        }

        animalRepository.save(animal);

        return WalkChangeStatusDTO.fromWalk(walk);
    }

    public WalkWithTimeDTO updateTime(Long id, WalkTimeUpdateRequest request) throws WalkTimeIntersectException, WalkDoesntExistException, WalkCannotBeModifiedException {
        if (!walkRepository.existsById(id)) {
            throw new WalkDoesntExistException("Cannot update period of walk with id '" + id + "' because walk with this id doesn't exist");
        }

        System.out.println("herethsi=1");

        Walk walk = walkRepository.findById(id).get();

        System.out.println("herethsi=2");

        if (!walk.getWalkStatus().getStatus().equals("Pending")) {
            throw new WalkCannotBeModifiedException("Cannot update period of walk with id '" + id + "' because the status is different from 'Pending'");
        }

        TimeInterval interval = new TimeInterval(request.getStartTime(), request.getEndTime());


        if (!isAnimalCapableOfWalkUpdate(walk.getPupil().getAnimalId(), interval, walk)) {
            throw new WalkTimeIntersectException("Cannot update walk time because the animal has another activity that intersects with given time period");
        }

        if (!isPersonCapableOfWalkUpdate(walk.getCaretaker().getPersonId(), interval, walk)) {
            throw new WalkTimeIntersectException("Cannot update walk time because the caretaker has another activity that intersects with given time period");
        }

        walk.setStartTime(request.getStartTime());
        walk.setEndTime(request.getEndTime());

        walkRepository.save(walk);

        System.out.println("finished");

        return WalkWithTimeDTO.fromWalk(walk);
    }

    public void deleteWalk(Long id) throws WalkDoesntExistException, WalkCannotBeDeletedException {
        if (!walkRepository.existsById(id)) {
            throw new WalkDoesntExistException("Cannot get walk with id '" + id + "' because it doesn't exist");
        }

        Walk walk = walkRepository.findById(id).get();

        if (!(walk.getWalkStatus().getStatus().equals("Finished") || walk.getWalkStatus().getStatus().equals("Cancelled"))) {
            throw new WalkCannotBeDeletedException(
                    "Cannot delete walk with id '" + id + "'. Can only delete walks with statuses"
                            + "'Finished' and 'Cancelled'. Consider changing the status first"
            );
        }

        walkRepository.delete(walk);
    }


    private boolean isAnimalCapableOfWalkAdd(Long animalId, TimeInterval requestedInterval) {
        List<Walk> walks = walkRepository.getWalksByPupil_AnimalId(animalId);

        return isWalkPossible(requestedInterval, walks);
    }

    private boolean isPersonCapableOfWalkAdd(Long personId, TimeInterval requestedInterval) {
        List<Walk> walks = walkRepository.getWalksByCaretaker_PersonId(personId);

        return isWalkPossible(requestedInterval, walks);
    }

    private boolean isAnimalCapableOfWalkUpdate(Long animalId, TimeInterval requestedInterval, Walk walk) {
        List<Walk> walks = walkRepository.getWalksByPupil_AnimalId(animalId);
        walks.remove(walk);

        return isWalkPossible(requestedInterval, walks);
    }

    private boolean isPersonCapableOfWalkUpdate(Long personId, TimeInterval requestedInterval, Walk walk) {
        List<Walk> walks = walkRepository.getWalksByCaretaker_PersonId(personId);
        walks.remove(walk);

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

    private boolean isStatusChangeValid(Long id, WalkStatusUpdateRequest request) {
        HashMap<String, List<String>> possibleChanges = new HashMap<>();
        List<String> coreStatuses = WalkStatusInitializer.coreStatuses;

        possibleChanges.put(coreStatuses.get(0), List.of(coreStatuses.get(1), coreStatuses.get(3)));
        possibleChanges.put(coreStatuses.get(1), List.of(coreStatuses.get(2)));
        possibleChanges.put(coreStatuses.get(2), Collections.emptyList());
        possibleChanges.put(coreStatuses.get(3), Collections.emptyList());

        Walk walk = walkRepository.findById(id).get();

        if (!possibleChanges.containsKey(walk.getWalkStatus().getStatus())) {
            return true;
        }

        return possibleChanges.get(walk.getWalkStatus().getStatus()).contains(request.getStatus());
    }
}
