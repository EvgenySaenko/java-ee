package ru.geekbrains;


import ru.geekbrains.persist.dto.ProductDto;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

public class JmsClient {

    public static void main(String[] args) throws IOException, NamingException {
        //контекст он объеденяет connection and session
        Context context = createInitialContext();//подключаемся в JNDI контексту

        //получаем конекшен фектори которая является входной точкой в JMS
        ConnectionFactory factory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
        JMSContext jmsContext = factory.createContext("jmsuser", "123");

        //получаем место в которое хотим отправить данное сообщение
        Destination dest = (Destination) context.lookup("jms/queue/productQueue");//задаем имя очереди

        //чтобы отправить сообщение создаем продюсера
        JMSProducer producer = jmsContext.createProducer();

        //создаем сообщение
        ObjectMessage om = jmsContext.createObjectMessage(new ProductDto(null, "Product from JMS", "Product from JMS",
                new BigDecimal(100), 1L, null));

        producer.send(dest, om);//продюсер шлет сообщения, указав куда и что шлем
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
