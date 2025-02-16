package com.benevolekarizma.benevolekarizma.repositories;

import com.benevolekarizma.benevolekarizma.models.Volunteer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Optional<Volunteer> findByUsername(String username);

}
