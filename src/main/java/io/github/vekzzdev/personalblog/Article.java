package io.github.vekzzdev.personalblog;

import io.github.vekzzdev.personalblog.exception.ArticleValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public record Article(
        UUID id,
        String title,
        String content,
        LocalDateTime publishedAt
) {
    public Article {
        if (id == null)
            throw new ArticleValidationException("Article id cannot be null");

        if (title == null || title.isBlank())
            throw new ArticleValidationException("Title cannot be empty");

        if (content == null || content.isBlank())
            throw new ArticleValidationException("Content cannot be empty");

        if (publishedAt == null)
            throw new ArticleValidationException("Published At cannot be null");

        if (publishedAt.isAfter(LocalDateTime.now()))
            throw new ArticleValidationException("Published At cannot be after now");

        title = title.strip();
        content = content.strip();
    }

    public static Article create(String title, String content) {
        var id = UUID.randomUUID();
        var publishedAt = LocalDateTime.now();
        return new Article(id, title, content, publishedAt);
    }

    public Article update(String title, String content, LocalDateTime publishedAt) {
        return new Article(id, title, content, publishedAt);
    }

    public String getId() {
        return id.toString();
    }

    public String getFormattedPublishedAt() {
        return publishedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public String getPrettyPublishedAt() {
        var day = publishedAt.getDayOfMonth();
        var month = publishedAt.getMonth().toString().toLowerCase();
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        var year = publishedAt.getYear();
        var time = publishedAt.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH));

        return String.format("%s %d, %d %s", month, day, year, time);
    }
}
