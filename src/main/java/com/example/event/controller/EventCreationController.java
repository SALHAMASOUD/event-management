package com.example.event.controller;

import com.example.event.model.Event;
import com.example.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
public class EventCreationController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(
            @RequestParam("title") String title,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("location") String location,
            @RequestParam("description") String description,
            @RequestParam("ticketPrices") String ticketPrices,
            @RequestParam(value = "banner", required = false) MultipartFile banner,
            @RequestParam(value = "agenda", required = false) MultipartFile agenda,
            @RequestParam("reminders") boolean reminders,
            @RequestParam("feedback") boolean feedback) throws IOException {

        Event event = new Event();
        event.setTitle(title);
        event.setDate(java.sql.Date.valueOf(date));
        event.setTime(time);
        event.setLocation(location);
        event.setDescription(description);
        event.setTicketPrices(ticketPrices);

        if (banner != null) {
            event.setBanner(new String(banner.getBytes()));  // handle file as needed
        }

        if (agenda != null) {
            event.setAgenda(new String(agenda.getBytes()));  // handle file as needed
        }

        event.setReminders(reminders);
        event.setFeedback(feedback);

        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }
}
