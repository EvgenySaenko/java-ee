package ru.geekbrains.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.dto.ProductDto;
import ru.geekbrains.service.ProductService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(
        activationConfig = {//указываем к какой очереди подключаться
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/productQueue")
        }
)
public class JmsProductReceiver implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(JmsProductReceiver.class);

    @EJB
    private ProductService productService;

    @Override
    public void onMessage(Message message) {
        logger.info("New JMS message");

        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
            try {
                productService.save((ProductDto) om.getObject());
            } catch (JMSException e) {
                logger.error("", e);
            }
        }
    }
}
