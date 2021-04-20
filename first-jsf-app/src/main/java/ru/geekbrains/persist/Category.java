package ru.geekbrains.persist;



import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "deleteCategoryById", query = "delete from Category c where c.id = :id"),
        @NamedQuery(name = "findAllCategory", query = "select c from Category c"),
        @NamedQuery(name = "countCategories", query = "select count(c) from Category c")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)//категория относится ко многим продуктам
    private List<Product> products;


    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
