package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public User create(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    public List<User> read() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    public User update(User entity) {
        entityManager.merge(entity);
        return entity;
    }

    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        Query query = entityManager.createQuery("select u from User u where u.login=:login and u.password=:password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        List<User> userList = query.getResultList();
        if (userList.size() != 0) {
            return (User) query.getSingleResult();
        } else {
            return null;
        }
    }

    @Override
    public User findUserById(int id) {
        Query query = entityManager.createQuery("select u from User u where u.id=:id");
        query.setParameter("id", id);
        List<User> userList = query.getResultList();
        if (userList.size() != 0) {
            return (User) query.getSingleResult();
        } else {
            return null;
        }
    }
}
