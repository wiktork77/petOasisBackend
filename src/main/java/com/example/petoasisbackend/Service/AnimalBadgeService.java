package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Badge.AnimalBadge.AnimalBadgeMinimumDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.AnimalBadge.AnimalBadgeAlreadyAttachedException;
import com.example.petoasisbackend.Exception.AnimalBadge.AnimalBadgeNotAttachedException;
import com.example.petoasisbackend.Exception.Badge.BadgeDoesntExistException;
import com.example.petoasisbackend.Mapper.Badge.AnimalBadgeMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Badge.AnimalBadge;
import com.example.petoasisbackend.Model.Badge.AnimalBadgeId;
import com.example.petoasisbackend.Model.Badge.Badge;
import com.example.petoasisbackend.Repository.AnimalBadgeRepository;
import com.example.petoasisbackend.Repository.AnimalRepository;
import com.example.petoasisbackend.Repository.BadgeRepository;
import com.example.petoasisbackend.Request.AnimalBadge.AnimalBadgeAttachRequest;
import com.example.petoasisbackend.Request.AnimalBadge.AnimalBadgeDetachRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalBadgeService {
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalBadgeRepository animalBadgeRepository;

    @Autowired
    private AnimalBadgeMapper animalBadgeMapper;
    @Autowired
    private BadgeRepository badgeRepository;


    public List<ModelDTO<AnimalBadge>> getBadgesAttachedToAnimals(DataDetailLevel level) {
        List<AnimalBadge> badges = animalBadgeRepository.findAll();
        var mapper = animalBadgeMapper.getMapper(level);

        return badges.stream().map(mapper).collect(Collectors.toList());
    }


    public List<ModelDTO<AnimalBadge>> getBadgesOfAnAnimal(Long animalId, DataDetailLevel level) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(animalId)) {
            throw new AnimalDoesntExistException("Cannot get badges of the animal with id '" + animalId + "' because it doesn't exist.");
        }

        List<AnimalBadge> badges = animalBadgeRepository.findAnimalBadgesByAnimal_AnimalId(animalId);
        var mapper = animalBadgeMapper.getMapper(level);

        return badges.stream().map(mapper).collect(Collectors.toList());
    }

    public List<ModelDTO<AnimalBadge>> getAnimalsWithBadge(Integer badgeId, DataDetailLevel level) throws BadgeDoesntExistException {
        if (!badgeRepository.existsById(badgeId)) {
            throw new BadgeDoesntExistException("Cannot get recipients of badge with id '" + badgeId + "' because it doesn't exist");
        }

        List<AnimalBadge> badges = animalBadgeRepository.findAnimalBadgesByBadge_BadgeId(badgeId);
        var mapper = animalBadgeMapper.getMapper(level);

        return badges.stream().map(mapper).collect(Collectors.toList());
    }

    public AnimalBadgeMinimumDTO attachBadge(AnimalBadgeAttachRequest request) throws AnimalDoesntExistException, BadgeDoesntExistException, AnimalBadgeAlreadyAttachedException {
        if (!animalRepository.existsById(request.getAnimalId())) {
            throw new AnimalDoesntExistException("Cannot attach a badge to an animal because the animal with id '" + request.getAnimalId() + "' doesn't exist");
        }

        if (!badgeRepository.existsById(request.getBadgeId())) {
            throw new BadgeDoesntExistException("Cannot attach a badge to an animal because the badge with id '" + request.getBadgeId() + "' doesn't exist");
        }

        AnimalBadgeId badgeId = AnimalBadgeId.fromAnimalBadgeAttachRequest(request);

        if (animalBadgeRepository.existsById(badgeId)) {
            throw new AnimalBadgeAlreadyAttachedException("Cannot attach a badge to an animal because the badge is already attached");
        }

        Animal animal = animalRepository.findById(request.getAnimalId()).get();
        Badge badge = badgeRepository.findById(request.getBadgeId()).get();

        AnimalBadge animalBadge = new AnimalBadge(animal, badge);

        AnimalBadge savedBadge = animalBadgeRepository.save(animalBadge);

        return AnimalBadgeMinimumDTO.fromAnimalBadge(savedBadge);
    }

    public void detachBadge(AnimalBadgeDetachRequest request) throws AnimalDoesntExistException, BadgeDoesntExistException, AnimalBadgeNotAttachedException {
        if (!animalRepository.existsById(request.getAnimalId())) {
            throw new AnimalDoesntExistException("Cannot detach a badge from an animal because the animal with id '" + request.getAnimalId() + "' doesn't exist");
        }

        if (!badgeRepository.existsById(request.getBadgeId())) {
            throw new BadgeDoesntExistException("Cannot detach a badge from an animal because the badge with id '" + request.getBadgeId() + "' doesn't exist");
        }

        AnimalBadgeId badgeId = AnimalBadgeId.fromAnimalBadgeDetachRequest(request);

        if (!animalBadgeRepository.existsById(badgeId)) {
            throw new AnimalBadgeNotAttachedException("Cannot detach a badge from an animal because the badge isn't attached");
        }

        animalBadgeRepository.deleteById(badgeId);
    }
}
