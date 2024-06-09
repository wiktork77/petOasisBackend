package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Users.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
