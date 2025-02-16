package com.benevolekarizma.benevolekarizma.mapper;

import org.springframework.stereotype.Component;

import com.benevolekarizma.benevolekarizma.DTO.user.UserResponse;
import com.benevolekarizma.benevolekarizma.models.Organization;
import com.benevolekarizma.benevolekarizma.models.Volunteer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMapper {

    public UserResponse toUserResponse(Volunteer volunteer) {
        if (volunteer == null) {
            return null;
        }
        return UserResponse.builder()
                .id(volunteer.getIdUser())
                .username(volunteer.getUsername())
                .firstName(volunteer.getFirstName())
                .lastName(volunteer.getLastName())
                .email(volunteer.getEmail())
                .phoneNumber(volunteer.getPhoneNumber())
                .role(volunteer.getRole())
                .region(volunteer.getRegion())
                .hoursVolunteer(volunteer.getHoursVolunteer())
                .orgName(null)
                .orgPhoneNumber(null)
                .orgAddress(null)
                .build();
    }

    public UserResponse toUserResponse(Organization organization) {
        if (organization == null) {
            return null;
        }
        return UserResponse.builder()
                .id(organization.getIdUser())
                .username(organization.getUsername())
                .firstName(organization.getFirstName())
                .lastName(organization.getLastName())
                .email(organization.getEmail())
                .phoneNumber(organization.getPhoneNumber())
                .role(organization.getRole())
                .region(organization.getRegion())
                .orgName(organization.getOrgName())
                .orgPhoneNumber(organization.getOrgPhoneNumber())
                .orgAddress(organization.getOrgAddress())
                .hoursVolunteer(null)
                .build();
    }

}
