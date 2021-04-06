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
import java.util.List;


@ApplicationScoped
@Named
public class CustomerRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Resource
    private UserTransaction ut;


    @PostConstruct
    public void init() {
        if (countCustomers() == 0){
            try{
                ut.begin();
                save(new Customer(null,"JackRicher","123456"));
                save(new Customer(null,"John McClain","777"));
                ut.commit();
            }catch (Exception e){
                try {
                    ut.rollback();
                } catch (SystemException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            }
        }
    }
    @Transactional
    public void save(Customer customer){
        if (customer.getId() == null){
            em.persist(customer);
        }
        em.merge(customer);
    }

    @Transactional
    public void delete(Long id) {
        em.createNamedQuery("deleteCustomerById")
                .setParameter("id", id)
                .executeUpdate();
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        return em.createNamedQuery("findAllCustomer",Customer.class).getResultList();
    }

    public Long countCustomers(){
        return em.createNamedQuery("countCustomers",Long.class).getSingleResult();
    }
}
