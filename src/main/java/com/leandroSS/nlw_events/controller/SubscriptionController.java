package com.leandroSS.nlw_events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandroSS.nlw_events.entity.Subscription;
import com.leandroSS.nlw_events.entity.User;
import com.leandroSS.nlw_events.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/{prettyName}")
    public ResponseEntity<Subscription> createSubscription(
            @PathVariable String prettyName,
            @RequestBody User subscriber) {

        Subscription result = subscriptionService.createSubscription(prettyName, subscriber);

        if (result != null) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.badRequest().build();

    }

}
