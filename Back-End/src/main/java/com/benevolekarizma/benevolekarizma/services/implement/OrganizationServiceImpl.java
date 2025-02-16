package com.benevolekarizma.benevolekarizma.services.implement;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.benevolekarizma.benevolekarizma.DTO.organization.OrganizationRequest;
import com.benevolekarizma.benevolekarizma.DTO.organization.OrganizationResponse;
import com.benevolekarizma.benevolekarizma.DTO.user.UpdateUserRequest;
import com.benevolekarizma.benevolekarizma.DTO.user.UserResponse;
import com.benevolekarizma.benevolekarizma.exceptions.ResourceNotFoundException;
import com.benevolekarizma.benevolekarizma.exceptions.UsernameNotFoundException;
import com.benevolekarizma.benevolekarizma.mapper.OrganizationMapper;
import com.benevolekarizma.benevolekarizma.mapper.UserMapper;
import com.benevolekarizma.benevolekarizma.models.Organization;
import com.benevolekarizma.benevolekarizma.repositories.OrganizationRepository;
import com.benevolekarizma.benevolekarizma.repositories.UserRepository;
import com.benevolekarizma.benevolekarizma.services.interfaces.OrganizationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Organization entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public OrganizationResponse addOrganization(OrganizationRequest organization) {
        organization.setPassword(passwordEncoder.encode(organization.getPassword()));
        Organization org = organizationMapper.convertToEntity(organization);
        return organizationMapper.convertToDTO(userRepository.save(org));
    }

    @Override
    public UserResponse getByUserName(String username) {
        Organization org = organizationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Organization not found with username: " + username));
        return userMapper.toUserResponse(org);
    }

    @Override
    public UserResponse updateOrganization(Long id, UpdateUserRequest updateUserRequest) {
        log.info("user id:" + id);
        Organization organizationDB = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        if (updateUserRequest.getFirstName() != null && !updateUserRequest.getFirstName().isEmpty()) {
            organizationDB.setFirstName(updateUserRequest.getFirstName());
        }
        if (updateUserRequest.getLastName() != null && !updateUserRequest.getLastName().isEmpty()) {
            organizationDB.setLastName(updateUserRequest.getLastName());
        }
        if (updateUserRequest.getPhoneNumber() != null && !updateUserRequest.getPhoneNumber().isEmpty()) {
            organizationDB.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }
        if (updateUserRequest.getRegion() != null) {
            organizationDB.setRegion(updateUserRequest.getRegion());
        }
        if (updateUserRequest.getOrgName() != null && !updateUserRequest.getOrgName().isEmpty()) {
            organizationDB.setOrgName(updateUserRequest.getOrgName());
        }
        if (updateUserRequest.getOrgPhoneNumber() != null && !updateUserRequest.getOrgPhoneNumber().isEmpty()) {
            organizationDB.setOrgPhoneNumber(updateUserRequest.getOrgPhoneNumber());
        }
        if (updateUserRequest.getOrgAddress() != null && !updateUserRequest.getOrgAddress().isEmpty()) {
            organizationDB.setOrgAddress(updateUserRequest.getOrgAddress());
        }

        return userMapper.toUserResponse(organizationRepository.save(organizationDB));
    }

}
