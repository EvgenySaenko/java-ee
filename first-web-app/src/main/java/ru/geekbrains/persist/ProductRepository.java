package ru.geekbrains.persist;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();

    //используем для генерации id
    private final AtomicLong identity = new AtomicLong(0);

    public void save(Product product) {
        if (product.getId() == null) {//присваиваем следующий id
            product.setId(identity.incrementAndGet());
        }
        productMap.put(product.getId(), product);
    }

    public void delete(Long id) {
        productMap.remove(id);
    }

    public Product findById(Long id) {
        return productMap.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }
}