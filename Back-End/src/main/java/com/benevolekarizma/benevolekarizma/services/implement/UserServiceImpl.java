package com.benevolekarizma.benevolekarizma.services.implement;

import com.benevolekarizma.benevolekarizma.DTO.event.EventResponse;
import com.benevolekarizma.benevolekarizma.exceptions.UsernameNotFoundException;
import com.benevolekarizma.benevolekarizma.mapper.EventMapper;
import com.benevolekarizma.benevolekarizma.models.Event;
import com.benevolekarizma.benevolekarizma.models.User;
import com.benevolekarizma.benevolekarizma.models.Volunteer;
import com.benevolekarizma.benevolekarizma.repositories.EventRepository;
import com.benevolekarizma.benevolekarizma.repositories.UserRepository;
import com.benevolekarizma.benevolekarizma.services.interfaces.UserServiceInterface;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {
    private final UserRepository userRepository;
    private EventMapper eventMapper;
    private final EventRepository eventRepository;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public List<EventResponse> BrowseEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventResponse> getEventrs = new ArrayList<>();
        for (Event event : events) {
            getEventrs.add(eventMapper.toResponse(event));
        }
        return getEventrs;
    }

    public List<EventResponse> BrowseUserEvents(long idVolunteer) {
        Volunteer volunteer = (Volunteer) userRepository.findById(idVolunteer).get();
        List<EventResponse> volunteerEvents = new ArrayList<>();
        for (Event event : volunteer.getEvents()) {
            volunteerEvents.add(eventMapper.toResponse(event));
        }
        return volunteerEvents;
    }

    public void RegisterToEvent(long idEvent, long idVolunteer) {
        Event event1 = eventRepository.getReferenceById(idEvent);
        User user = (Volunteer) userRepository.findById(idVolunteer).get();
        ((Volunteer) user).getEvents().add(event1);
        userRepository.save(user);
    }

    public void ResignFromAnEvent(Long idUSer, long idEvent) {
        Volunteer volunteer = (Volunteer) userRepository.findById(idUSer).get();
        Event event = eventRepository.findById(idEvent).get();
        volunteer.getEvents().remove(event);
        userRepository.save(volunteer);

    }

    @Override
    public void hoursServed(long idUser) {
        Volunteer volunteer = (Volunteer) userRepository.findById(idUser).get();
        int totalHoursServed = volunteer.getHoursVolunteer();
        for (Event event : volunteer.getEvents())
            totalHoursServed += event.getDuration() * 24;
        volunteer.setHoursVolunteer(totalHoursServed);
        userRepository.save(volunteer);
    }

    @Override
    public User getByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public User getByUserId(long idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user id : " + idUser));
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

}
