package io.github.vekzzdev.personalblog.exception;

public class InvalidArticleIdException extends ArticleException {

    public InvalidArticleIdException(String id) {
        super("Invalid article id " + id);
    }
}
