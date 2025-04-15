package com.example.petoasisbackend.DataInitializer;

import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.CatBreedRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CatBreedInitializer implements ApplicationRunner {
    private CatBreedRepository catBreedRepository;

    public CatBreedInitializer(CatBreedRepository catBreedRepository) {
        this.catBreedRepository = catBreedRepository;
    }

    private final List<String> baseCatBreeds = Arrays.asList(
            "Persian Cat",
            "British Shorthair",
            "Maine Coon",
            "Siamese Cat",
            "Bengal Cat",
            "Different"
    );

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String b: baseCatBreeds) {
            if (!catBreedRepository.existsByBreedName(b)) {
                CatBreed breed = new CatBreed(b);
                catBreedRepository.save(breed);
            }
        }
    }
}
