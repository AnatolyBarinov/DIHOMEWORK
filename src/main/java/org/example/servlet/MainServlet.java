package org.example.servlet;

import org.example.controller.PostController;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
@ComponentScan
public class MainServlet extends HttpServlet {
    private PostController postController;

    @Override
    public void init() throws ServletException {
        // Получаем бин контроллера из контекста
        postController = ApplicationContextProvider.getContext().getBean(PostController.class);
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
}