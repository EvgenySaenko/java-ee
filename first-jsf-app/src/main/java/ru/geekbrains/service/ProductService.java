package ru.geekbrains.service;


import ru.geekbrains.persist.dto.ProductDto;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProductService {

    void save(ProductDto product);

    void delete(Long id);

    ProductDto findById(long id);

    List<ProductDto> findAll();

    List<ProductDto> findAllWithCategoryFetch();

    long count();
}
