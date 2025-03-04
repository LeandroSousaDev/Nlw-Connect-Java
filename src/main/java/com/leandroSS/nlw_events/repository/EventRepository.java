package com.leandroSS.nlw_events.repository;

import com.leandroSS.nlw_events.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
}
