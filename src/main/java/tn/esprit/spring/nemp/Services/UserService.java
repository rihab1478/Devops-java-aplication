package tn.esprit.spring.nemp.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.nemp.Entities.User;
import tn.esprit.spring.nemp.Excceptions.BadRequestException;
import tn.esprit.spring.nemp.Interfaces.UserInterface;
import tn.esprit.spring.nemp.Repositorys.UserRepository;

import java.util.List;
@AllArgsConstructor
@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {

        return (List<User>) this.userRepository.findAll();    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse((User) null);
    }

    @Override
    public User createUser(User user) {
        Boolean existsEmail = userRepository.selectExistsEmail(user.getAdress());
        if(existsEmail)
        {
            throw new BadRequestException("Email" + user.getAdress()+ "taken");
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User updatedUser) {
        User existingOrder = getUserById(id);

        existingOrder.setPrenom(updatedUser.getPrenom());
        existingOrder.setTel(updatedUser.getTel());

        existingOrder.setNom(updatedUser.getNom());
        existingOrder.setCin(updatedUser.getCin());
        existingOrder.setAdress(updatedUser.getAdress());

        return userRepository.save(existingOrder);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);

    }
}
