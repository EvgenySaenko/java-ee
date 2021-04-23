package ru.geekbrains.service;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.dto.CategoryDto;


import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryService {

     void save(CategoryDto categoryDto);

     void delete(Long id);

     CategoryDto findById(Long id);

     CategoryDto getReference(Long id);

     List<CategoryDto> findAll();

     Long countCategories();
}
