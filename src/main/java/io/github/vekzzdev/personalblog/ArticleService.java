package io.github.vekzzdev.personalblog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.vekzzdev.personalblog.exception.ArticleException;
import io.github.vekzzdev.personalblog.exception.ArticleNotFoundException;
import io.github.vekzzdev.personalblog.exception.InvalidArticleIdException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

public class ArticleService {

    private final Gson gson;
    private final Storage store;

    public ArticleService(Storage store) {
        this.store = store;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<Article> findAll() {
        String json = store.read();

        return gson.fromJson(json,
                new TypeToken<List<Article>>() {
                }.getType()
        );
    }

    public Article findById(String id) {
        List<Article> articles = findAll();

        var uuid = parseId(id);

        return articles.stream()
                .filter(a -> a.id().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public Article insert(String title, String content) {
        List<Article> articles = findAll();

        var newArticle = Article.create(title, content);
        articles.add(newArticle);

        store.write(gson.toJson(articles));
        return newArticle;
    }

    public Article update(String id, String title,
                          String content, String publishDate) {
        List<Article> articles = findAll();

        var uuid = parseId(id);

        var current = articles.stream()
                .filter(a -> a.id().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new ArticleNotFoundException(id));

        var date = parseDate(publishDate);
        var updated = current.update(title, content, date);

        List<Article> nextArticles = articles.stream()
                .map(a -> a.id().equals(uuid) ? updated : a)
                .toList();

        store.write(gson.toJson(nextArticles));
        return updated;
    }

    public void delete(String id) {
        List<Article> articles = findAll();
        var uuid = parseId(id);

        var removed = articles.removeIf(a -> a.id().equals(uuid));
        if (!removed) throw new ArticleNotFoundException(id);

        store.write(gson.toJson(articles));
    }

    private UUID parseId(String id) {
        try {
            return UUID.fromString(id);

        } catch (IllegalArgumentException e) {
            throw new InvalidArticleIdException(id);
        }
    }

    private LocalDateTime parseDate(String date) {
        try {
            return LocalDateTime.parse(date);

        } catch (DateTimeParseException e) {
            throw new ArticleException("Invalid date format: " + date);
        }
    }
}
