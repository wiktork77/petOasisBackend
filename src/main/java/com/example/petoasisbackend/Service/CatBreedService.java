package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedMinimumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedUpdateDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Breed.Cat.CatBreedAlreadyExists;
import com.example.petoasisbackend.Exception.Breed.Cat.CatBreedDoesntExist;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusAlreadyExistsException;
import com.example.petoasisbackend.Mapper.AnimalBreed.CatBreedMapper;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.CatBreedRepository;
import com.example.petoasisbackend.Request.AnimalBreed.Cat.CatBreedAddRequest;
import com.example.petoasisbackend.Request.AnimalBreed.Cat.CatBreedUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatBreedService {
    @Autowired
    private CatBreedRepository catBreedRepository;

    @Autowired
    private CatBreedMapper catBreedMapper;

    public List<ModelDTO<CatBreed>> getAll(DataDetailLevel level) {
        List<CatBreed> breeds = catBreedRepository.findAll();
        var mapper = catBreedMapper.getMapper(level);

        return breeds.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<CatBreed> getById(Integer id, DataDetailLevel level) throws CatBreedDoesntExist {
        if (!catBreedRepository.existsById(id)) {
            throw new CatBreedDoesntExist("Cannot get cat breed with id '" + id + "' because it doesn't exist");
        }

        CatBreed breed = catBreedRepository.findById(id).get();
        var mapper = catBreedMapper.getMapper(level);

        return mapper.apply(breed);
    }

    public ModelDTO<CatBreed> getByName(String name, DataDetailLevel level) throws CatBreedDoesntExist {
        if (!catBreedRepository.existsByBreedName(name)) {
            throw new CatBreedDoesntExist("Cannot get cat breed with name '" + name + "' because it doesn't exist");
        }

        CatBreed breed = catBreedRepository.findByBreedName(name);
        var mapper = catBreedMapper.getMapper(level);

        return mapper.apply(breed);
    }

    public CatBreedMinimumDTO add(CatBreedAddRequest request) throws CatBreedAlreadyExists {
        if (catBreedRepository.existsByBreedName(request.getBreedName())) {
            throw new CatBreedAlreadyExists("Cannot add cat breed with name '" + request.getBreedName() + "' because it already exists");
        }

        CatBreed breed = CatBreed.fromCatBreedAddRequest(request);

        CatBreed savedBreed = catBreedRepository.save(breed);

        return CatBreedMinimumDTO.fromCatBreed(savedBreed);
    }

    public CatBreedUpdateDTO update(Integer id, CatBreedUpdateRequest request) throws CatBreedAlreadyExists, CatBreedDoesntExist {
        if (!catBreedRepository.existsById(id)) {
            throw new CatBreedDoesntExist("Cannot update cat breed with id '" + id + "' because it doesn't exist");
        }

        if (catBreedRepository.existsByBreedName(request.getBreedName())) {
            throw new CatBreedAlreadyExists("Cannot update cat breed with id '" + id + "' to '" + request.getBreedName() + "' because '" + request.getBreedName() + "' already exists");
        }

        CatBreed breed = catBreedRepository.findById(id).get();
        breed.update(request);

        CatBreed savedBreed = catBreedRepository.save(breed);

        return CatBreedUpdateDTO.fromCatBreed(savedBreed);
    }

    public void delete(Integer id) throws CatBreedDoesntExist {
        if (!catBreedRepository.existsById(id)) {
            throw new CatBreedDoesntExist("Cannot delete cat breed with id '" + id + "' because it doesn't exist");
        }

        catBreedRepository.deleteById(id);
    }

}
