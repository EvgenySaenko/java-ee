package ru.geekbrains.persist;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @TransactionAttribute
    public void save(Category category){
        if (category.getId() == null){
            em.persist(category);
        }
        em.merge(category);
    }

    @TransactionAttribute
    public void delete(Long id) {
        em.createNamedQuery("deleteCategoryById")
                .setParameter("id", id)
                .executeUpdate();
    }
    //полноценно извлекает из базы данных
    public Category findById(Long id) {
        return em.find(Category.class,id);
    }

    //не обращается к базе, а создает хэбернейтовскую ссылку на сущность Категорий у которой будет только id
    //используя этот метод избегаем обращения к базе данных
    public Category getReference(Long id) {
        return em.getReference(Category.class, id);
    }

    public List<Category> findAll() {
        return em.createNamedQuery("findAllCategory",Category.class).getResultList();
    }

    public Long countCategories(){
        return em.createNamedQuery("countCategories",Long.class).getSingleResult();
    }
}
