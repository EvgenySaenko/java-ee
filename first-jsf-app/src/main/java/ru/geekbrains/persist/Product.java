package ru.geekbrains.persist;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NamedQueries({
        //создадим именнованый запрос для метода delete(ProductRepository)
        @NamedQuery(name = "deleteProductById", query = "delete from Product p where p.id = :id"),
        //создадим именнованый запрос для метода findALl
        @NamedQuery(name="findAllProduct", query = "select p from Product p"),//можно писать и сокращенно (from Product p)
        @NamedQuery(name = "findAllWithCategoryFetch", query = "select p from Product p left join fetch p.category"),
        @NamedQuery(name="countProducts", query = "select count(p) from Product p")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @ManyToOne//множество продуктов относятся к одной категории
    @JoinColumn(name = "category_id")
    private Category category;


    public Product() {
    }

    public Product(Long id, String name, String description, BigDecimal price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category =  category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
