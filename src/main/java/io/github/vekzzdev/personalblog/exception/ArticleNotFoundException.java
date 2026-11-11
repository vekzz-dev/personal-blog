package io.github.vekzzdev.personalblog.exception;

public class ArticleNotFoundException extends ArticleException{

    public ArticleNotFoundException(String id) {
        super("Article with id " + id + " not found");
    }
}
