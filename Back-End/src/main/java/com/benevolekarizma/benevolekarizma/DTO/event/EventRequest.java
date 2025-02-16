package com.benevolekarizma.benevolekarizma.DTO.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import com.benevolekarizma.benevolekarizma.models.enums.Region;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String eventName;
    private String eventDescription;
    private Date eventDate;
    private String image;
    private Region eventRegion;
    private int maxParticipants;
    private int duration;
}
