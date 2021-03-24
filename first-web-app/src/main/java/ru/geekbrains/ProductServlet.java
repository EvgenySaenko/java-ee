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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    // /category/123/product/1231415/list
    // Pattern.compile("\\/(\\d*)\\/product\\/(\\d*)");

    // /123
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
            resp.getWriter().println("<table>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<th>Id</th>");
            resp.getWriter().println("<th>Name</th>");
            resp.getWriter().println("<th>Description</th>");
            resp.getWriter().println("<th>Price</th>");
            resp.getWriter().println("</tr>");

            for (Product product : productRepository.findAll()) {
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td><a href='" + getServletContext().getContextPath() + "/product/" + product.getId() + "'>" + product.getId() + "</a></td>");
                resp.getWriter().println("<td>" + product.getName() + "</td>");
                resp.getWriter().println("<td>" + product.getDescription() + "</td>");
                resp.getWriter().println("<td>" + product.getPrice() + "</td>");
                resp.getWriter().println("</tr>");
            }
            resp.getWriter().println("</table>");
        } else {
            Matcher matcher = pathParam.matcher(req.getPathInfo());//сохраняем матчер
            if (matcher.matches()){//если матчится - т.е. путь совпал то вернем true
                long id;
                try{
                    id = Long.parseLong(matcher.group(1)); //в 0 будет весь path а в 1 будет (\\d*) как раз наш id
                }catch (NumberFormatException e){
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                //Product product = productRepository.findById(Long.valueOf(req.getPathInfo().replaceAll("\\W", "")));
                Product product = productRepository.findById(id);
                resp.getWriter().println("<p>Product info</p>");
                resp.getWriter().println("<p>Id: " + product.getId() + "</p>");
                resp.getWriter().println("<p>Name: " + product.getName() + "</p>");
                resp.getWriter().println("<p>Description: " + product.getDescription() + "</p>");
                resp.getWriter().println("<p>Price: " + product.getPrice() + "</p>");
                resp.getWriter().println("<a href='" + getServletContext().getContextPath() + "/product" + "'>" + "Products Page " + "</a>");
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
