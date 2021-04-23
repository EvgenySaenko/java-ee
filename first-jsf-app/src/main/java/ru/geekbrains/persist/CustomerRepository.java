package ru.geekbrains.persist;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;


@Stateless
public class CustomerRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public void save(Customer customer){
        if (customer.getId() == null){
            em.persist(customer);
        }
        em.merge(customer);
    }

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
