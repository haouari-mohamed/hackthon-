package com.benevolekarizma.benevolekarizma.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerRequest;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerResponse;
import com.benevolekarizma.benevolekarizma.models.Volunteer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class VolunteerMapper {
    
    public Volunteer convertToEntity(VolunteerRequest volunteerRequest) {
        return Volunteer.builder()
                .username(volunteerRequest.getUsername())
                .firstName(volunteerRequest.getFirstName())
                .lastName(volunteerRequest.getLastName())
                .email(volunteerRequest.getEmail())
                .phoneNumber(volunteerRequest.getPhoneNumber())
                .password(volunteerRequest.getPassword())
                .role(volunteerRequest.getRole())
                .region(volunteerRequest.getRegion())
                .hoursVolunteer(volunteerRequest.getHoursVolunteer())
                .build();
    }

    public VolunteerResponse convertToDTO(Volunteer volunteer) {
        return VolunteerResponse.builder()
                .id(volunteer.getIdUser())
                .username(volunteer.getUsername())
                .firstName(volunteer.getFirstName())
                .lastName(volunteer.getLastName())
                .email(volunteer.getEmail())
                .phoneNumber(volunteer.getPhoneNumber())
                .role(volunteer.getRole())
                .region(volunteer.getRegion())
                .hoursVolunteer(volunteer.getHoursVolunteer())
                .build();
    }

    public List<VolunteerResponse> convertToDTOList(List<Volunteer> volunteers) {
        return volunteers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
