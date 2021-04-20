package ru.geekbrains.service;

import ru.geekbrains.persist.dto.ProductDto;

import java.util.List;

public interface ProductServiceRemote {

    List<ProductDto> findAllRemote();
}
