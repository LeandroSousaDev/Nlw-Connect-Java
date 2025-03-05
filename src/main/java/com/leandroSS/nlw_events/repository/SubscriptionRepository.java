package com.leandroSS.nlw_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandroSS.nlw_events.entity.Event;
import com.leandroSS.nlw_events.entity.Subscription;
import com.leandroSS.nlw_events.entity.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);

}
