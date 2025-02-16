package com.benevolekarizma.benevolekarizma.services.interfaces;

import com.benevolekarizma.benevolekarizma.DTO.user.UpdateUserRequest;
import com.benevolekarizma.benevolekarizma.DTO.user.UserResponse;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerRequest;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerResponse;

public interface VolunteerService {

    VolunteerResponse addVolunteer(VolunteerRequest volunteer);

    UserResponse getByUserName(String username);

    UserResponse updateVolunteer(Long id, UpdateUserRequest updateUserRequest);

}
