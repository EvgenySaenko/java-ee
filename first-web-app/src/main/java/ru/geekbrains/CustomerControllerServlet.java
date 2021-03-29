package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.listener.StartupListener;
import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomerRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/customer/*")
public class CustomerControllerServlet extends HttpServlet {
    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");

    private CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);


    @Override
    public void init() throws ServletException {
        customerRepository = (CustomerRepository) getServletContext().getAttribute("customerRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            req.setAttribute("customers", customerRepository.findAll());//создали атрибут products и в него сохраним список продуктов для отображения
            getServletContext().getRequestDispatcher("/WEB-INF/views/customer.jsp").forward(req, resp);
        }else if (req.getPathInfo().equals("/new")) {
            //отобразить пустую форму ввода
            req.setAttribute("customer", new Customer());//добавили атрибут product и в него положили пока пустой продукт
            getServletContext().getRequestDispatcher("/WEB-INF/views/customer_form.jsp").forward(req,resp);

        } else {
            Matcher matcher = pathParam.matcher(req.getPathInfo());//сохраняем матчер
            if (matcher.matches()) {//если матчится - т.е. путь совпал то вернем true
                long id;
                try {
                    id = Long.parseLong(matcher.group(1)); //в 0 будет весь path а в 1 будет (\\d*) как раз наш id
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Customer customer = customerRepository.findById(id);
                if (customer == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                req.setAttribute("customer", customer);//добавили атрибут product и в него положили этот продукт
                getServletContext().getRequestDispatcher("/WEB-INF/views/customer_form.jsp").forward(req,resp);
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            String strId = req.getParameter("id");
            try {
                Customer customer =  new Customer(
                        strId.equals("") ? null : Long.parseLong(strId),
                        req.getParameter("username"),
                        req.getParameter("password"));
                customerRepository.save(customer);
                resp.sendRedirect(getServletContext().getContextPath() + "/customer");//просим перейти на эту ссылку браузер(ответ с 300 кодом)
            }catch (NumberFormatException ex){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }else if (req.getPathInfo().startsWith("/delete")) {
            try{
                long id = getIdFromParams(req.getPathInfo().replace("/delete",""));
                customerRepository.delete(id);
                resp.sendRedirect(getServletContext().getContextPath() + "/customer");//просим перейти на эту ссылку браузер(ответ с 300 кодом)
            }catch (IllegalArgumentException ex) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        }else{
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    //парсит путь и возвращает кусочек пути
    private long getIdFromParams(String path) {
        Matcher matcher = pathParam.matcher(path);//сохраняем матчер
        if (matcher.matches()) {//если матчится - т.е. путь совпал то вернем true
            try {
                return Long.parseLong(matcher.group(1)); //в 0 будет весь path а в 1 будет (\\d*) как раз наш id
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }
        throw new IllegalArgumentException();
    }
}
