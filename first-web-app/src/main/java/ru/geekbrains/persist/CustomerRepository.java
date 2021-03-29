package ru.geekbrains.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerRepository {
    private final Map<Long, Customer> customerMap = new ConcurrentHashMap<>();

    private final AtomicLong identity = new AtomicLong(0);

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
