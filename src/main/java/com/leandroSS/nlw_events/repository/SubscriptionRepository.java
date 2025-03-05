package com.leandroSS.nlw_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandroSS.nlw_events.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

}
