package com.example.petoasisbackend.DataInitializer;

import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.DogBreedRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DogBreedInitializer implements ApplicationRunner {
    private DogBreedRepository dogBreedRepository;

    public DogBreedInitializer(DogBreedRepository dogBreedRepository) {
        this.dogBreedRepository = dogBreedRepository;
    }

    private final List<String> baseDogBreeds = Arrays.asList(
            "German Shepherd",
            "Labrador Retriever",
            "Golden Retriever",
            "French Bulldog",
            "Beagle",
            "Different"
    );

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String b: baseDogBreeds) {
            if (!dogBreedRepository.existsByBreedName(b)) {
                DogBreed breed = new DogBreed(b);
                dogBreedRepository.save(breed);
            }
        }
    }
}
