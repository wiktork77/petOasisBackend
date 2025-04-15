package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DataInitializer.AvailabilityStatusInitializer;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusDoesntExistException;
import com.example.petoasisbackend.Mapper.Status.AvailabilityStatusMapper;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Repository.AvailabilityStatusRepository;
import com.example.petoasisbackend.Request.Status.AvailabilityStatus.AvailabilityStatusAddRequest;
import com.example.petoasisbackend.Request.Status.AvailabilityStatus.AvailabilityStatusUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityStatusService {
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;
    @Autowired
    private AvailabilityStatusMapper availabilityStatusMapper;

    public List<ModelDTO<AvailabilityStatus>> getAvailabilityStatuses(DataDetailLevel level) {
        List<AvailabilityStatus> statuses = availabilityStatusRepository.findAll();
        var mapper = availabilityStatusMapper.getMapper(level);

        return statuses.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<AvailabilityStatus> getAvailabilityStatusById(Integer id, DataDetailLevel level) throws AvailabilityStatusDoesntExistException {
        if (!availabilityStatusRepository.existsById(id)) {
            throw new AvailabilityStatusDoesntExistException(
                    "Cannot get availability status with id '" + id + "' because it doesn't exist"
            );
        }

        AvailabilityStatus status = availabilityStatusRepository.findById(id).get();
        var mapper = availabilityStatusMapper.getMapper(level);

        return mapper.apply(status);
    }

    public AvailabilityStatusVerboseDTO getAvailabilityStatusByName(String name) throws AvailabilityStatusDoesntExistException {
        if (!availabilityStatusRepository.existsByAvailability(name)) {
            throw new AvailabilityStatusDoesntExistException(
                    "Cannot get availability status with name '" + name + "' because it doesn't exist"
            );
        }

        AvailabilityStatus status = availabilityStatusRepository.findByAvailability(name);

        return AvailabilityStatusVerboseDTO.fromAvailabilityStatus(status);
    }

    public AvailabilityStatusMinimumDTO addAvailabilityStatus(AvailabilityStatusAddRequest request) throws AvailabilityStatusAlreadyExistsException {
        if (availabilityStatusRepository.existsByAvailability(request.getAvailability())) {
            throw new AvailabilityStatusAlreadyExistsException(
                    "Cannot add availability status with name '" + request.getAvailability() + "' because it already exists"
            );
        }


        AvailabilityStatus status = new AvailabilityStatus(request.getAvailability());
        availabilityStatusRepository.save(status);

        return AvailabilityStatusMinimumDTO.fromAvailabilityStatus(status);
    }

    public void removeAvailabilityStatus(Integer id) throws AvailabilityStatusDoesntExistException, AvailabilityStatusCannotBeModifiedException {
        if (!availabilityStatusRepository.existsById(id)) {
            throw new AvailabilityStatusDoesntExistException(
                    "Cannot delete availability status with id '" + id + "' because it doesnt exist"
            );
        }
        AvailabilityStatus status = availabilityStatusRepository.findById(id).get();

        if (AvailabilityStatusInitializer.coreStatuses.contains(status.getAvailability())) {
            throw new AvailabilityStatusCannotBeModifiedException(
                    "Cannot delete core status"
            );
        }

        availabilityStatusRepository.delete(status);
    }

    public AvailabilityStatusVerboseDTO updateAvailabilityStatus(Integer id, AvailabilityStatusUpdateRequest statusNameDTO) throws AvailabilityStatusDoesntExistException, AvailabilityStatusAlreadyExistsException, AvailabilityStatusCannotBeModifiedException {
        if (!availabilityStatusRepository.existsById(id)) {
            throw new AvailabilityStatusDoesntExistException(
                    "Cannot update availability status with id '" + id + "' because it doesn't exist"
            );
        }

        if (availabilityStatusRepository.existsByAvailability(statusNameDTO.getAvailability())) {
            throw new AvailabilityStatusAlreadyExistsException(
                    "Cannot update availability status with id '" + id + "' to '"
                            + statusNameDTO.getAvailability() + "' because '" + statusNameDTO.getAvailability()
                            +  "' already exists"
            );
        }

        AvailabilityStatus status = availabilityStatusRepository.findById(id).get();

        if (AvailabilityStatusInitializer.coreStatuses.contains(status.getAvailability())) {
            throw new AvailabilityStatusCannotBeModifiedException("Cannot modify core status");
        }

        status.setAvailability(statusNameDTO.getAvailability());

        availabilityStatusRepository.save(status);

        return AvailabilityStatusVerboseDTO.fromAvailabilityStatus(status);
    }
}
