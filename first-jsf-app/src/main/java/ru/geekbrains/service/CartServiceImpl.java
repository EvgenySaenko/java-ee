package ru.geekbrains.service;


import ru.geekbrains.persist.dto.ProductDto;

import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//этот бин хранит данные и поэтому аннотация @Stateless не совсем подходит
// и аннотация @Singleton тоже не подходит, так как корзина должна быть своя для каждого пользователя
//поэтому используем @Stateful - бин создается когда мы пытаемся получить к нему доступ и пожет хранить инфу
@Stateful
public class CartServiceImpl implements CartService {


}
