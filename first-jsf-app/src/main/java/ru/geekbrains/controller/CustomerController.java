package ru.geekbrains.controller;


import ru.geekbrains.persist.dto.CustomerDto;
import ru.geekbrains.service.CustomerService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CustomerController implements Serializable {

    @EJB
    private CustomerService customerService;

    private CustomerDto customerDto;

    private List<CustomerDto> customerList;

    //при загрузке страницы срабатывает и выполняет предзагрузку и сохраняет в список
    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.customerList = customerService.findAll();
    }

    public CustomerDto getCustomer() {
        return customerDto;
    }

    public void setCustomer(CustomerDto customerDto){
        this.customerDto = customerDto;
    }

    public List<CustomerDto> findAll(){
        return customerList;
    }

    public String editCustomer(CustomerDto customerDto){
        this.customerDto = customerDto;
        return "/customer_form.xhtml?faces-redirect=true";
    }

    public void deleteCustomer(CustomerDto customerDto) {
        customerService.delete(customerDto.getId());
    }

    public String saveCustomer() {
        customerService.save(customerDto);
        return "/customer.xhtml?faces-redirect=true";
    }

    public String addCustomer() {
        this.customerDto = new CustomerDto();
        return "/customer_form.xhtml?faces-redirect=true";
    }
}
