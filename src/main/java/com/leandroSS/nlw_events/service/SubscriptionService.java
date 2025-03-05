package com.leandroSS.nlw_events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandroSS.nlw_events.entity.Event;
import com.leandroSS.nlw_events.entity.Subscription;
import com.leandroSS.nlw_events.entity.User;
import com.leandroSS.nlw_events.exception.EventNotFoundException;
import com.leandroSS.nlw_events.exception.SubscriptionConflitException;
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

        if (event == null) {
            throw new EventNotFoundException("event " + eventName + " não existe");
        }

        User userExists = userRepository.findByEmail(user.getEmail());

        if (userExists == null) {
            userExists = userRepository.save(user);
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(userExists);

        Subscription subscriptionExists = subscriptionRepository.findByEventAndSubscriber(event, userExists);
        if (subscriptionExists != null) {
            throw new SubscriptionConflitException("Ja existe uma inscrição para o usuario "
                    + userExists.getName() + " no evento " + event.getTitle());
        }

        Subscription result = subscriptionRepository.save(subscription);
        return result;

    }

}
