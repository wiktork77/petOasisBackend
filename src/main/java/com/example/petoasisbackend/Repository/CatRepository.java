package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Animal.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
