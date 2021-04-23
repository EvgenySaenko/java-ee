package ru.geekbrains.service;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.dto.CategoryDto;
import ru.geekbrains.rest.CategoryResource;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CategoryServiceImpl implements CategoryService, CategoryResource {

    @EJB
    private CategoryRepository categoryRepository;

    @Override
    @TransactionAttribute
    public void save(CategoryDto categoryDto){
        categoryRepository.save(new Category(categoryDto.getId(),categoryDto.getName()));
    }

    @Override
    @TransactionAttribute
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

    @Override
    public CategoryDto findById(Long id) {
        return createCategoryDto(categoryRepository.findById(id));
    }

    @Override
    public void insert(CategoryDto categoryDto) {
        if (categoryDto.getId() != null){
            throw new IllegalArgumentException("Not null id in the inserted Category");
        }
        save(categoryDto);
    }

    @Override
    public void update(CategoryDto categoryDto) {
        if (categoryDto.getId() == null){
            throw new IllegalArgumentException("Not id in the inserted Category");
        }
        save(categoryDto);
    }


    @Override
    public CategoryDto getReference(Long id) {
        return createCategoryDto(categoryRepository.getReference(id));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(CategoryServiceImpl::createCategoryDto).collect(Collectors.toList());
    }

    @Override
    public Long countCategories(){
        return categoryRepository.countCategories();
    }

    private static CategoryDto createCategoryDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName());
    }
}
