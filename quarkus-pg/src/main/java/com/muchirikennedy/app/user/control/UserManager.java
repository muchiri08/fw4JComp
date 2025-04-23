package com.muchirikennedy.app.user.control;

import java.util.Optional;
import com.muchirikennedy.app.user.entity.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserManager {

    @Inject
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
