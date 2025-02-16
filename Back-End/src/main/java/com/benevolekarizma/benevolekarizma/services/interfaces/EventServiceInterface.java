package com.benevolekarizma.benevolekarizma.services.interfaces;

import com.benevolekarizma.benevolekarizma.DTO.event.EventRequest;
import com.benevolekarizma.benevolekarizma.DTO.event.EventResponse;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerResponse;
import com.benevolekarizma.benevolekarizma.mapper.EventMapper;
import com.benevolekarizma.benevolekarizma.models.Event;
import com.benevolekarizma.benevolekarizma.models.User;
import com.benevolekarizma.benevolekarizma.models.enums.Region;

import java.util.List;
import java.util.stream.Collectors;

public interface EventServiceInterface {

    List<EventResponse> getVolunteerEvents(User user);

    /**
     * Create a new event
     * 
     * @param eventRequest the event details
     * @return the created event response
     */
    default EventResponse createEvent(EventRequest eventRequest) {
        Event event = EventMapper.toEntity(eventRequest);
        return EventMapper.toResponse(saveEvent(event));
    }

    /**
     * Retrieve an event by its ID
     * 
     * @param eventId the ID of the event
     * @return the event response
     */
    default EventResponse getEventById(Long eventId) {
        return EventMapper.toResponse(findEventById(eventId));
    }

    /**
     * Retrieve all available events
     * 
     * @return a list of event responses
     */
    default List<EventResponse> getAllEvents() {
        return findAllEvents().stream()
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all available events for organization
     * 
     * @return a list of event responses
     */
    default List<EventResponse> getAllEvents(Long userId) {
        return findAllEvents().stream()
                .filter(event -> event.getOrganization().getIdUser().equals(userId))
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing event
     * 
     * @param eventId      the ID of the event to update
     * @param eventRequest the updated event details
     * @return the updated event response
     */
    default EventResponse updateEvent(Long eventId, EventRequest eventRequest) {
        Event event = findEventById(eventId);
        event.setEventName(eventRequest.getEventName());
        event.setEventDescription(eventRequest.getEventDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setEventRegion(eventRequest.getEventRegion());
        event.setMaxParticipants(eventRequest.getMaxParticipants());
        return EventMapper.toResponse(saveEvent(event));
    }

    /**
     * Delete an event by its ID
     * 
     * @param eventId the ID of the event to delete
     */
    void deleteEvent(Long eventId);

    /**
     * Register a user as a volunteer for an event
     * 
     * @param eventId the ID of the event
     * @param userId  the ID of the user
     * @return the updated event response
     */
    EventResponse registerVolunteer(Long eventId, Long userId);

    /**
     * Get a list of volunteers for a specific event
     * 
     * @param eventId the ID of the event
     * @return a list of volunteer IDs
     */
    List<VolunteerResponse> getEventVolunteers(Long eventId);

    // Abstract methods for implementation
    Event saveEvent(Event event);

    Event findEventById(Long eventId);

    List<Event> findAllEvents();

    EventResponse convertToResponse(Event event);

    EventResponse getEventDetailsForUser(Long eventId, Long userId);

    List<EventResponse> getSuggestedEvents(Region region, String name);

    List<EventResponse> searchEventsByName(String name);

}