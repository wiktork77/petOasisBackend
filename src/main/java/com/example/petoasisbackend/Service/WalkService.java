package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Activity.Walk.*;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DataInitializer.WalkStatusInitializer;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Exception.Walk.*;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Mapper.WalkMapper;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Repository.*;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.Walk.WalkAddRequest;
import com.example.petoasisbackend.Request.WalkStatus.WalkStatusUpdateRequest;
import com.example.petoasisbackend.Tools.Time.Period;
import com.example.petoasisbackend.Validation.Time.TimeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WalkService {
    @Autowired
    private WalkRepository walkRepository;
    @Autowired
    private WalkStatusRepository walkStatusRepository;
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private WalkMapper walkMapper;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ShelterRepository shelterRepository;


    public List<ModelDTO<Walk>> getWalks(DataDetailLevel level) {
        List<Walk> data = walkRepository.findAll();
        var mapper = walkMapper.getMapper(level);

        return data.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<Walk> getWalkById(Long id, DataDetailLevel level) throws WalkDoesntExistException {
        if (!walkRepository.existsById(id)) {
            throw new WalkDoesntExistException("Cannot get walk with id '" + id + "' because it doesn't exist");
        }

        Walk walk = walkRepository.findById(id).get();
        var mapper = walkMapper.getMapper(level);

        return mapper.apply(walk);
    }

    public WalkMinimumDTO addWalk(WalkAddRequest request) throws AnimalDoesntExistException, WalkTimeIntersectException, PersonDoesntExistException, ShelterDoesntExistException {
        if (!animalRepository.existsById(request.getAnimalId())) {
            throw new AnimalDoesntExistException("Cannot get animal with id '" + request.getAnimalId() + "'" + " because it doesn't exist");
        }

        if (!checkIfAnimalIsAvailableForWalk(request.getAnimalId(), request.getStartTime(), request.getEndTime())) {
            throw new WalkTimeIntersectException("Cannot add a walk with animal with id '" + request.getAnimalId() + "' because given time intersects with other walk(s).");
        }
        Animal pupil = animalRepository.getReferenceById(request.getAnimalId());

        if (!personRepository.existsById(request.getPersonId())) {
            throw new PersonDoesntExistException("Cannot get user with id '" + request.getPersonId() + "' because it doesn't exist!");
        }
        Person caretaker = personRepository.getReferenceById(request.getPersonId());

        if (!shelterRepository.existsByShelterId(request.getShelterId())) {
            throw new ShelterDoesntExistException(
                    "Cannot get shelter with id '" + request.getShelterId() + "' because it doesn't exist"
            );
        }
        Shelter supervisor = shelterRepository.getReferenceById(request.getShelterId());

        Walk newWalk = new Walk(
                pupil,
                caretaker,
                supervisor,
                request.getStartTime(),
                request.getEndTime(),
                walkStatusRepository.getWalkStatusByStatus("Pending")
        );

        Walk savedWalk = walkRepository.save(newWalk);

        return WalkMinimumDTO.fromWalk(savedWalk);
    }

    public void removeWalk(Long id) throws WalkDoesntExistException, WalkCannotBeDeletedException {
        List<String> allowedStatuses = List.of("Finished", "Cancelled");
        if (!walkRepository.existsById(id)) {
            throw new WalkDoesntExistException("Cannot delete walk with id '" + id + "'. It doesn't exist.");
        }

        Walk walk = walkRepository.getReferenceById(id);

        if (!allowedStatuses.contains(walk.getWalkStatus().getStatus())) {
            throw new WalkCannotBeDeletedException("Cannot delete walk with id '" + id + "'. Can only delete walks with statuses 'Finished' and 'Cancelled'. Consider changing the status first");
        }

        walkRepository.delete(walk);
    }

    public WalkWithStatusDTO updateWalkStatus(Long walkId, WalkStatusUpdateRequest request) throws WalkStatusDoesntExistException, WalkDoesntExistException, WalkInvalidStatusChangeException {
        if (!walkStatusRepository.existsByStatus(request.getStatus())) {
            throw new WalkStatusDoesntExistException(
                    "Cannot update walk status of walk with id '" + walkId + "' because status '" +
                            request.getStatus() + "' doesn't exist"
            );
        }

        if (!walkRepository.existsById(walkId)) {
            throw new WalkDoesntExistException(
                    "Cannot update walk status of walk with id '" + walkId + "' because walk with this id doesn't exist"
            );
        }

        Walk walk = walkRepository.findById(walkId).get();
        WalkStatus walkStatus = walkStatusRepository.getWalkStatusByStatus(request.getStatus());

        if (!isStatusChangeValid(walk.getWalkStatus(), walkStatus)) {
            throw new WalkInvalidStatusChangeException("Invalid walk status change");
        }

        walk.setWalkStatus(walkStatus);
        Walk savedWalk = walkRepository.save(walk);


        return WalkWithStatusDTO.fromWalk(savedWalk);
    }

    public WalkWithTimeDTO updateWalkTime(Long walkId, Period period) throws WalkInvalidTimePeriodException, WalkDoesntExistException, WalkCannotBeModifiedException {
        TimeValidator validator = new TimeValidator();
        if (!validator.isPeriodValid(period)) {
            throw new WalkInvalidTimePeriodException("Cannot update period of walk with id '" + walkId + "' because the time period is invalid, endTime must be after startTime");
        }
        if (!walkRepository.existsById(walkId)) {
            throw new WalkDoesntExistException("Cannot update period of walk with id '" + walkId + "' because walk with this id doesn't exist");
        }

        Walk walk = walkRepository.findById(walkId).get();

        if (!walk.getWalkStatus().getStatus().equals("Pending")) {
            throw new WalkCannotBeModifiedException("Cannot update period of walk with id '" + walkId + "' because the status is different from 'Pending'");
        }

        walk.setStartTime(period.getStart());
        walk.setEndTime(period.getFinish());

        Walk savedWalk = walkRepository.save(walk);

        return WalkWithTimeDTO.fromWalk(savedWalk);
    }




    public Boolean checkIfAnimalIsAvailableForWalk(Long animalId, LocalDateTime start, LocalDateTime end) {

        TimeValidator parser = new TimeValidator();
        Period wantedWalkPeriod = new Period(start, end);

        List<Walk> walks = walkRepository.findAll();

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

    private boolean isStatusChangeValid(WalkStatus current, WalkStatus request) {
        Map<String, List<String >> possibleChanges = new HashMap<>();
        List<String> coreStatuses = WalkStatusInitializer.coreStatuses;

        possibleChanges.put(coreStatuses.get(0), List.of(coreStatuses.get(1), coreStatuses.get(3)));
        possibleChanges.put(coreStatuses.get(1), List.of(coreStatuses.get(2)));
        possibleChanges.put(coreStatuses.get(2), List.of());
        possibleChanges.put(coreStatuses.get(3), List.of());

        if (coreStatuses.contains(current.getStatus())) {
            if (!possibleChanges.get(current.getStatus()).contains(request.getStatus())) {
                return false;
            }
        };

        return true;
    }

}
