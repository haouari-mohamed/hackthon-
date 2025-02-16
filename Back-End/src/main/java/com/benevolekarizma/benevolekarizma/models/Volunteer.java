package com.benevolekarizma.benevolekarizma.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity ;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("VOLUNTEER")
public class Volunteer extends User {

    @Builder.Default
    private int hoursVolunteer = 0;

    @ManyToMany
    @JoinTable(name = "participations", joinColumns = @JoinColumn(name = "volunteer_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
}
