package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@ApplicationScoped
@Named
public class CategoryRepository {

    private final Map<Long, Category> categoryMap = new ConcurrentHashMap<>();

    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        save(new Category(null,"electronics"));
        save(new Category(null,"food"));
        save(new Category(null,"drinks"));
    }

    public void save(Category category){
        if (category.getId() == null){
            category.setId(identity.incrementAndGet());
        }
        categoryMap.put(category.getId(),category);
    }

    public void delete(Long id) {
        categoryMap.remove(id);
    }

    public Category findById(Long id) {
        return categoryMap.get(id);
    }

    public List<Category> findAll() {
        return new ArrayList<>(categoryMap.values());
    }
}
