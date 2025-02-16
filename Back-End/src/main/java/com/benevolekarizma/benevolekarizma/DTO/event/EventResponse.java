package com.benevolekarizma.benevolekarizma.DTO.event;

import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerResponse;
import com.benevolekarizma.benevolekarizma.models.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private Long idEvent;
    private String eventName;
    private String eventDescription;
    private Date eventDate;
    private Region eventRegion;
    private String image;
    private int maxParticipants;
    private int duration;
    private List<VolunteerResponse> volunteers;
}
