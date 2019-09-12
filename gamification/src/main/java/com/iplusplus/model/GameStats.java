package com.iplusplus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class GameStats {

	private final String userName;
	private final int score;
	private final List<Badge> badges;
	
	public GameStats() {
		this.userName = null;
		this.score = 0;
		this.badges = new ArrayList<>();
	}
	
	public static GameStats emptyStats(final String userName) {
		return new GameStats(userName, 0, Collections.emptyList());
	}
	
	public List<Badge> getBadges() {
		return Collections.unmodifiableList(badges);
	}
}
