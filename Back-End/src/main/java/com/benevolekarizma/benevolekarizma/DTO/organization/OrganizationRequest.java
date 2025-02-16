package com.benevolekarizma.benevolekarizma.DTO.organization;

import com.benevolekarizma.benevolekarizma.models.enums.Region;
import com.benevolekarizma.benevolekarizma.models.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrganizationRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

    @NotNull(message = "Region is required")
    private Region region;

    @NotBlank(message = "Organization name is required")
    private String orgName;

    @NotBlank(message = "Organization phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    private String orgPhoneNumber;

    @NotBlank(message = "Organization address is required")
    private String orgAddress;

}
