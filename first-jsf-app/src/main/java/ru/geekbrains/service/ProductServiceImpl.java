package ru.geekbrains.service;

import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.dto.ProductDto;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;

//@Singleton(ejb) равна этим двум  = @ApplicationScoped @Named
@Stateless//некий бин(без состояния) внутри не хранит никаких данных
@Remote(ProductServiceRemote.class)// - ProductServiceRemote.class  хотим использовать в качестве удаленного интерфейса
public class ProductServiceImpl implements ProductService, ProductServiceRemote {

    //лучше не смешивать если внедряем через javax.ejb то использовать ее анотации
    @EJB//для внедрения бинов рекомендуется использовать EJB а не Inject(так как бин у нас по технологии EJB через Stateless
    private ProductRepository productRepository;

    @EJB
    private CategoryRepository categoryRepository;

    @Override
    @TransactionAttribute
    public void save(ProductDto productDto) {
        productRepository.save(new Product(productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                categoryRepository.getReference(productDto.getCategoryId())
        ));
    }

    @Override
    @TransactionAttribute
    public void delete(Long id) {
        productRepository.delete(id);
    }

    @Override
    public ProductDto findById(long id) {
        return createProductDtoWithCategory(productRepository.findById(id));
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductServiceImpl::createProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllWithCategoryFetch() {
        return productRepository.findAllWithCategoryFetch().stream()
                .map(ProductServiceImpl::createProductDtoWithCategory)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return productRepository.countProducts();
    }

    private static ProductDto createProductDtoWithCategory(Product product){
        return new ProductDto(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null);
    }

    private static ProductDto createProductDto(Product product){
        return new ProductDto(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                null,
                null);
    }

    @Override//реализуем метод удаленного продукт сервиса
    public List<ProductDto> findAllRemote() {
        return findAllWithCategoryFetch();
    }
}
