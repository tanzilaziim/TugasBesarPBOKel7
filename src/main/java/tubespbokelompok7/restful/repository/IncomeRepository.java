package tubespbokelompok7.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tubespbokelompok7.restful.entity.Income;
import tubespbokelompok7.restful.entity.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface IncomeRepository extends JpaRepository <Income, String> {
    Optional<Income> findFirstByUserAndId(User user, String id);

    @Query("SELECT COALESCE(SUM(i.price * i.amount), 0) FROM Income i WHERE i.user = :user")
    double sumIncome(@Param("user") User user);
}
