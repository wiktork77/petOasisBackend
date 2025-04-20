package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Badge.Badge.BadgeMinimumDTO;
import com.example.petoasisbackend.DTO.Badge.Badge.BadgeUpdateDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Badge.BadgeAlreadyExists;
import com.example.petoasisbackend.Exception.Badge.BadgeDoesntExistException;
import com.example.petoasisbackend.Mapper.Badge.BadgeMapper;
import com.example.petoasisbackend.Model.Badge.Badge;
import com.example.petoasisbackend.Repository.BadgeRepository;
import com.example.petoasisbackend.Request.Badge.BadgeAddRequest;
import com.example.petoasisbackend.Request.Badge.BadgeUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BadgeService {
    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private BadgeMapper badgeMapper;

    public List<ModelDTO<Badge>> getAll(DataDetailLevel level) {
        List<Badge> badges = badgeRepository.findAll();
        var mapper = badgeMapper.getMapper(level);

        return badges.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<Badge> getById(Integer id, DataDetailLevel level) throws BadgeDoesntExistException {
        if (!badgeRepository.existsById(id)) {
            throw new BadgeDoesntExistException("Cannot get badge with id '" + id + "' because it doesn't exist");
        }

        Badge badge = badgeRepository.findById(id).get();
        var mapper = badgeMapper.getMapper(level);

        return mapper.apply(badge);
    }

    public ModelDTO<Badge> getByName(String name, DataDetailLevel level) throws BadgeDoesntExistException {
        if (!badgeRepository.existsByBadgeName(name)) {
            throw new BadgeDoesntExistException("Cannot get badge with name '" + name + "' because it doesn't exist");
        }

        Badge badge = badgeRepository.getBadgeByBadgeName(name);
        var mapper = badgeMapper.getMapper(level);

        return mapper.apply(badge);
    }

    public BadgeMinimumDTO add(BadgeAddRequest request) throws BadgeAlreadyExists {
        if (badgeRepository.existsByBadgeName(request.getBadgeName())) {
            throw new BadgeAlreadyExists("Cannot add badge with name '" + request.getBadgeName() + "' because it already exists");
        }

        Badge badge = Badge.fromBadgeAddRequest(request);

        badgeRepository.save(badge);

        return BadgeMinimumDTO.fromBadge(badge);
    }

    public BadgeUpdateDTO update(Integer id, BadgeUpdateRequest request) throws BadgeAlreadyExists, BadgeDoesntExistException {
        if (!badgeRepository.existsById(id)) {
            throw new BadgeDoesntExistException("Cannot update badge with id '" + id + "' because it doesn't exist");
        }

        if (badgeRepository.existsByBadgeName(request.getBadgeName())) {
            throw new BadgeAlreadyExists("Cannot update badge because badge with name '" + request.getBadgeName() + "' already exists");
        }

        Badge badge = badgeRepository.findById(id).get();
        badge.update(request);

        badgeRepository.save(badge);

        return BadgeUpdateDTO.fromBadge(badge);
    }

    public void delete(Integer id) throws BadgeDoesntExistException {
        if (!badgeRepository.existsById(id)) {
            throw new BadgeDoesntExistException("Cannot delete badge with id '" + id + "' because it doesn't exist");
        }

        badgeRepository.deleteById(id);
    }
}
