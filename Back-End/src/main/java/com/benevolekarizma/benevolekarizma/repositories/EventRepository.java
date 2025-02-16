package com.benevolekarizma.benevolekarizma.repositories;

import com.benevolekarizma.benevolekarizma.models.Event;
import com.benevolekarizma.benevolekarizma.models.enums.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEventNameContainingIgnoreCase(String name);

    List<Event> findByEventRegion(Region region);

    List<Event> findByEventRegionAndEventNameContainingIgnoreCase(Region eventRegion, String eventName);

}
