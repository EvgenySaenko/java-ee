package ru.geekbrains.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/admin/*")
public class SecurityFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Security filter");

        if (request.getParameter("username") != null) {
            chain.doFilter(request, response);
        } else {//RequestDispatcher для внутренних запросов между сервлетами
            filterConfig.getServletContext().getRequestDispatcher("/access_denied").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
