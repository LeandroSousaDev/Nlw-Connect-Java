package com.leandroSS.nlw_events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandroSS.nlw_events.dto.ErrorMessage;
import com.leandroSS.nlw_events.dto.SubscriptionResponse;
import com.leandroSS.nlw_events.entity.User;
import com.leandroSS.nlw_events.exception.EventNotFoundException;
import com.leandroSS.nlw_events.exception.SubscriptionConflitException;
import com.leandroSS.nlw_events.exception.UserIndicadorNotFoundException;
import com.leandroSS.nlw_events.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({ "/{prettyName}", "/{prettyName}/{userId}" })
    public ResponseEntity<?> createSubscription(
            @PathVariable String prettyName,
            @RequestBody User subscriber,
            @PathVariable(required = false) Integer userId) {

        try {
            SubscriptionResponse result = subscriptionService.createSubscription(prettyName, subscriber, userId);

            if (result != null) {
                return ResponseEntity.ok().body(result);
            }

        } catch (EventNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));

        } catch (SubscriptionConflitException ex) {
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));

        } catch (UserIndicadorNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }

        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/{prettyName}/ranking")
    public ResponseEntity<?> generateRanking(@PathVariable String prettyName) {

        try {
            return ResponseEntity.ok().body(subscriptionService.getCompleteRank(prettyName)
                    .subList(0, 3));
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> generateRankingByEventAndUser(@PathVariable String prettyName,
            @PathVariable Integer userId) {

        try {
            return ResponseEntity.ok(subscriptionService.getRankUser(prettyName, userId));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

}
