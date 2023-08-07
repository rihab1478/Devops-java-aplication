package tn.esprit.spring.nemp.Interfaces;

import tn.esprit.spring.nemp.Entities.User;

import java.util.List;

public interface UserInterface {

    public List<User> getAllUsers();
    public User getUserById(int id);
    public User createUser(User user);
    public User updateUser(int id, User updatedUser);
    public void deleteUser(int id);

}
