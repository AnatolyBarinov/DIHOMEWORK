package org.example.servlet;


import org.example.config.JavaConfig;
import org.example.controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        if (req == null || resp == null) {
            return;
        }

        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            switch (method) {
                case "GET":
                    if (path.equals("/api/posts")) {
                        controller.all(resp);
                        return;
                    }
                    if (path.matches("/api/posts/\\d+")) {
                        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                        controller.getById(id, resp);
                        return;
                    }
                    break;
                case "POST":
                    if (path.equals("/api/posts")) {
                        controller.save(req.getReader(), resp);
                        return;
                    }
                    break;
                case "DELETE":
                    if (path.matches("/api/posts/\\d+")) {
                        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                        controller.removeById(id, resp);
                        return;
                    }
                    break;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resp.getWriter().println("Internal Server Error");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
