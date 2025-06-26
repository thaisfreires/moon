package yoga.moon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yoga.moon.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long userId);
    
}
