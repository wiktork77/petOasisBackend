package com.example.petoasisbackend.Repository;


import com.example.petoasisbackend.Model.Activity.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkRepository extends JpaRepository<Walk, Long> {
}
