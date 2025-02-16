package com.benevolekarizma.benevolekarizma.mapper;

import com.benevolekarizma.benevolekarizma.DTO.event.EventRequest;
import com.benevolekarizma.benevolekarizma.DTO.event.EventResponse;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerResponse;
import com.benevolekarizma.benevolekarizma.models.Event;
import com.benevolekarizma.benevolekarizma.models.Volunteer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventResponse toResponse(Event event) {
        if (event == null) {
            return null;
        }

        List<VolunteerResponse> volunteerDTOs = null;

        List<Volunteer> volunteers = event.getVolunteers();
        volunteerDTOs = volunteers.stream()
                .map(volunteer -> VolunteerResponse.builder()
                        .id(volunteer.getIdUser())
                        .username(volunteer.getUsername())
                        .firstName(volunteer.getFirstName())
                        .lastName(volunteer.getLastName())
                        .email(volunteer.getEmail())
                        .phoneNumber(volunteer.getPhoneNumber())
                        .region(volunteer.getRegion())
                        .hoursVolunteer(volunteer.getHoursVolunteer())
                        .build())
                .collect(Collectors.toList());

        return new EventResponse(
                event.getIdEvent(),
                event.getEventName(),
                event.getEventDescription(),
                event.getEventDate(),
                event.getEventRegion(),
                event.getImage(),
                event.getDuration(),
                event.getMaxParticipants(),
                volunteerDTOs);
    }

    public static Event toEntity(EventRequest eventRequest) {
        if (eventRequest == null) {
            return null;
        }
        Event event = new Event();
        event.setEventName(eventRequest.getEventName());
        event.setEventDescription(eventRequest.getEventDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setEventRegion(eventRequest.getEventRegion());
        event.setMaxParticipants(eventRequest.getMaxParticipants());
        event.setVolunteers(new ArrayList<>()); // Ensures volunteers list is initialized
        return event;
    }
}
