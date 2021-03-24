package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.listener.StartupListener;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
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

@WebServlet(urlPatterns = "/category/*")
public class CategoryControllerServlet extends HttpServlet {
    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");

    private CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);


    @Override
    public void init() throws ServletException {
        categoryRepository = (CategoryRepository)getServletContext().getAttribute("categoryRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            req.setAttribute("categories", categoryRepository.findAll());//создали атрибут categories и в него сохраним список категорий для отображения
            getServletContext().getRequestDispatcher("/WEB-INF/views/category.jsp").forward(req, resp);
        }else if (req.getPathInfo().equals("/new")) {
            //отобразить пустую форму ввода
            req.setAttribute("category", new Category());//добавили атрибут product и в него положили пока пустой продукт
            getServletContext().getRequestDispatcher("/WEB-INF/views/category_form.jsp").forward(req,resp);

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
                Category category = categoryRepository.findById(id);
                if (category == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                req.setAttribute("category", category);//добавили атрибут product и в него положили этот продукт
                getServletContext().getRequestDispatcher("/WEB-INF/views/category_form.jsp").forward(req,resp);
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            String strId = req.getParameter("id");
            try {
                Category category =  new Category(
                        strId.equals("") ? null : Long.parseLong(strId),
                        req.getParameter("name"));
                categoryRepository.save(category);
                resp.sendRedirect(getServletContext().getContextPath() + "/category");//просим перейти на эту ссылку браузер(ответ с 300 кодом)
            }catch (NumberFormatException ex){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
