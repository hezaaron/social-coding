package com.iplusplus.gamification.entity;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.iplusplus.gamification.model.Badge;
import com.iplusplus.gamification.model.BagTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Getter @ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class BadgeCard {

	@Id @GeneratedValue
	private final Long id;
	private final String username;
	private final Badge badge;
	private final LocalDateTime badgeTime;
	
	public BadgeCard() {
		this(null, null, null, null);
	}
	
	public BadgeCard(final String username, final Badge badge) {
		this(null, username, badge, new BagTime(Clock.systemDefaultZone()).getTime());
	}
}
