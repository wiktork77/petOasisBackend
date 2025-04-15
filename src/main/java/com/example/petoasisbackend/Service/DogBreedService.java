package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedMinimumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedNameDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedUpdateDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Breed.Dog.DogBreedAlreadyExists;
import com.example.petoasisbackend.Exception.Breed.Dog.DogBreedDoesntExist;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusAlreadyExistsException;
import com.example.petoasisbackend.Mapper.AnimalBreed.DogBreedMapper;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.DogBreedRepository;
import com.example.petoasisbackend.Request.AnimalBreed.Dog.DogBreedAddRequest;
import com.example.petoasisbackend.Request.AnimalBreed.Dog.DogBreedUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogBreedService {
    @Autowired
    private DogBreedRepository dogBreedRepository;

    @Autowired
    private DogBreedMapper dogBreedMapper;

    public List<ModelDTO<DogBreed>> getAll(DataDetailLevel level) {
        List<DogBreed> breeds = dogBreedRepository.findAll();
        var mapper = dogBreedMapper.getMapper(level);

        return breeds.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<DogBreed> getById(Integer id, DataDetailLevel level) throws DogBreedDoesntExist {
        if (!dogBreedRepository.existsById(id)) {
            throw new DogBreedDoesntExist("Cannot get dog breed with id '" + id + "' because it doesn't exist");
        }

        DogBreed breed = dogBreedRepository.findById(id).get();
        var mapper = dogBreedMapper.getMapper(level);

        return mapper.apply(breed);
    }

    public ModelDTO<DogBreed> getByName(String name, DataDetailLevel level) throws DogBreedDoesntExist {
        if (!dogBreedRepository.existsByBreedName(name)) {
            throw new DogBreedDoesntExist("Cannot get dog breed with name '" + name + "' because it doesn't exist");
        }

        DogBreed breed = dogBreedRepository.findByBreedName(name);
        var mapper = dogBreedMapper.getMapper(level);

        return mapper.apply(breed);
    }

    public DogBreedMinimumDTO add(DogBreedAddRequest request) throws DogBreedAlreadyExists {
        if (dogBreedRepository.existsByBreedName(request.getBreedName())) {
            throw new DogBreedAlreadyExists("Cannot add dog breed '" + request.getBreedName() + "' because it already exists");
        }

        DogBreed breed = DogBreed.fromDogBreedAddRequest(request);
        DogBreed savedBreed = dogBreedRepository.save(breed);

        return DogBreedMinimumDTO.fromDogBreed(savedBreed);
    }

    public DogBreedUpdateDTO update(Integer id, DogBreedUpdateRequest request) throws DogBreedDoesntExist, DogBreedAlreadyExists {
        if (!dogBreedRepository.existsById(id)) {
            throw new DogBreedDoesntExist("Cannot update dog breed with id '" + id + "' because it doesn't exist");
        }

        if (dogBreedRepository.existsByBreedName(request.getBreedName())) {
            throw new DogBreedAlreadyExists("Cannot update dog breed with id '" + id + "' to '" + request.getBreedName() + "' because '" + request.getBreedName() + "' already exists");
        }

        DogBreed breed = dogBreedRepository.findById(id).get();
        breed.update(request);

        DogBreed updatedBreed = dogBreedRepository.save(breed);

        return DogBreedUpdateDTO.fromDogBreed(updatedBreed);
    }

    public void delete(Integer id) throws DogBreedDoesntExist {
        if (!dogBreedRepository.existsById(id)) {
            throw new DogBreedDoesntExist("Cannot delete dog breed with id '" + id + "' because it doesn't exist");
        }
        dogBreedRepository.deleteById(id);
    }
}
