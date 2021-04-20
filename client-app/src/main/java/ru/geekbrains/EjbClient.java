package ru.geekbrains;

import ru.geekbrains.service.ProductServiceRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class EjbClient {
    //клиент который может использовать наш API модуль или отдельное приложение
    public static void main(String[] args) throws IOException, NamingException {
        Context context = createInitialContext();

        //в эту строку кладем из логов локального сервера ссылку на ejb бин
        String jndiServiceName = "ejb:/first-jsf-app/ProductServiceImpl!ru.geekbrains.service.ProductServiceRemote";
        ProductServiceRemote productService = (ProductServiceRemote) context.lookup(jndiServiceName);//возвращает Object делаем првиедение

        productService.findAllRemote()
                .forEach(prod -> System.out.println(prod.getId() + "\t" + prod.getName() + "\t" + prod.getCategoryId()));
    }

    //считывает настройки из файла properties и создает initialcontext
    //чтобы удаленно подключится к JNDI каталогу нашего сервера wildFly
    //из этого каталога можем получать доступ к разным элементам в том числе и к EJB бинам
    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
