package ru.geekbrains.service;


import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomerRepository;
import ru.geekbrains.persist.dto.CustomerDto;
import ru.geekbrains.rest.CustomerResource;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CustomerServiceImpl implements CustomerService, CustomerResource {

    @EJB
    private CustomerRepository customerRepository;

    @Override
    @TransactionAttribute
    public void save(CustomerDto customerDto) {
        customerRepository.save(new Customer(
                customerDto.getId(),
                customerDto.getUsername(),
                customerDto.getPassword()
        ));
    }

    @Override
    @TransactionAttribute
    public void delete(Long id) {
        customerRepository.delete(id);
    }

    @Override
    public CustomerDto findById(Long id) {
        return createCustomerDto(customerRepository.findById(id));
    }

    @Override
    public void insert(CustomerDto customerDto) {
        if (customerDto.getId() != null){
            throw new IllegalArgumentException("Not null id in the inserted Customer");
        }
        save(customerDto);
    }

    @Override
    public void update(CustomerDto customerDto) {
        if (customerDto.getId() == null){
            throw new IllegalArgumentException("Not id in the inserted Customer");
        }
        save(customerDto);
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerRepository.findAll().stream().map(CustomerServiceImpl::createCustomerDto).collect(Collectors.toList());
    }

    @Override
    public Long countCustomers() {
        return customerRepository.countCustomers();
    }

    private static CustomerDto createCustomerDto(Customer customer){
        return new CustomerDto(
                customer.getId(),
                customer.getUsername(),
                customer.getPassword());
    }
}
