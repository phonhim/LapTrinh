package com.example.LapTrinh.Repositories;

import com.example.LapTrinh.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
