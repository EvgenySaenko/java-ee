package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@Named
public class CustomerRepository {
    private final Map<Long, Customer> customerMap = new ConcurrentHashMap<>();

    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        save(new Customer(null,"JackRicher","123456"));
        save(new Customer(null,"John McClain","777"));
    }

    public void save(Customer customer){
        if (customer.getId() == null){
            customer.setId(identity.incrementAndGet());
        }
        customerMap.put(customer.getId(),customer);
    }

    public void delete(Long id) {
        customerMap.remove(id);
    }

    public Customer findById(Long id) {
        return customerMap.get(id);
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customerMap.values());
    }
}
