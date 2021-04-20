package ru.geekbrains.service;

import ru.geekbrains.persist.dto.ProductDto;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CartService {
    void add(ProductDto productDto);

    void remove(long id);

    List<ProductDto>  findAll();
}
