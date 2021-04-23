package ru.geekbrains.service;

import ru.geekbrains.persist.dto.CustomerDto;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CustomerService {

    void save(CustomerDto customerDto);

    void delete(Long id);

    CustomerDto findById(Long id);

    List<CustomerDto> findAll();

    Long countCustomers();
}
