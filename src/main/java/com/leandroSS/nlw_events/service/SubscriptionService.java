package com.leandroSS.nlw_events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandroSS.nlw_events.entity.Event;
import com.leandroSS.nlw_events.entity.Subscription;
import com.leandroSS.nlw_events.entity.User;
import com.leandroSS.nlw_events.repository.EventRepository;
import com.leandroSS.nlw_events.repository.SubscriptionRepository;
import com.leandroSS.nlw_events.repository.UserRepository;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    public Subscription createSubscription(String eventName, User user) {

        Event event = eventRepository.findByPrettyName(eventName);

        User userExists = userRepository.findByEmail(user.getEmail());

        if (userExists == null) {
            userExists = userRepository.save(user);
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(userExists);

        Subscription result = subscriptionRepository.save(subscription);
        return result;

    }

}
