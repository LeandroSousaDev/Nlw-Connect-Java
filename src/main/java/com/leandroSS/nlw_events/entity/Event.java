package com.leandroSS.nlw_events.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_event")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_Id")
    private Integer eventId;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "pretty_Name", length = 50, nullable = false, unique = true)
    private String prettyName;

    @Column(name = "location", length = 255, nullable = false)
    private String location;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public Event() {
    }

    public Event(Integer eventId,
                 String title,
                 String prettyName,
                 String location,
                 Double price,
                 LocalDate startDate,
                 LocalDate endDate,
                 LocalTime startTime,
                 LocalTime endTime) {
        this.eventId = eventId;
        this.title = title;
        this.prettyName = prettyName;
        this.location = location;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
