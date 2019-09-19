package com.iplusplus.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.gamification.entity.BadgeCard;

public interface BadgeCardRepository extends JpaRepository<BadgeCard, Long> {
	List<BadgeCard> findByUsernameOrderByBadgeTimeDesc(String username);
}
