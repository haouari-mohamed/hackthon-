package com.benevolekarizma.benevolekarizma.models;

import com.benevolekarizma.benevolekarizma.models.enums.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("removed_at IS NULL")
@SQLDelete(sql = "UPDATE events SET removed_at = CURRENT_TIMESTAMP WHERE id_event=?")
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEvent;

    private String eventName;

    private String eventDescription;

    private int duration;

    private String image;

    private Date eventDate;

    @Enumerated(EnumType.STRING)
    private Region eventRegion;

    private int maxParticipants;

    @ManyToOne
    private Organization organization;

    @ManyToMany(mappedBy = "events")
    private List<Volunteer> volunteers;

    @Column(nullable = true)
    private LocalDateTime removedAt;

}
