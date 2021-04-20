package ru.geekbrains.persist;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@ApplicationScoped
//@Named
//так как этот класс не хранит в себе никакой информации
//а испольняет роль посредника для работы с базой данных
//не имеет смысла создавать его сразу при поднятии приложения и в единственном экземпляре(singleton)
//@Singleton - класс создается при запуске приложения и на протяжении существования приложения будет существовать
//@Stateless  -  1.создается лениво, создастся при первом обращении к какому нибудь его методу
//             - 2.при необходимости сервер может создавать несколько экземпляров этого класса
//               за счет этого мы можем добиться масштабируемости нашего приложения, например из серверов wildfly можно использовать кластеры
@Stateless
public class ProductRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        }
        em.merge(product);
    }

    public void delete(Long id) {
        em.createNamedQuery("deleteProductById")
                .setParameter("id", id) //id равен id которому мы передали
                .executeUpdate();
    }

    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createNamedQuery("findAllProduct",Product.class).getResultList();
    }

    public List<Product> findAllWithCategoryFetch(){
        return em.createNamedQuery("findAllWithCategoryFetch",Product.class).getResultList();
    }

    public Long countProducts() {
        return em.createNamedQuery("countProducts",Long.class).getSingleResult();
    }
}