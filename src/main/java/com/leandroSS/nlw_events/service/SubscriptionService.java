package com.leandroSS.nlw_events.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandroSS.nlw_events.dto.SubscriptionRankByUser;
import com.leandroSS.nlw_events.dto.SubscriptionRankItem;
import com.leandroSS.nlw_events.dto.SubscriptionResponse;
import com.leandroSS.nlw_events.entity.Event;
import com.leandroSS.nlw_events.entity.Subscription;
import com.leandroSS.nlw_events.entity.User;
import com.leandroSS.nlw_events.exception.EventNotFoundException;
import com.leandroSS.nlw_events.exception.SubscriptionConflitException;
import com.leandroSS.nlw_events.exception.UserIndicadorNotFoundException;
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

    public SubscriptionResponse createSubscription(String eventName, User user, Integer userId) {

        Event event = eventRepository.findByPrettyName(eventName);

        if (event == null) {
            throw new EventNotFoundException("event " + eventName + " não existe");
        }

        User userExists = userRepository.findByEmail(user.getEmail());

        if (userExists == null) {
            userExists = userRepository.save(user);
        }

        User indicador = null;

        if (userId != null) {
            indicador = userRepository.findById(userId).orElse(null);
        }

        if (indicador == null) {
            throw new UserIndicadorNotFoundException("usuario " + userId + " indicador");
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(userExists);
        subscription.setIndication(indicador);

        Subscription subscriptionExists = subscriptionRepository.findByEventAndSubscriber(event, userExists);
        if (subscriptionExists != null) {
            throw new SubscriptionConflitException("Ja existe uma inscrição para o usuario "
                    + userExists.getName() + " no evento " + event.getTitle());
        }

        Subscription result = subscriptionRepository.save(subscription);
        return new SubscriptionResponse(
                result.getSubscriptionNumber(),
                "https://codecraft.com/" + result.getEvent().getPrettyName() + "/" + result.getSubscriber().getId());

    }

    public List<SubscriptionRankItem> getCompleteRank(String prettyName) {

        Event event = eventRepository.findByPrettyName(prettyName);

        if (event == null) {
            throw new EventNotFoundException("Evento " + prettyName + " não existe");
        }

        return subscriptionRepository.generateRancking(event.getEventId());
    }

    public SubscriptionRankByUser getRankUser(String prettyName, Integer userId) {
        List<SubscriptionRankItem> ranking = getCompleteRank(prettyName);

        SubscriptionRankItem item = ranking.stream()
                .filter(i -> i.userId().equals(userId)).findFirst().orElse(null);

        if (item == null) {
            throw new UserIndicadorNotFoundException("não há inscrições com indicação do usuario " + userId);
        }

        Integer position = IntStream.range(0, ranking.size())
                .filter(p -> ranking.get(p).userId().equals(userId))
                .findFirst().getAsInt();

        return new SubscriptionRankByUser(item, position + 1);
    }
}
