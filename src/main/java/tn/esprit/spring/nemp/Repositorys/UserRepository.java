package tn.esprit.spring.nemp.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.nemp.Entities.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
}
