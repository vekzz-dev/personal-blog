package io.github.vekzzdev.personalblog;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;

import java.util.Map;

public class HomeController {

    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void registerRoutes() {
        ApiBuilder.path("/", () -> {
            ApiBuilder.get("/", ctx -> ctx.redirect("/home"));
            ApiBuilder.get("/home", this::getAll);
            ApiBuilder.get("/home/article/{id}", this::getById);
        });
    }

    private void getAll(Context ctx) {
        var articles = articleService.findAll();

        ctx.render("templates/home.jte", Map.of("articles", articles));
    }

    private void getById(Context ctx) {
        var id = ctx.pathParam("id");
        var article = articleService.findById(id);

        ctx.render("templates/article.jte", Map.of("article", article));
    }
}
