package ru.geekbrains.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override//будет вызван когда приложение запустится один раз
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Startup Listener");

        //создастся репозиторий
        ProductRepository productRepository = new ProductRepository();
        //и наполнится контентом
        productRepository.save(new Product(null,"Product1","Description 1", new BigDecimal(100)));
        productRepository.save(new Product(null,"Product2","Description 2", new BigDecimal(200)));
        productRepository.save(new Product(null,"Product3","Description 3", new BigDecimal(300)));
        //помещаем репозиторий сюда и вытащить его можем где угодно
        sce.getServletContext().setAttribute("productRepository",productRepository);

        //добавим категорий
        CategoryRepository categoryRepository =  new CategoryRepository();
        categoryRepository.save(new Category(null,"electronics"));
        categoryRepository.save(new Category(null,"food"));
        categoryRepository.save(new Category(null,"drinks"));
        sce.getServletContext().setAttribute("categoryRepository",categoryRepository);

        //добавим покупателей
        CustomerRepository customerRepository = new CustomerRepository();
        customerRepository.save(new Customer(null,"JackRicher","123456"));
        customerRepository.save(new Customer(null,"John McClain","777"));
        sce.getServletContext().setAttribute("customerRepository", customerRepository);

    }
}
