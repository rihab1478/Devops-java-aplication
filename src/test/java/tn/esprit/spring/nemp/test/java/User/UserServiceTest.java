package tn.esprit.spring.nemp.test.java.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.spring.nemp.Entities.User;
import tn.esprit.spring.nemp.Excceptions.BadRequestException;
import tn.esprit.spring.nemp.Repositorys.UserRepository;
import tn.esprit.spring.nemp.Services.UserService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    //the implementation in the repository are good no need to  @Autowired
    @Mock private UserRepository userrepo;
    private UserService userserv;


    @BeforeEach
    void setUp() {
        userserv = new UserService(userrepo);
    }



    @Test
    void getAllUsers() {
        //when
        userserv.getAllUsers();
        //then
        verify(userrepo).findAll();

    }

    @Test
    void getUserById() {
        // Given
        int userId = 1;
        User user = new User(1, "12345678", 98765432, "John", "Doe", "example@example.com");
        when(userrepo.findById(userId)).thenReturn(Optional.of(user));

        // When
        User result = userserv.getUserById(userId);

        // Then
        //assertThat(expected,the test it show an error if it doesn't match the expected)
        assertThat(result).isEqualTo(user);
    }

    @Test
    void createUser() {
        //given
        String adress = "bizerte";
        User user = new User (2,"11404196",53948594,"rihab","nabli",adress);
        //when
        userserv.createUser(user);

        //then
        ArgumentCaptor<User> UserArgumentCaptor =
                ArgumentCaptor.forClass(User.class);
        verify(userrepo).save(UserArgumentCaptor.capture());

        User capturedUser =  UserArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
         }

    @Test
    void WillthrowWhenEmailIsTakin() {
        //given
        String adress = "bizerte";
        User user = new User (2,"11404196",53948594,"rihab","nabli",adress);
        given(userrepo.selectExistsEmail(anyString())).willReturn(true);

        //when


        //then
assertThatThrownBy(() -> userserv.createUser(user)  )
        .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email"+user.getAdress()+"taken");
    verify(userrepo,never()).save(any());
    //save never exected

    }

    @Test
    void updateUser() {

        int userId = 1;
        User updatedUser = new User(1, "12345678", 98765432, "Updated", "User", "updated@example.com");

        // Mock the behavior of findById and save methods
        when(userrepo.findById(userId)).thenReturn(Optional.of(updatedUser));

        // When
        userserv.updateUser(userId, updatedUser);

        // Then
        verify(userrepo).save(updatedUser);
    }

    @Test
    void deleteUser() {

        int userId = 1;

        // When
        userserv.deleteUser(userId);

        // Then
        verify(userrepo).deleteById(userId);
    }
}