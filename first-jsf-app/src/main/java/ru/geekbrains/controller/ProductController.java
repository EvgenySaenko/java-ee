package ru.geekbrains.controller;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.dto.ProductDto;
import ru.geekbrains.service.ProductService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class ProductController implements Serializable {


    @EJB
    private ProductService productService;

    @EJB
    private CategoryRepository categoryRepository;

    private ProductDto productDto;

    private List<ProductDto> productList;

    private List<Category> categoryList;

    //при загрузке страницы срабатывает и выполняет предзагрузку и сохраняет в список
    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.productList = productService.findAllWithCategoryFetch();
        this.categoryList = categoryRepository.findAll();
    }

    public ProductDto getProduct() {
        return productDto;
    }

    public void setProduct(ProductDto productDto){
        this.productDto = productDto;
    }

    //тут мы просто читаем список, чтобы каждый раз при обновлении хибернейт не делал селект
    public List<ProductDto> findAll(){
        return  productList;
    }

    public String editProduct(ProductDto productDto){
        this.productDto = productDto;
        return "/product_form.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductDto productDto) {
        productService.delete(productDto.getId());
    }

    public String saveProduct() {
        productService.save(productDto);
        return "/product.xhtml?faces-redirect=true";
    }

    public String addProduct() {
        this.productDto = new ProductDto();
        return "/product_form.xhtml?faces-redirect=true";
    }

    public List<Category> getCategories(){
        return categoryList;
    }
}
