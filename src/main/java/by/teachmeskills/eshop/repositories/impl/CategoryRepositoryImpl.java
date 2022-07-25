package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Category create(Category entity) {
        entityManager.persist(entity);
        return entity;
    }

    public List<Category> read() {
        return entityManager.createQuery("select c from Category c").getResultList();
    }

    public Category update(Category entity) {
        return entityManager.merge(entity);
    }

    public void delete(int id) {
        entityManager.remove(entityManager.find(Category.class, id));
    }

    @Override
    public Category findById(int id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public Category getCategoryFromDBByName(Category category) {
        Query query = entityManager.createQuery("select c from Category c where c.name=:name");
        query.setParameter("name", category.getName());
        List<Category> categoryList = query.getResultList();
        if (categoryList.size() != 0) {
            return (Category) query.getSingleResult();
        } else {
            return null;
        }
    }
}
