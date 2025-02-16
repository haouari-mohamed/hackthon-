package com.benevolekarizma.benevolekarizma.services.implement;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.benevolekarizma.benevolekarizma.DTO.user.UpdateUserRequest;
import com.benevolekarizma.benevolekarizma.DTO.user.UserResponse;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerRequest;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerResponse;
import com.benevolekarizma.benevolekarizma.exceptions.ResourceNotFoundException;
import com.benevolekarizma.benevolekarizma.exceptions.UsernameNotFoundException;
import com.benevolekarizma.benevolekarizma.mapper.UserMapper;
import com.benevolekarizma.benevolekarizma.mapper.VolunteerMapper;
import com.benevolekarizma.benevolekarizma.models.Volunteer;
import com.benevolekarizma.benevolekarizma.repositories.UserRepository;
import com.benevolekarizma.benevolekarizma.repositories.VolunteerRepository;
import com.benevolekarizma.benevolekarizma.services.interfaces.VolunteerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Volunteer entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VolunteerSerivceImpl implements VolunteerService {
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public VolunteerResponse addVolunteer(VolunteerRequest volunteer) {
        volunteer.setPassword(passwordEncoder.encode(volunteer.getPassword()));
        Volunteer user = volunteerMapper.convertToEntity(volunteer);
        return volunteerMapper.convertToDTO(userRepository.save(user));
    }

    @Override
    public UserResponse getByUserName(String username) {
        Volunteer volunteer = volunteerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Volunteer not found with username: " + username));
        return userMapper.toUserResponse(volunteer);
    }

    @Override
    public UserResponse updateVolunteer(Long id, UpdateUserRequest updateUserRequest) {
        Volunteer volunteerDB = volunteerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found"));

        if (updateUserRequest.getFirstName() != null && !updateUserRequest.getFirstName().isEmpty()) {
            volunteerDB.setFirstName(updateUserRequest.getFirstName());
        }
        if (updateUserRequest.getLastName() != null && !updateUserRequest.getLastName().isEmpty()) {
            volunteerDB.setLastName(updateUserRequest.getLastName());
        }
        if (updateUserRequest.getPhoneNumber() != null && !updateUserRequest.getPhoneNumber().isEmpty()) {
            volunteerDB.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }
        if (updateUserRequest.getRegion() != null) {
            volunteerDB.setRegion(updateUserRequest.getRegion());
        }

        return userMapper.toUserResponse(volunteerRepository.save(volunteerDB));
    }

}
