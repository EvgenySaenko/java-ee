package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
@Named
public class ProductRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Resource
    private UserTransaction ut;

    @PostConstruct
    public void init() {
        if (countProducts() == 0){
            try{
                ut.begin();
                save(new Product(null,"Product1","Description 1", new BigDecimal(100)));
                save(new Product(null,"Product2","Description 2", new BigDecimal(200)));
                save(new Product(null,"Product3","Description 3", new BigDecimal(300)));
                save(new Product(null,"Фанта","лимонад", new BigDecimal(120)));
                ut.commit();
            }catch(Exception ex){
                try{
                    ut.rollback();
                }catch (SystemException e){
                    throw new RuntimeException(e);
                }
                throw new RuntimeException(ex);
            }
        }
    }

    @Transactional
    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        }
        em.merge(product);
    }

    @Transactional
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

    public Long countProducts() {
        return em.createNamedQuery("countProducts",Long.class).getSingleResult();
    }
}