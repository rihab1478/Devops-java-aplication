package tn.esprit.spring.nemp.test.java;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.nemp.Entities.User;
import tn.esprit.spring.nemp.Repositorys.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class NemPTest {
    Calculator caltest = new Calculator();
    @Test
    void itShouldAdd2Numbers()
    {
        //given
        int nb1= 20 ;
        int nb2= 30 ;
        //When
        int result = caltest.add(nb1,nb2);
        //Then
        int expected = 50;
        assertThat(result).isEqualTo(expected);


    }



class Calculator {

    int add(int a,int b)
    {

        return  a+b ;
    }

}



}
