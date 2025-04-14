package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMinimumDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUProfilePictureDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUVerboseDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUVerificationDTO;
import com.example.petoasisbackend.Exception.GSU.UserDoesntExistException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Mapper.GSUMapper;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.GSU.PasswordChangeRequest;
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

    public GSUProfilePictureDTO changeProfilePicture(Long id, String url) throws UserDoesntExistException {
        if (!systemUserRepository.existsById(id)) {
            throw new UserDoesntExistException("Cannot change profile picture of user with id '" + id + "' because it doesn't exist");
        }

        GeneralSystemUser user = systemUserRepository.findById(id).get();
        user.setPictureUrl(url);

        GeneralSystemUser savedUser = systemUserRepository.save(user);

        return GSUProfilePictureDTO.fromGSU(savedUser);
    }

    public GSUMinimumDTO changePassword(PasswordChangeRequest request) {
        return null;
    }

    private Boolean matchesPassword(Long id, String enteredPassword) {
        GeneralSystemUser gsu = systemUserRepository.findById(id).get();
        Encoder encoder = new Encoder();
        return encoder.passwordMatches(enteredPassword, gsu.getPassword());
    }



}
