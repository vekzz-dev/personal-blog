package io.github.vekzzdev.personalblog;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;

import java.util.Map;

public class AdminController {

    private final ArticleService articleService;

    public AdminController(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void registerRoutes() {
        ApiBuilder.path("/admin", () -> {
            ApiBuilder.get("", this::getAll);
            ApiBuilder.get("/article/{id}", this::getById);
            ApiBuilder.get("/new", this::newArticle);
            ApiBuilder.post("/create", this::create);
            ApiBuilder.get("/edit/{id}", this::editArticle);
            ApiBuilder.post("/update/{id}", this::update);
            ApiBuilder.get("/delete/{id}", this::delete);
        });
    }

    private void getAll(Context ctx) {
        var articles = articleService.findAll();

        ctx.render("templates/admin.jte", Map.of("articles", articles));
    }

    private void getById(Context ctx) {
        var id = ctx.pathParam("id");
        var article = articleService.findById(id);

        ctx.render("templates/article-admin.jte", Map.of("article", article));
    }

    private void newArticle(Context ctx) {
        ctx.render("templates/new-article.jte");
    }

    private void create(Context ctx) {
        var title = ctx.formParam("title");
        var content = ctx.formParam("content");

        articleService.insert(title, content);
        ctx.redirect("/admin");
    }

    private void editArticle(Context ctx) {
        var id = ctx.pathParam("id");
        var article = articleService.findById(id);

        ctx.render("templates/update-article.jte", Map.of("article", article));
    }

    private void update(Context ctx) {
        var id = ctx.pathParam("id");
        var title = ctx.formParam("title");
        var content = ctx.formParam("content");
        var date = ctx.formParam("publish_date");

        articleService.update(id, title, content, date);
        ctx.redirect("/admin");
    }

    private void delete(Context ctx) {
        var id = ctx.pathParam("id");

        articleService.delete(id);
        ctx.redirect("/admin");
    }
}
