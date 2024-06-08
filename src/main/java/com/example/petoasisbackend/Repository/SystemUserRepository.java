package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepository extends JpaRepository<GeneralSystemUser, Long> {
}
