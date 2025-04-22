package com.muchirikennedy.app.user.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muchirikennedy.app.user.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserManager {

    @Autowired
    private EntityManager em;

    @Transactional
    public void saveUser(User user) {
        em.persist(user);
    }

    public Optional<User> findUser(int userId) {
        return Optional.ofNullable(em.find(User.class, userId));
    }
}
