package com.benevolekarizma.benevolekarizma.services.implement;

import com.benevolekarizma.benevolekarizma.DTO.event.EventRequest;
import com.benevolekarizma.benevolekarizma.DTO.event.EventResponse;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerResponse;
import com.benevolekarizma.benevolekarizma.exceptions.UsernameNotFoundException;
import com.benevolekarizma.benevolekarizma.mapper.VolunteerMapper;
import com.benevolekarizma.benevolekarizma.models.Event;
import com.benevolekarizma.benevolekarizma.models.Organization;
import com.benevolekarizma.benevolekarizma.models.User;
import com.benevolekarizma.benevolekarizma.models.Volunteer;
import com.benevolekarizma.benevolekarizma.models.enums.Region;
import com.benevolekarizma.benevolekarizma.models.enums.Role;
import com.benevolekarizma.benevolekarizma.repositories.EventRepository;
import com.benevolekarizma.benevolekarizma.repositories.OrganizationRepository;
import com.benevolekarizma.benevolekarizma.repositories.UserRepository;
import com.benevolekarizma.benevolekarizma.repositories.VolunteerRepository;
import com.benevolekarizma.benevolekarizma.services.interfaces.EventServiceInterface;
import com.benevolekarizma.benevolekarizma.services.interfaces.UserServiceInterface;

import lombok.RequiredArgsConstructor;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventServiceInterface {

    private final EventRepository eventRepository;
    private final OrganizationRepository organizationRepository;
    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;
    private final VolunteerMapper volunteerMapper;
    private final UserServiceInterface userService;

    @Override
    public EventResponse createEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setEventName(eventRequest.getEventName());
        event.setEventDescription(eventRequest.getEventDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setEventRegion(eventRequest.getEventRegion());
        event.setImage(eventRequest.getImage());
        event.setDuration(eventRequest.getDuration()); // ✅ Added duration
        event.setMaxParticipants(eventRequest.getMaxParticipants());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Organization org = organizationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Organization not found with username: " + username));
        event.setOrganization(org);

        Event savedEvent = eventRepository.save(event);
        return convertToResponse(savedEvent);
    }

    @Override
    public EventResponse getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return convertToResponse(event);
    }

    @Override
    public List<EventResponse> getAllEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUserName(username);

        return eventRepository.findAll().stream()
                .filter(event -> event.getVolunteers()
                        .stream()
                        .noneMatch(volunteer -> volunteer.getIdUser().equals(user.getIdUser())))
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }

    @Override
    public EventResponse updateEvent(Long eventId, EventRequest eventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        event.setEventName(eventRequest.getEventName());
        event.setEventDescription(eventRequest.getEventDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setEventRegion(eventRequest.getEventRegion());
        event.setDuration(eventRequest.getDuration()); // ✅ Added duration
        event.setMaxParticipants(eventRequest.getMaxParticipants());

        Event updatedEvent = eventRepository.save(event);
        return convertToResponse(updatedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        eventRepository.delete(event);
    }

    @Override
    public EventResponse registerVolunteer(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        Volunteer volunteer = volunteerRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found"));

        if (event.getVolunteers() == null) {
            event.setVolunteers(new ArrayList<>());
        }

        // ✅ Prevent duplicate registration
        if (event.getVolunteers().stream().anyMatch(v -> v.getIdUser().equals(userId))) {
            throw new IllegalArgumentException("User is already registered for this event.");
        }

        // ✅ Prevent exceeding max participants
        if (event.getVolunteers().size() >= event.getMaxParticipants()) {
            throw new IllegalArgumentException("Event has reached max capacity.");
        }

        volunteer.getEvents().add(event);
        event.getVolunteers().add(volunteer);

        // Save the owning side (or both if needed)
        volunteerRepository.save(volunteer);
        eventRepository.save(event);

        return convertToResponse(event);

    }

    @Override
    public List<VolunteerResponse> getEventVolunteers(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return event.getVolunteers().stream().map(volunteerMapper::convertToDTO).toList();
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public EventResponse convertToResponse(Event event) {
        List<VolunteerResponse> volunteerDTOs = null;

        List<Volunteer> volunteers = event.getVolunteers();
        if (volunteers != null) {
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
        }

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

    public EventResponse convertToResponse(Event event, String param) {

        List<VolunteerResponse> volunteerDTOs = null;
        if (param.isEmpty()) {
            List<Volunteer> volunteers = event.getVolunteers();
            if (volunteers != null) {
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
            }
        }

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

    @Override
    public EventResponse getEventDetailsForUser(Long eventId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        boolean isVolunteer = user.getRole() == Role.VOLUNTEER;

        return new EventResponse(
                event.getIdEvent(),
                event.getEventName(),
                event.getEventDescription(),
                event.getEventDate(),
                event.getEventRegion(),
                event.getImage(),
                event.getDuration(), // ✅ Added duration
                isVolunteer ? null : event.getMaxParticipants(),
                null);
    }

    @Override
    public List<EventResponse> searchEventsByName(String name) {
        List<Event> events = eventRepository.findByEventNameContainingIgnoreCase(name);
        return events.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<EventResponse> getSuggestedEvents(Region region, String name) {
        List<Event> events;

        if (region != null && name != null) {
            events = eventRepository.findByEventRegionAndEventNameContainingIgnoreCase(region, name);
        } else if (region != null) {
            events = eventRepository.findByEventRegion(region);
        } else if (name != null) {
            events = eventRepository.findByEventNameContainingIgnoreCase(name);
        } else {
            events = eventRepository.findAll();
        }

        return events.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<EventResponse> getVolunteerEvents(User user) {
        String username = user.getUsername();
        Volunteer volunteer = volunteerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Volunteer not found with username: " + username));
        return volunteer.getEvents().stream()
                .map(event -> convertToResponse(event, "param"))
                .collect(Collectors.toList());
    }
}
