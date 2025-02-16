package com.benevolekarizma.benevolekarizma.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("ORGANIZATION")
public class Organization extends User {

    private String orgName;

    private String orgPhoneNumber;

    private String orgAddress;

    @OneToMany(mappedBy = "organization")
    private List<Event> events;

}
