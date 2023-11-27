package org.example.servlet;

import org.example.config.AppConfig;
import org.example.controller.PostController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.Reader;

public class MainServlet extends HttpServlet {
    private WebApplicationContext context;
    private PostController postController;

    @Override
    public void init() throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(AppConfig.class);
        webApplicationContext.refresh();
        context = webApplicationContext;

        // Получаем бин контроллера из контекста
        postController = context.getBean(PostController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        postController.all(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Reader reader = req.getReader()) {
            postController.save(reader, resp);
        }
    }
}
