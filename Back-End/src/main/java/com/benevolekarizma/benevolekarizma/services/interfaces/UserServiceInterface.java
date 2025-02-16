package com.benevolekarizma.benevolekarizma.services.interfaces;

import com.benevolekarizma.benevolekarizma.DTO.event.EventResponse;
import com.benevolekarizma.benevolekarizma.models.User;

import java.util.List;

public interface UserServiceInterface {

    List<EventResponse> BrowseEvents();

    void RegisterToEvent(long idEvent, long idUser);

    void ResignFromAnEvent(Long idUser, long idEvent);

    void hoursServed(long idUser);

    User getByUserName(String userName);

    User getByUserId(long idUser);

    void deleteUser(long idUser);
}
