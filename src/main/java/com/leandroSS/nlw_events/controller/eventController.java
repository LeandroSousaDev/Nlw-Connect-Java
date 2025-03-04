package com.leandroSS.nlw_events.controller;

import com.leandroSS.nlw_events.entity.Event;
import com.leandroSS.nlw_events.service.EventService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class eventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/")
    public Event addNewEvent(@RequestBody Event event) {
        return eventService.addNewEvent(event);
    }

    @GetMapping("/")
    public List<Event> getallEvents() {
        return eventService.getAllEvent();
    }

    @GetMapping("/{prettyName}")
    public ResponseEntity<Event> findByPrettyname(@PathVariable String prettyName) {
        var event = eventService.getByPrettyName(prettyName);

        if (event != null) {
            return ResponseEntity.ok().body(event);
        }
        return ResponseEntity.notFound().build();
    }
}
