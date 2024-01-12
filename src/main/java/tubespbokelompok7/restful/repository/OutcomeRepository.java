package tubespbokelompok7.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tubespbokelompok7.restful.entity.Outcome;
import tubespbokelompok7.restful.entity.User;

import java.util.Optional;

@Repository
public interface OutcomeRepository extends JpaRepository <Outcome, String> {
    Optional<Outcome> findFirstByUserAndId(User user, String id);

    @Query("SELECT COALESCE(SUM(o.outcome_total), 0) FROM Outcome o WHERE o.user = :user")
    double sumOutcome(@Param("user") User user);
}
