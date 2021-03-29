package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.listener.StartupListener;
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

@WebServlet(urlPatterns = "/product/*")
public class ProductControllerServlet extends HttpServlet {

    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");

    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);


    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            req.setAttribute("products", productRepository.findAll());//создали атрибут products и в него сохраним список продуктов для отображения
            getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
        }else if (req.getPathInfo().equals("/new")) {
            //отобразить пустую форму ввода
            req.setAttribute("product", new Product());//добавили атрибут product и в него положили пока пустой продукт
            getServletContext().getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(req,resp);

        } else {
            try{
                long id = getIdFromParams(req.getPathInfo());
                Product product = productRepository.findById(id);
                if (product == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                req.setAttribute("product", product);//добавили атрибут product и в него положили этот продукт
                getServletContext().getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(req,resp);
            }catch (IllegalArgumentException ex) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            String strId = req.getParameter("id");
            try {
                Product product =  new Product(
                        strId.equals("") ? null : Long.parseLong(strId),
                        req.getParameter("name"),
                        req.getParameter("description"),
                        new BigDecimal(req.getParameter("price")));
                productRepository.save(product);
                resp.sendRedirect(getServletContext().getContextPath() + "/product");//просим перейти на эту ссылку браузер(ответ с 300 кодом)
            }catch (NumberFormatException ex){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }else if (req.getPathInfo().startsWith("/delete")) {
            try{
                long id = getIdFromParams(req.getPathInfo().replace("/delete",""));
                productRepository.delete(id);
                resp.sendRedirect(getServletContext().getContextPath() + "/product");//просим перейти на эту ссылку браузер(ответ с 300 кодом)
            }catch (IllegalArgumentException ex) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        }else{
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

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
