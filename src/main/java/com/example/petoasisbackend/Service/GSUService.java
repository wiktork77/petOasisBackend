package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.*;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.GSU.UserDoesntExistException;
import com.example.petoasisbackend.Exception.GSU.UserPasswordDoesntMatch;
import com.example.petoasisbackend.Mapper.User.GSUMapper;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.User.GSU.GSUUpdateRequest;
import com.example.petoasisbackend.Request.User.GSU.PasswordChangeRequest;
import com.example.petoasisbackend.Request.User.GSU.ProfilePictureChangeRequest;
import com.example.petoasisbackend.Tools.Credentials.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GSUService {
    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private GSUMapper gsuMapper;

    public List<ModelDTO<GeneralSystemUser>> getAll(DataDetailLevel level) {
        List<GeneralSystemUser> users = systemUserRepository.findAll();
        var mapper = gsuMapper.getMapper(level);

        return users.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<GeneralSystemUser> getById(Long id, DataDetailLevel level) throws UserDoesntExistException {
        if (!systemUserRepository.existsById(id)) {
            throw new UserDoesntExistException("Cannot get user with id '" + id + "' because it doesn't exist");
        }

        GeneralSystemUser user = systemUserRepository.findById(id).get();
        var mapper = gsuMapper.getMapper(level);

        return mapper.apply(user);
    }

    public GSUVerificationDTO verify(Long id) throws UserDoesntExistException {
        if (!systemUserRepository.existsById(id)) {
            throw new UserDoesntExistException("Cannot verify user with id '" + id + "' because it doesn't exist");
        }

        GeneralSystemUser user = systemUserRepository.findById(id).get();
        user.setIsVerified(true);

        GeneralSystemUser savedUser = systemUserRepository.save(user);

        return GSUVerificationDTO.fromGSU(savedUser);
    }

    public GSUProfilePictureDTO changeProfilePicture(Long id, ProfilePictureChangeRequest request) throws UserDoesntExistException {
        if (!systemUserRepository.existsById(id)) {
            throw new UserDoesntExistException("Cannot change profile picture of user with id '" + id + "' because it doesn't exist");
        }

        GeneralSystemUser user = systemUserRepository.findById(id).get();
        user.setPictureUrl(request.getUrl());

        GeneralSystemUser savedUser = systemUserRepository.save(user);

        return GSUProfilePictureDTO.fromGSU(savedUser);
    }

    public GSUMinimumDTO changePassword(Long id, PasswordChangeRequest request) throws UserPasswordDoesntMatch, UserDoesntExistException {
        if (!systemUserRepository.existsById(id)) {
            throw new UserDoesntExistException("Cannot change password of user with id '" + id + "' because it doesn't exist");
        }

        if (!matchesPassword(id, request.getOldPassword())) {
            throw new UserPasswordDoesntMatch("Current password and given password don't match");
        }

        GeneralSystemUser user = systemUserRepository.findById(id).get();
        Encoder encoder = new Encoder();

        user.setPassword(encoder.encodePassword(request.getNewPassword()));

        GeneralSystemUser savedUser = systemUserRepository.save(user);

        return GSUMinimumDTO.fromGSU(savedUser);
    }

    public GSUUpdateDTO update(Long id, GSUUpdateRequest request) throws UserDoesntExistException, UserAlreadyExistsException {
        if (!systemUserRepository.existsById(id)) {
            throw new UserDoesntExistException("Cannot update user with id '" + id + "' because it doesn't exist");
        }

        if (systemUserRepository.existsByLogin(request.getLogin())) {
            throw new UserAlreadyExistsException("Cannot update user because given login already exists");
        }

        GeneralSystemUser gsu = systemUserRepository.findById(id).get();
        gsu.update(request);

        systemUserRepository.save(gsu);

        return GSUUpdateDTO.fromGSU(gsu);
    }


    private Boolean matchesPassword(Long id, String enteredPassword) {
        GeneralSystemUser gsu = systemUserRepository.findById(id).get();
        Encoder encoder = new Encoder();
        return encoder.passwordMatches(enteredPassword, gsu.getPassword());
    }
}
