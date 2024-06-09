package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WalkStatusRepository extends JpaRepository<WalkStatus, Integer> {
    boolean existsByStatus(String status);
    WalkStatus getWalkStatusByStatus(String status);
}
