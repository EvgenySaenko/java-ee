package ru.geekbrains;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FirstServlet implements Servlet {
    private static final Logger logger = LoggerFactory.getLogger(FirstServlet.class);
    private ServletConfig config;

    private List<String> sharedList = new CopyOnWriteArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Servlet initialized");

        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override//вызывается при каждом запросе
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        logger.info("New request");

        sharedList.add("asdada");

        res.getWriter().println("<h1>Hello from Servlet!!!</h1>");
        res.getWriter().println("<h2>Привет!!!</h2>");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
