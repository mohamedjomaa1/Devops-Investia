package tn.esprit.investia.services;

import tn.esprit.investia.entities.User;

import java.util.List;

public interface IUserInterface {

    User addUser(User user);

    List<User> getUser();

    void deleteUser(Long id);

    User updateUser(User user);

    User getUserById(Long id);

    User getUserByEmail(String email);
}
