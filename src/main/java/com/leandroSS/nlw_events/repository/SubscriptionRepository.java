package com.leandroSS.nlw_events.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leandroSS.nlw_events.dto.SubscriptionRankItem;
import com.leandroSS.nlw_events.entity.Event;
import com.leandroSS.nlw_events.entity.Subscription;
import com.leandroSS.nlw_events.entity.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);

    @Query(value = """
            select count(subscription_number) as quantidade, indication_user_id, user_name
            from tb_subscription inner join tb_user
            on tb_subscription.indication_user_id = tb_user.user_id
            where indication_user_id is not null
            and event_id = :eventId
            group by indication_user_id, user_name
            order by quantidade desc
                        """, nativeQuery = true)
    public List<SubscriptionRankItem> generateRancking(@Param("eventId") Integer eventId);

}
