package com.benevolekarizma.benevolekarizma.services.interfaces;

import com.benevolekarizma.benevolekarizma.DTO.organization.OrganizationRequest;
import com.benevolekarizma.benevolekarizma.DTO.organization.OrganizationResponse;
import com.benevolekarizma.benevolekarizma.DTO.user.UpdateUserRequest;
import com.benevolekarizma.benevolekarizma.DTO.user.UserResponse;

public interface OrganizationService {

    OrganizationResponse addOrganization(OrganizationRequest organization);

    UserResponse getByUserName(String username);

    UserResponse updateOrganization(Long id, UpdateUserRequest updateUserRequest);

}
