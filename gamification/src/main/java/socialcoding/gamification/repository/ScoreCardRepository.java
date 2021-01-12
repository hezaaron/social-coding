package socialcoding.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import socialcoding.gamification.entity.ScoreCard;
import socialcoding.gamification.model.LeaderBoardRow;

public interface ScoreCardRepository extends JpaRepository<ScoreCard, Long> {

	@Query("SELECT SUM(s.score) FROM socialcoding.gamification.entity.ScoreCard s "
			+ "where s.username = :username GROUP BY s.username")
	int getTotalScoreForUser(@Param("username") final String username);
	
	@Query("SELECT NEW socialcoding.gamification.model.LeaderBoardRow(s.username, SUM(s.score))"
			+ "FROM socialcoding.gamification.entity.ScoreCard s "
			+ "GROUP BY s.username ORDER BY SUM(s.score) DESC")
	List<LeaderBoardRow> findFirst10();
	
	List<ScoreCard> findByUsernameOrderByScoreTimeDesc(String username);
}
