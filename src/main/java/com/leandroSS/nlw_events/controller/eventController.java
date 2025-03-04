package com.leandroSS.nlw_events.controller;

import com.leandroSS.nlw_events.entity.Event;
import com.leandroSS.nlw_events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class eventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/")
    public Event addNewEvent(@RequestBody Event event) {
        return eventService.addNewEvent(event);
    }
}
