package ru.geekbrains.controller;

import ru.geekbrains.persist.dto.CategoryDto;
import ru.geekbrains.service.CategoryService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CategoryController implements Serializable {


    @EJB
    private CategoryService categoryService;

    private CategoryDto categoryDto;

    private List<CategoryDto> categoryList;

    //при загрузке страницы срабатывает и выполняет предзагрузку и сохраняет в список
    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.categoryList = categoryService.findAll();

    }

    public CategoryDto getCategory() {
        return categoryDto;
    }

    public void setCategory(CategoryDto categoryDto){
        this.categoryDto = categoryDto;
    }

    public List<CategoryDto> findAll(){
        return categoryList;
    }

    public String editCategory(CategoryDto categoryDto){
        this.categoryDto = categoryDto;
        return "/category_form.xhtml?faces-redirect=true";
    }

    public void deleteCategory(CategoryDto categoryDto) {
        categoryService.delete(categoryDto.getId());
    }

    public String saveCategory() {
        categoryService.save(categoryDto);
        return "/category.xhtml?faces-redirect=true";
    }

    public String addCategory() {
        this.categoryDto = new CategoryDto();
        return "/category_form.xhtml?faces-redirect=true";
    }
}
