package ru.geekbrains.controller;

import ru.geekbrains.persist.dto.ProductDto;
import ru.geekbrains.service.CartService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped//т.к. он сессионный для каждой сессии пользователя будет создаваться новый экземпляр этого бина
@Named
public class CartController implements Serializable {

}
