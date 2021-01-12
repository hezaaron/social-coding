package socialcoding.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import socialcoding.gamification.entity.BadgeCard;

public interface BadgeCardRepository extends JpaRepository<BadgeCard, Long> {
	List<BadgeCard> findByUsernameOrderByBadgeTimeDesc(String username);
}
