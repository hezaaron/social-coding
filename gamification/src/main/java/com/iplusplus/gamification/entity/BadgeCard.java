package com.iplusplus.gamification.entity;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.persistence.Column;
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
	@Column(name = "BADGE_ID")
	private final Long badgeId;
	private final String userName;
	private final Badge badge;
	private final LocalDateTime badgeTime;
	
	public BadgeCard() {
		this(null, null, null, null);
	}
	
	public BadgeCard(final String userName, final Badge badge) {
		this(null, userName, badge, new BagTime(Clock.systemDefaultZone()).getTime());
	}
}
