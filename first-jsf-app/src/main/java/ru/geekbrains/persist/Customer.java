package ru.geekbrains.persist;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@NamedQueries({
        @NamedQuery(name = "deleteCustomerById", query = "delete from Customer c where c.id = :id"),
        @NamedQuery(name = "findAllCustomer", query = "select c from Customer c"),
        @NamedQuery(name = "countCustomers", query = "select count(c) from Customer c")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    public Customer() {
    }

    public Customer(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
