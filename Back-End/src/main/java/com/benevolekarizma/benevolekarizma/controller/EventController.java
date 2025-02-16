package com.benevolekarizma.benevolekarizma.controller;

import com.benevolekarizma.benevolekarizma.DTO.event.EventRequest;
import com.benevolekarizma.benevolekarizma.DTO.event.EventResponse;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerResponse;
import com.benevolekarizma.benevolekarizma.models.enums.Region;
import com.benevolekarizma.benevolekarizma.services.interfaces.EventServiceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventServiceInterface eventService;

    public EventController(EventServiceInterface eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.createEvent(eventRequest));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @GetMapping("/organizer/{userId}")
    public ResponseEntity<List<EventResponse>> getOrganizationEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getAllEvents(userId));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long eventId,
            @RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.updateEvent(eventId, eventRequest));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/register/{userId}")
    public ResponseEntity<EventResponse> registerVolunteer(@PathVariable Long eventId, @PathVariable Long userId) {
        return ResponseEntity.ok(eventService.registerVolunteer(eventId, userId));
    }

    @GetMapping("/{eventId}/volunteers")
    public ResponseEntity<List<VolunteerResponse>> getEventVolunteers(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventVolunteers(eventId));
    }

    @GetMapping("/{eventId}/user/{userId}")
    public ResponseEntity<EventResponse> getEventDetailsForUser(@PathVariable Long eventId, @PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getEventDetailsForUser(eventId, userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<EventResponse>> searchEventsByName(@RequestParam String name) {
        return ResponseEntity.ok(eventService.searchEventsByName(name));
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<EventResponse>> getSuggestedEvents(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String name) {

        Region regionEnum = null;
        if (region != null) {
            try {
                regionEnum = Region.valueOf(region.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Collections.emptyList());
            }
        }

        return ResponseEntity.ok(eventService.getSuggestedEvents(regionEnum, name));
    }
}