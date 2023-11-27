package org.example.servlet;

import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
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
                case "PUT":
                    if (path.matches("/api/posts/\\d+")) {
                        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                        controller.updateById(id, req.getReader(), resp);
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