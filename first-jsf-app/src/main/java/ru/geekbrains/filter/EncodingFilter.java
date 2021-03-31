package ru.geekbrains.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(EncodingFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Encoding filter");
        //перед тем как запрос прийдет на сервлет установим кодировку
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        //все что выше это если что-то нужно сделать до того как запрос попадет сервлету
        chain.doFilter(request, response);
        //после как запрос уже попал в сервлет
    }

    @Override
    public void destroy() {

    }
}
