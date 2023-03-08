package com.cogent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogent.entity.ERole;
import com.cogent.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
