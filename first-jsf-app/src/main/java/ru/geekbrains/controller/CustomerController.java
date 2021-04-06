package ru.geekbrains.controller;

import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomerRepository;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CustomerController implements Serializable {

    @Inject
    private CustomerRepository customerRepository;

    private Customer customer;

    private List<Customer> customerList;

    //при загрузке страницы срабатывает и выполняет предзагрузку и сохраняет в список
    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.customerList = customerRepository.findAll();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public List<Customer> findAll(){
        return customerList;
    }

    public String editCustomer(Customer customer){
        this.customer = customer;
        return "/customer_form.xhtml?faces-redirect=true";
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer.getId());
    }

    public String saveCustomer() {
        customerRepository.save(customer);
        return "/customer.xhtml?faces-redirect=true";
    }

    public String addCustomer() {
        this.customer = new Customer();
        return "/customer_form.xhtml?faces-redirect=true";
    }
}
