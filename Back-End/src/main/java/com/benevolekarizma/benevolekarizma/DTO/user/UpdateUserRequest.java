package com.benevolekarizma.benevolekarizma.DTO.user;

import com.benevolekarizma.benevolekarizma.models.enums.Region;
import com.benevolekarizma.benevolekarizma.models.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private Role role;

    private Region region;

    private int hoursVolunteer;

    private String orgName;

    private String orgPhoneNumber;

    private String orgAddress;

}
