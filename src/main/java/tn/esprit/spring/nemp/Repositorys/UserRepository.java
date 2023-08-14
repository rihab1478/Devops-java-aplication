package tn.esprit.spring.nemp.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.nemp.Entities.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.adress = ?1")
    Boolean selectExistsEmail(String adress);

}
