package com.muchirikennedy.app.user.control;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.muchirikennedy.app.user.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class UserManager {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveUser(User user) {
        em.persist(user);
    }

    public Optional<User> findUser(int userId) {
        return Optional.ofNullable(em.find(User.class, userId));
    }

    @Transactional
    public boolean deleteUser(int userId) {
        var rows = em.createNamedQuery("deleteUser")
                .setParameter("id", userId)
                .executeUpdate();
        return rows == 1;
    }
}
