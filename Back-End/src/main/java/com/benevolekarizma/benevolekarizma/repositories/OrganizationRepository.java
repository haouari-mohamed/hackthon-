package com.benevolekarizma.benevolekarizma.repositories;

import com.benevolekarizma.benevolekarizma.models.Organization;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByUsername(String username);

}
