package com.benevolekarizma.benevolekarizma.controller;

import com.benevolekarizma.benevolekarizma.DTO.event.EventResponse;
import com.benevolekarizma.benevolekarizma.services.implement.UserServiceImpl;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {
    private final UserServiceImpl userService;

    public VolunteerController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/browse/{id}")
    public List<EventResponse> browseUserEvents(@PathVariable long id) {
        return userService.BrowseUserEvents(id);
    }

    @PostMapping("/register/{idEvent}/{idUser}")
    public void registerToEvent(@PathVariable long idEvent, @PathVariable long idUser) {
        userService.RegisterToEvent(idEvent, idUser);
    }

    @PostMapping("/resign/{idUser}/{idEvent}")
    public void resignFromAnEvent(@PathVariable long idUser, @PathVariable long idEvent) {
        userService.ResignFromAnEvent(idUser, idEvent);
    }

}
