package tn.esprit.spring.nemp.test.java.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import tn.esprit.spring.nemp.Entities.User;
import tn.esprit.spring.nemp.Repositorys.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {
@Autowired
private UserRepository userrepo;

    @AfterEach
    void tearDown() {
        userrepo.deleteAll();
    }

    @Test
    void ItShouldCheckIfUserExistsEmail() {
        //given
        String adress = "bizerte";
        User user = new User (2,"11404196",53948594,"rihab","nabli",adress);
        userrepo.save(user);
        //when

       boolean  expected =  userrepo.selectExistsEmail(adress);
        //then
     assertThat(expected).isTrue();


    }
    @Test
    void ItShouldCheckIfUserDoseNotExistsEmail() {
        //given
        String adress = "bizerte";

        //when

        boolean  expected =  userrepo.selectExistsEmail(adress);
        //then
        assertThat(expected).isFalse();


    }



}